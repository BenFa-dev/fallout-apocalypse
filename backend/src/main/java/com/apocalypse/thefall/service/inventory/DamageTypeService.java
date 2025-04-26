package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.item.DamageType;
import com.apocalypse.thefall.repository.DamageTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DamageTypeService {

    private final DamageTypeRepository damageTypeRepository;

    @Transactional(readOnly = true)
    public List<DamageType> findAll() {
        return damageTypeRepository.findAll();
    }

}