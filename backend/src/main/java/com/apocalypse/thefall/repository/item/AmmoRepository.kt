package com.apocalypse.thefall.repository.item

import com.apocalypse.thefall.entity.item.Ammo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AmmoRepository : JpaRepository<Ammo, Long>