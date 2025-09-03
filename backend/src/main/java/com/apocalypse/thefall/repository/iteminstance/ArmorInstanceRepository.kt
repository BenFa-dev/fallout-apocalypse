package com.apocalypse.thefall.repository.iteminstance;

import com.apocalypse.thefall.entity.instance.ArmorInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArmorInstanceRepository extends JpaRepository<ArmorInstance, Long> {
}