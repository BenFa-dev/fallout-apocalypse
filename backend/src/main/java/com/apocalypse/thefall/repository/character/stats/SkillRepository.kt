package com.apocalypse.thefall.repository.character.stats;

import com.apocalypse.thefall.entity.character.stats.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findAllByVisibleTrueOrderByDisplayOrderAsc();
}
