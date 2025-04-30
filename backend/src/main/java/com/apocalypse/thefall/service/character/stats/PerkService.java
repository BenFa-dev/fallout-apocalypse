package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.stats.Perk;
import com.apocalypse.thefall.repository.character.stats.PerkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PerkService {

    private final PerkRepository perkRepository;

    @Transactional(readOnly = true)
    public List<Perk> findAll() {
        return perkRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }

}