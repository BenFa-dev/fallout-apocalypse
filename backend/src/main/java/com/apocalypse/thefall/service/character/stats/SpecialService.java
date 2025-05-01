package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Special;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialInstance;
import com.apocalypse.thefall.repository.character.stats.SpecialRepository;
import com.apocalypse.thefall.util.MapUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SpecialService {

    private final SpecialRepository specialRepository;

    @Transactional(readOnly = true)
    public List<Special> findAll() {
        return specialRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }

    public Map<SpecialEnum, Integer> getSpecialValuesMap(Character character) {
        return MapUtils.extractMap(
                character.getSpecials(),
                instance -> instance.getSpecial() != null ? instance.getSpecial().getCode() : null,
                SpecialInstance::getValue
        );
    }

}