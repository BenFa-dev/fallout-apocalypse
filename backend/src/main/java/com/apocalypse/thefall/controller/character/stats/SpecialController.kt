package com.apocalypse.thefall.controller.character.stats

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.mapper.character.stats.special.toListDataItemDto
import com.apocalypse.thefall.service.character.stats.SpecialService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/specials")
class SpecialController(
    private val specialService: SpecialService
) {

    @GetMapping("/all")
    fun getAll(): List<DataItemDto> = specialService.findAll().toListDataItemDto()
}