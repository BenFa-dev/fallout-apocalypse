package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.stats.DerivedStat;
import com.apocalypse.thefall.entity.character.stats.DerivedStatInstance;
import com.apocalypse.thefall.repository.character.stats.DerivedStatRepository;
import com.apocalypse.thefall.service.character.rules.RuleEngine;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
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
public class DerivedStatService {

    private final DerivedStatRepository derivedStatRepository;
    private final RuleEngine ruleEngine;
    private Set<DerivedStatInstance> cachedDerivedStatsInstance = new HashSet<>();

    @PostConstruct
    public void init() {
        var derivedStats = derivedStatRepository.findAll();
        ruleEngine.preload(derivedStats);

        // Pré-initialisation des instances avec base = 0, car pas d'existence en BDD
        this.cachedDerivedStatsInstance = derivedStats.stream()
                .map(stat -> DerivedStatInstance.builder()
                        .derivedStat(stat)
                        .value(0)
                        .build())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    @Transactional(readOnly = true)
    public List<DerivedStat> findAll() {
        return derivedStatRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }
}