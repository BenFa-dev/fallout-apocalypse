package com.apocalypse.thefall.controller;

import com.apocalypse.thefall.dto.MapDto;
import com.apocalypse.thefall.dto.TileDto;
import com.apocalypse.thefall.mapper.MapMapper;
import com.apocalypse.thefall.mapper.TileMapper;
import com.apocalypse.thefall.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    private final MapMapper mapMapper;
    private final TileMapper tileMapper;

    @GetMapping("/{mapId}")
    public MapDto getMap(@PathVariable Long mapId) {
        return mapMapper.toDto(mapService.getMap(mapId));
    }

    @GetMapping("/{mapId}/tiles")
    public List<TileDto> getTiles(@AuthenticationPrincipal Jwt jwt, @PathVariable Long mapId) {
        return mapService.getInitialTiles(jwt.getSubject(), mapId).stream()
                .map(tileMapper::toDto)
                .toList();
    }

    @GetMapping("/{mapId}/discover")
    public List<TileDto> discoverTiles(@AuthenticationPrincipal Jwt jwt, @PathVariable Long mapId) {
        return mapService.discoverNewVisibleTiles(jwt.getSubject(), mapId).stream()
                .map(tileMapper::toDto)
                .toList();
    }
}