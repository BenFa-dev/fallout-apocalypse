package com.apocalypse.thefall.repository.item;

import com.apocalypse.thefall.entity.item.Weapon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponRepository extends JpaRepository<Weapon, Long> {
} 