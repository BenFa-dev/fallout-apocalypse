package com.apocalypse.thefall.repository.iteminstance;

import com.apocalypse.thefall.entity.instance.WeaponInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeaponInstanceRepository extends JpaRepository<WeaponInstance, Long> {
}