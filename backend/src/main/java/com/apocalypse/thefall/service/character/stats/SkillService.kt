package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.stats.Skill;
import com.apocalypse.thefall.repository.character.stats.SkillRepository;
import com.apocalypse.thefall.service.character.rules.RuleEngine;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SkillService {

    private final SkillRepository skillRepository;
    private final RuleEngine ruleEngine;

    @PostConstruct
    public void init() {

        List<Skill> skills = skillRepository.findAll();

        log.info("Preloading {} skills", skills.size());

        ruleEngine.preload(skills);
    }

    @Transactional(readOnly = true)
    public List<Skill> findAll() {
        return skillRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }
}