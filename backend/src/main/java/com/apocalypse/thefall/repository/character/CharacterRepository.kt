package com.apocalypse.thefall.repository.character

import com.apocalypse.thefall.entity.character.Character
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
        SELECT c FROM Character c
        LEFT JOIN FETCH c.inventory i
        LEFT JOIN FETCH c.skills s
        LEFT JOIN FETCH c.perks p
        LEFT JOIN FETCH c.specials sp
        LEFT JOIN FETCH c.conditions cd
        LEFT JOIN FETCH i.items its
        LEFT JOIN FETCH its.item it
        LEFT JOIN FETCH TREAT(it AS Weapon).compatibleAmmo
        LEFT JOIN FETCH TREAT(it AS Armor).damages d
        LEFT JOIN FETCH d.damageType
        WHERE c.userId = :userId
        """
    )
    fun findByUserIdForInventory(userId: String): Character?
}
