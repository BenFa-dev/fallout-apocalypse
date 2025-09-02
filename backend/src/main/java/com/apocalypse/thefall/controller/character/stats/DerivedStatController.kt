package com.apocalypse.thefall.controller.character.stats

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.mapper.character.stats.derived.toListDataItemDto
import com.apocalypse.thefall.service.character.stats.DerivedStatService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/derived-stats")
class DerivedStatController(
    private val derivedStatService: DerivedStatService
) {

    @GetMapping("/all")
    fun getAll(): List<DataItemDto> = derivedStatService.findAll().toListDataItemDto()
}