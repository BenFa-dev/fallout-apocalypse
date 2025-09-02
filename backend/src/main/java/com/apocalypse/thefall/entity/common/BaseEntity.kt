package com.apocalypse.thefall.entity.common

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@MappedSuperclass
open class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    open var createdAt: Instant? = null

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    open var updatedAt: Instant? = null
}
