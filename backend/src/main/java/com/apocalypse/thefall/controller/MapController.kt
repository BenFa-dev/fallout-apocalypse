package com.apocalypse.thefall.controller

import com.apocalypse.thefall.dto.MapDto
import com.apocalypse.thefall.dto.TileDto
import com.apocalypse.thefall.mapper.toDto
import com.apocalypse.thefall.service.MapService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/maps")
class MapController(
    private val mapService: MapService,
) {
    @GetMapping("/{mapId}")
    fun getMap(@PathVariable mapId: Long): MapDto = mapService.getMap(mapId).toDto()
    
    @GetMapping("/{mapId}/tiles")
    fun getTiles(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable mapId: Long
    ): List<TileDto> = mapService.getInitialTiles(jwt.subject, mapId).map { it.toDto() }

    @GetMapping("/{mapId}/discover")
    fun discoverTiles(
        @AuthenticationPrincipal jwt: Jwt,
        @PathVariable mapId: Long
    ): List<TileDto> = mapService.discoverNewVisibleTiles(jwt.subject, mapId).map { it.toDto() }

}