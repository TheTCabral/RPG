package com.aetherhold.domain.shared;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class DiceRollerTest {

    private DiceRoller diceRoller;

    @BeforeEach
    void setUp() {
        diceRoller = new DiceRoller(42);
    }

    @Test
    void d20ShouldReturnBetween1And20() {
        for (int i = 0; i < 1000; i++) {
            int result = diceRoller.d20();
            assertThat(result).isBetween(1, 20);
        }
    }

    @Test
    void d6ShouldReturnBetween1And6() {
        for (int i = 0; i < 100; i++) {
            int result = diceRoller.d6();
            assertThat(result).isBetween(1, 6);
        }
    }

    @Test
    void rollWithNotationShouldWork() {
        int result = diceRoller.roll("2d6");
        assertThat(result).isBetween(2, 12);
    }

    @Test
    void rollInvalidNotationShouldThrow() {
        assertThatThrownBy(() -> diceRoller.roll("invalid"))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void chanceShouldReturnBetween0And1() {
        for (int i = 0; i < 100; i++) {
            double result = diceRoller.chance();
            assertThat(result).isBetween(0.0, 1.0);
        }
    }

    @Test
    void sameSeedShouldProduceSameResults() {
        DiceRoller roller1 = new DiceRoller(123);
        DiceRoller roller2 = new DiceRoller(123);

        for (int i = 0; i < 100; i++) {
            assertThat(roller1.d20()).isEqualTo(roller2.d20());
        }
    }
}

