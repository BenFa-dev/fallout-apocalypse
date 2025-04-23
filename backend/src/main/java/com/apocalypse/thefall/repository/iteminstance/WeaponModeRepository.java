package com.apocalypse.thefall.repository.iteminstance;

import com.apocalypse.thefall.entity.item.WeaponMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponModeRepository extends JpaRepository<WeaponMode, Long> {
}