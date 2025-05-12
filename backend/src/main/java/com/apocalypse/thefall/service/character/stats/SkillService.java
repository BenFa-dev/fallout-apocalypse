package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.stats.Skill;
import com.apocalypse.thefall.repository.character.stats.SkillRepository;
import com.apocalypse.thefall.service.character.rules.RuleEngine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final RuleEngine ruleEngine;

    @PostConstruct
    public void init() {
        ruleEngine.preload(skillRepository.findAll());
    }

    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }
}