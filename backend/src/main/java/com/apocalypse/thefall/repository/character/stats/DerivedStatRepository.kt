package com.apocalypse.thefall.repository.character.stats;

import com.apocalypse.thefall.entity.character.stats.DerivedStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DerivedStatRepository extends JpaRepository<DerivedStat, Long> {

    List<DerivedStat> findAllByVisibleTrueOrderByDisplayOrderAsc();
}
