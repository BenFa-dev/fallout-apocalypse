package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.item.DamageType;
import com.apocalypse.thefall.entity.item.enums.DamageTypeEnum;
import com.apocalypse.thefall.repository.DamageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DamageTypeService {

    private final DamageTypeRepository damageTypeRepository;

    @Transactional(readOnly = true)
    public List<DamageType> findAll() {
        return damageTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Map<DamageTypeEnum, DamageType> getDamageTypeMap() {
        return findAll().stream()
                .collect(Collectors.toMap(DamageType::getCode, Function.identity()));
    }
}