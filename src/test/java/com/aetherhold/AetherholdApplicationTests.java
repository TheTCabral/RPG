package com.aetherhold;

import com.aetherhold.domain.shared.DiceRoller;
import com.aetherhold.infrastructure.config.GameProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AetherholdApplicationTests {

    @Autowired
    private GameProperties gameProperties;

    @Autowired
    private DiceRoller diceRoller;

    @Test
    void contextLoads() {
        assertThat(gameProperties).isNotNull();
    }

    @Test
    void gamePropertiesShouldLoadCorrectly() {
        assertThat(gameProperties.getDungeon().getFloors()).isEqualTo(10);
        assertThat(gameProperties.getDungeon().getSeed()).isEqualTo(42);
        assertThat(gameProperties.getDungeon().getRoomsPerFloor()).isEqualTo(12);
        assertThat(gameProperties.getCharacter().getStartingGold()).isEqualTo(50);
    }

    @Test
    void diceRollerBeanShouldBeCreated() {
        assertThat(diceRoller).isNotNull();
        assertThat(diceRoller.d20()).isBetween(1, 20);
    }
}

