package com.apocalypse.thefall.repository.character

import com.apocalypse.thefall.entity.character.Character
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CharacterRepository : JpaRepository<Character, Long> {

    @Query(
        """
        SELECT c FROM Character c
        WHERE c.userId = :userId
        """
    )
    fun findSimpleByUserId(userId: String): Character?

    @Query(
        """
        select c
        from Character c
        left join fetch c.inventory i
        where c.userId = :userId
    """
    )
    fun findByUserIdForInventory(userId: String): Character?

    @EntityGraph(
        attributePaths = ["currentStats", "skills", "perks", "specials", "conditions"],
        type = EntityGraph.EntityGraphType.LOAD
    )
    @Query("select c from Character c where c.id = :id")
    fun fetchStatsById(id: Long): Character
}
