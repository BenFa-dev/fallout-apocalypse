package com.apocalypse.thefall.repository.item;

import com.apocalypse.thefall.entity.item.Armor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmorRepository extends JpaRepository<Armor, Long> {
} 