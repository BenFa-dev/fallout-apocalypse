package com.apocalypse.thefall.repository.inventory

import com.apocalypse.thefall.entity.inventory.Inventory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface InventoryRepository : JpaRepository<Inventory, Long>