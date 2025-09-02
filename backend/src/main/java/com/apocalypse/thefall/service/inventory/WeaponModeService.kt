package com.apocalypse.thefall.service.inventory

import com.apocalypse.thefall.entity.item.WeaponMode
import com.apocalypse.thefall.exception.GameException
import com.apocalypse.thefall.repository.iteminstance.WeaponModeRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
open class WeaponModeService(
    private val weaponModeRepository: WeaponModeRepository
) {

    @Transactional(readOnly = true)
    open fun getWeaponModeById(weaponModeId: Long): WeaponMode {
        return weaponModeRepository.findById(weaponModeId)
            .orElseThrow { GameException("error.game.inventory.weaponModeNotFound", HttpStatus.NOT_FOUND) }
    }
}
