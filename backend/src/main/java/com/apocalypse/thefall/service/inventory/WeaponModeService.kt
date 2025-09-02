package com.apocalypse.thefall.service.inventory;

import com.apocalypse.thefall.entity.item.WeaponMode;
import com.apocalypse.thefall.exception.GameException;
import com.apocalypse.thefall.repository.iteminstance.WeaponModeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WeaponModeService {

    private final WeaponModeRepository weaponModeRepository;

    @Transactional(readOnly = true)
    public WeaponMode getWeaponModeById(Long weaponModeId) {
        return weaponModeRepository.findById(weaponModeId).orElseThrow(() -> new GameException("error.game.inventory.weaponModeNotFound", HttpStatus.NOT_FOUND));
    }

}