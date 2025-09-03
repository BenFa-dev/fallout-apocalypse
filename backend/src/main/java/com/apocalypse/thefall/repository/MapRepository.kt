package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.entity.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<GameMap, Long> {
    Optional<GameMap> findFirstByOrderByIdAsc();
}