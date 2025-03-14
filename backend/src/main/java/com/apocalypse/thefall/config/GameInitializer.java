package com.apocalypse.thefall.config;

import com.apocalypse.thefall.service.MapService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameInitializer {

    private final MapService mapService;

    @PostConstruct
    public void init() {
        // Crée une carte par défaut si aucune n'existe
        mapService.getOrCreateMap();
    }
}