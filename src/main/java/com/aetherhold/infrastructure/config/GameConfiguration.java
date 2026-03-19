package com.aetherhold.infrastructure.config;

import com.aetherhold.domain.shared.DiceRoller;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GameProperties.class)
@RequiredArgsConstructor
public class GameConfiguration {

    private final GameProperties gameProperties;

    /**
     * Bean DiceRoller — usa seed da configuração.
     * O DiceRoller em si não depende de Spring (é Java puro).
     */
    @Bean
    public DiceRoller diceRoller() {
        // Lombok gera getters automáticamente (@Getter)
        return new DiceRoller(gameProperties.getDungeon().getSeed());
    }
}

