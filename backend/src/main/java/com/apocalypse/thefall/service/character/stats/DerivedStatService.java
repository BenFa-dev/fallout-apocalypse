package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.stats.DerivedStat;
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance;
import com.apocalypse.thefall.repository.character.stats.DerivedStatRepository;
import com.apocalypse.thefall.service.character.rules.RuleEngine;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class DerivedStatService {

    private final DerivedStatRepository derivedStatRepository;
    private final RuleEngine ruleEngine;
    private Set<DerivedStatInstance> defaultZeroDerivedStatsInstance = new HashSet<>();

    @PostConstruct
    public void init() {

        List<DerivedStat> derivedStats = derivedStatRepository.findAll();

        ruleEngine.preload(derivedStats);

        // Pre-init instances values to 0, because not existing values in the database
        this.defaultZeroDerivedStatsInstance = derivedStats.stream()
                .map(stat -> DerivedStatInstance.builder()
                        .derivedStat(stat)
                        .value(0)
                        .build())
                .collect(Collectors.toCollection(LinkedHashSet::new));

        log.info("Preloading {} derived stats", this.defaultZeroDerivedStatsInstance.size());
    }

    @Transactional(readOnly = true)
    public List<DerivedStat> findAll() {
        return derivedStatRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }
}