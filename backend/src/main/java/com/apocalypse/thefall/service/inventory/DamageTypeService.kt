package com.apocalypse.thefall.service.inventory

import com.apocalypse.thefall.entity.item.DamageType
import com.apocalypse.thefall.entity.item.enums.DamageTypeEnum
import com.apocalypse.thefall.repository.DamageTypeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class DamageTypeService(
    private val damageTypeRepository: DamageTypeRepository
) {

    @Transactional(readOnly = true)
    open fun findAll(): List<DamageType> {
        return damageTypeRepository.findAll()
    }

    @Transactional(readOnly = true)
    open fun getDamageTypeMap(): Map<DamageTypeEnum, DamageType> {
        return findAll().associateBy { it.code }
    }
}
