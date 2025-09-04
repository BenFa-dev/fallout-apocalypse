package com.apocalypse.thefall.controller.admin

import com.apocalypse.thefall.service.cache.CacheEvictService
import com.apocalypse.thefall.service.initializer.GameInitializer
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin")
open class AdminController(
    private val cacheEvictService: CacheEvictService,
    private val gameInitializer: GameInitializer
) {

    @GetMapping("/test")
    @PreAuthorize("hasRole('ROLE_admin')")
    open fun adminEndpoint(): String {
        return "Cet endpoint nécessite le rôle ADMIN"
    }

    @PostMapping("/reload-reference-data")
    fun reload(): Map<String, Any> {
        cacheEvictService.evictReferenceDataCaches()
        val stats = gameInitializer.init()
        return mapOf("status" to "ok", "reloaded" to stats)
    }
}
