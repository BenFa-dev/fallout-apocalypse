package com.apocalypse.thefall.config.logging

import jakarta.persistence.EntityManagerFactory
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.hibernate.SessionFactory
import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.data.repository.Repository
import org.springframework.stereotype.Component
import kotlin.system.measureTimeMillis

@Aspect
@Component
@ConditionalOnProperty(prefix = "cache.logging", name = ["enabled"], havingValue = "true")
class CacheLoggerAspect(emf: EntityManagerFactory) {

    private val log = LoggerFactory.getLogger(javaClass)
    private val sf: SessionFactory = emf.unwrap(SessionFactory::class.java)

    private data class C(val h: Long, val m: Long, val p: Long) {
        operator fun minus(o: C) = C(h - o.h, m - o.m, p - o.p)
        fun any() = h != 0L || m != 0L || p != 0L
    }

    private fun ProceedingJoinPoint.repoEntity(): String? {
        tailrec fun scan(stack: Array<out Class<*>>): String? {
            if (stack.isEmpty()) return null
            val head = stack.first()
            if (Repository::class.java.isAssignableFrom(head)
                && head.name.startsWith("com.apocalypse.thefall.repository")
                && head.simpleName.endsWith("Repository")
            ) return head.simpleName.removeSuffix("Repository")
            val next = head.interfaces + stack.drop(1)
            return scan(next)
        }
        return scan(`this`.javaClass.interfaces)
    }

    private fun icons(l2: C, rq: C) = buildList {
        if (rq.h > 0 || rq.p > 0) add("🔷RQ")
        if (l2.h > 0) add("🟢L2")
        if (l2.p > 0 || rq.p > 0) add("📦PUT")
        if (l2.m > 0 || rq.m > 0) add("🟠MISS")
        if (isEmpty()) add("⚪️NONE")
    }.joinToString(" ")

    private fun warning(ms: Long, l2: C, rq: C, regions: List<String>, method: String): String {
        val bulk = method.startsWith("findAll", true)
                || method.startsWith("search", true)
                || method.startsWith("list", true)
                || method.startsWith("page", true)
                || regions.size >= 2

        val msgs = buildList {
            when {
                ms >= 1_000 && l2.h == 0L && rq.h == 0L -> add("SLOW/COLD")
                ms >= 1_000 -> add("SLOW")
            }
            if (bulk && ms >= 100 && rq.h == 0L && rq.p == 0L && l2.h == 0L) add("consider query cache")
            if (regions.size >= 6 && (l2.p > 0 || l2.m > 0)) add("large fetch graph; review JOIN FETCH / L2 on associations")
            if (l2.m > 0 && l2.h == 0L && l2.p == 0L) add("entities not cached; add @Cache")
        }
        return if (msgs.isEmpty()) "" else " | ⚠️ " + msgs.joinToString(" · ")
    }

    @Around("execution(* com.apocalypse.thefall.repository..*(..))")
    fun around(pjp: ProceedingJoinPoint): Any? {
        val s = sf.statistics

        // before
        val l2B = C(s.secondLevelCacheHitCount, s.secondLevelCacheMissCount, s.secondLevelCachePutCount)
        val rqB = C(s.queryCacheHitCount, s.queryCacheMissCount, s.queryCachePutCount)
        val regB = s.secondLevelCacheRegionNames.associateWith { r ->
            s.getCacheRegionStatistics(r)?.let { C(it.hitCount, it.missCount, it.putCount) } ?: C(0, 0, 0)
        }

        var out: Any? = null
        val ms = measureTimeMillis { out = pjp.proceed() }

        // deltas
        val l2D = C(s.secondLevelCacheHitCount, s.secondLevelCacheMissCount, s.secondLevelCachePutCount) - l2B
        val rqD = C(s.queryCacheHitCount, s.queryCacheMissCount, s.queryCachePutCount) - rqB

        val regions = s.secondLevelCacheRegionNames.mapNotNull { r ->
            if (r == "default-query-results-region") return@mapNotNull null
            val after = s.getCacheRegionStatistics(r)?.let { C(it.hitCount, it.missCount, it.putCount) } ?: C(0, 0, 0)
            (after - (regB[r] ?: C(0, 0, 0))).takeIf { it.any() }?.let { r.substringAfterLast('.') }
        }

        val entity = pjp.repoEntity() ?: regions.firstOrNull() ?: pjp.signature.declaringType.simpleName
        val warn = warning(ms, l2D, rqD, regions, pjp.signature.name)

        log.info(
            "Cache[$entity.${pjp.signature.name}] ${ms}ms | ${icons(l2D, rqD)} | " +
                    "L2(h=${l2D.h} m=${l2D.m} p=${l2D.p}) | RQ(h=${rqD.h} m=${rqD.m} p=${rqD.p})" +
                    ((regions.takeIf { it.isNotEmpty() }?.joinToString(prefix = " | ") ?: "") + warn)
        )
        return out
    }
}
