package com.apocalypse.thefall.controller;

import com.apocalypse.thefall.dto.MapDto;
import com.apocalypse.thefall.mapper.MapMapper;
import com.apocalypse.thefall.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class MapController {
    private final MapService mapService;
    private final MapMapper mapMapper;

    @GetMapping("/{mapId}")
    public MapDto getMapTiles(@PathVariable Long mapId) {
        return mapMapper.toDto(mapService.getMap(mapId));
    }
}