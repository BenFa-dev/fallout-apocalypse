package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.Character;
import com.apocalypse.thefall.entity.character.stats.Special;
import com.apocalypse.thefall.entity.character.stats.SpecialEnum;
import com.apocalypse.thefall.entity.character.stats.SpecialInstance;
import com.apocalypse.thefall.repository.character.stats.SpecialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialService {

    private final SpecialRepository specialRepository;

    @Transactional(readOnly = true)
    public List<Special> findAll() {
        return specialRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }

    public Integer getSpecialValue(Character character, SpecialEnum specialEnum) {
        if (character.getSpecials() == null || character.getSpecials().isEmpty()) {
            return 0;
        }
        return character.getSpecials().stream()
                .filter(instance -> instance.getSpecial() != null
                        && specialEnum.equals(instance.getSpecial().getCode()))
                .map(SpecialInstance::getValue)
                .findFirst()
                .orElse(0);
    }
}