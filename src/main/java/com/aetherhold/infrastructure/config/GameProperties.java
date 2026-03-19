package com.aetherhold.infrastructure.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "game")
public class GameProperties {
    private DungeonProperties dungeon = new DungeonProperties();
    private CombatProperties combat = new CombatProperties();
    private CharacterProperties character = new CharacterProperties();

    @Getter
    @Setter
    public static class DungeonProperties {
        private int floors = 10;
        private long seed = 42;
        private int roomsPerFloor = 12;
    }

    @Getter
    @Setter
    public static class CombatProperties {
        private double baseCritChance = 0.05;
        private double fleeSuccessChance = 0.40;
    }

    @Getter
    @Setter
    public static class CharacterProperties {
        private int startingGold = 50;
    }
}

