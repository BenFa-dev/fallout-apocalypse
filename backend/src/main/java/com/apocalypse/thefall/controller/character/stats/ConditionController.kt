package com.apocalypse.thefall.controller.character.stats

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.mapper.character.stats.condition.toListDataItemDto
import com.apocalypse.thefall.service.character.stats.ConditionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/conditions")
class ConditionController(
    private val conditionService: ConditionService
) {

    @GetMapping("/all")
    fun getAll(): List<DataItemDto> = conditionService.findAll().toListDataItemDto()
}
