package com.apocalypse.thefall.repository.item

import com.apocalypse.thefall.entity.item.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemRepository : JpaRepository<Item, Long>