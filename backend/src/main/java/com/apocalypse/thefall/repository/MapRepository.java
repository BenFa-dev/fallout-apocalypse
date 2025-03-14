package com.apocalypse.thefall.repository;

import com.apocalypse.thefall.model.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MapRepository extends JpaRepository<Map, Long> {
    Optional<Map> findFirstByOrderByIdAsc();
}