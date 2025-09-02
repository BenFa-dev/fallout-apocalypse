package com.apocalypse.thefall.config;

import com.apocalypse.thefall.service.GenerateMapService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameInitializer {

    private final GenerateMapService generateMapService;

    @PostConstruct
    public void init() {
        // Crée une carte par défaut si aucune n'existe
        generateMapService.getOrCreateMap();
    }
}