package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.entity.item.DamageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DamageTypeRepository extends JpaRepository<DamageType, Long> {
}
