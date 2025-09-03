package com.apocalypse.thefall.repository.iteminstance

import com.apocalypse.thefall.entity.instance.AmmoInstance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AmmoInstanceRepository : JpaRepository<AmmoInstance, Long>
