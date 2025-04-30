package com.apocalypse.thefall.repository.character.stats;

import com.apocalypse.thefall.entity.character.stats.Perk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerkRepository extends JpaRepository<Perk, Long> {

    List<Perk> findAllByVisibleTrueOrderByDisplayOrderAsc();
}
