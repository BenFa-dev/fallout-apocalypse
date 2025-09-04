package com.apocalypse.thefall.repository.iteminstance

import com.apocalypse.thefall.entity.instance.ItemInstance
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ItemInstanceRepository : JpaRepository<ItemInstance, Long> {
    @Query(
        """
        select distinct ii
        from ItemInstance ii
        left join fetch ii.item it
        where ii.inventory.id = :inventoryId
    """
    )
    fun loadForInventoryWithItem(inventoryId: Long): List<ItemInstance>
}