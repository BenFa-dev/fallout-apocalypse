package com.apocalypse.thefall.controller.character.stats

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.mapper.character.stats.damagetype.toListDataItemDto
import com.apocalypse.thefall.service.inventory.DamageTypeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/damage-types")
class DamageTypeController(
    private val damageTypeService: DamageTypeService
) {

    @GetMapping("/all")
    fun getAll(): List<DataItemDto> = damageTypeService.findAll().toListDataItemDto()
}
