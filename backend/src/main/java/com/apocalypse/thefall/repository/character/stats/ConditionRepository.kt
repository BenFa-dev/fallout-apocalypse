package com.apocalypse.thefall.repository.character.stats;

import com.apocalypse.thefall.entity.character.stats.Condition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConditionRepository extends JpaRepository<Condition, Long> {

    List<Condition> findAllByVisibleTrueOrderByDisplayOrderAsc();
}
