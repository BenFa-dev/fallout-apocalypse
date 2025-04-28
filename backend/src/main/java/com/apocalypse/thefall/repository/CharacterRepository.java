package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.entity.character.Character;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CharacterRepository extends JpaRepository<Character, Long> {

    @Query("""
                SELECT c FROM Character c
                LEFT JOIN FETCH c.inventory i
                LEFT JOIN FETCH c.skills s
                LEFT JOIN FETCH i.items its
                LEFT JOIN FETCH its.item it
                LEFT JOIN FETCH TREAT(it AS Weapon).compatibleAmmo
                LEFT JOIN FETCH TREAT(it AS Armor).damages d
                LEFT JOIN FETCH d.damageType
                WHERE c.userId = :userId
            """)
    Optional<Character> findByUserId(String userId);

    @Query("""
                SELECT c FROM Character c
                LEFT JOIN FETCH c.inventory i
                LEFT JOIN FETCH c.skills s
                LEFT JOIN FETCH i.items its
                LEFT JOIN FETCH its.item it
                LEFT JOIN FETCH TREAT(it AS Weapon).compatibleAmmo
                LEFT JOIN FETCH TREAT(it AS Armor).damages d
                LEFT JOIN FETCH d.damageType
                WHERE c.id = :id
            """)
    Optional<Character> findByCharacterId(Long id);
}
