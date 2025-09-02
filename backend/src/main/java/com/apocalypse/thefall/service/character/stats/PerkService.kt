package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.repository.character.stats.PerkRepository;
import com.apocalypse.thefall.service.character.rules.perk.PerkEngine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerkService {

    private final PerkRepository perkRepository;
    private final PerkEngine perkEngine;

    @Transactional(readOnly = true)
    public List<Perk> findAll() {
        return perkRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }

    @Transactional(readOnly = true)
    public List<Perk> findAvailablePerks(Character character) {
        List<Perk> perks = perkRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
        perkEngine.compute(perks, character);
        return perks;
    }
}