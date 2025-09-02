package com.apocalypse.thefall.controller.character.stats

import com.apocalypse.thefall.dto.character.stats.DataItemDto
import com.apocalypse.thefall.mapper.character.stats.skill.toListDataItemDto
import com.apocalypse.thefall.service.character.stats.SkillService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/skills")
class SkillController(
    private val skillService: SkillService
) {

    @GetMapping("/all")
    fun getAll(): List<DataItemDto> = skillService.findAll().toListDataItemDto()
}