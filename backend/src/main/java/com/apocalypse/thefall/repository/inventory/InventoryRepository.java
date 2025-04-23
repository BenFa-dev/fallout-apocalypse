package com.apocalypse.thefall.repository.inventory;

import com.apocalypse.thefall.entity.inventory.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    @Query("""
                SELECT i FROM Inventory i
                LEFT JOIN FETCH i.items itemInstance
                LEFT JOIN FETCH itemInstance.item
                WHERE i.character.id = :characterId
            """)
    Optional<Inventory> findByCharacterId(Long characterId);
}