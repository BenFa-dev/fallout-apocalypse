package com.apocalypse.thefall.service.character.stats;

import com.apocalypse.thefall.entity.character.stats.Condition;
import com.apocalypse.thefall.repository.character.stats.ConditionRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class ConditionService {

    private final ConditionRepository conditionRepository;

    @Transactional(readOnly = true)
    public List<Condition> findAll() {
        return conditionRepository.findAllByVisibleTrueOrderByDisplayOrderAsc();
    }
}