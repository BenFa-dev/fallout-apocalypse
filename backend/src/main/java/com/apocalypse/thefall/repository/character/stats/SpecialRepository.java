package com.apocalypse.thefall.repository.character.stats;

import com.apocalypse.thefall.entity.character.stats.Special;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialRepository extends JpaRepository<Special, Long> {

    List<Special> findAllByVisibleTrueOrderByDisplayOrderAsc();
}
