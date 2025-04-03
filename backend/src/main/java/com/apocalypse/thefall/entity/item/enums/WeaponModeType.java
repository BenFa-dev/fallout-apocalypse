package com.apocalypse.thefall.entity.item.enums;

public enum WeaponModeType {
    SINGLE, // Mode standard pour les armes à feu
    AIMED, // Mode visé (consomme plus d'AP mais plus précis)
    BURST, // Mode rafale (plusieurs tirs, plus de dégâts)
    THROW, // Pour les armes de jet
    SWING, // Pour les armes de mêlée
    THRUST, // Pour les armes de mêlée (coup d'estoc)
    PUNCH // Pour les armes de combat à mains nues
}