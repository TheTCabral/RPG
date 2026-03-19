package com.aetherhold.domain.model.character;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class StatsTest {
    // ...existing code...
}

class CharacterTest {

    private Character warrior;

    @BeforeEach
    void setUp() {
        warrior = CharacterFactory.createWarrior(java.util.UUID.randomUUID(), "Thorin");
    }

    @Test
    void shouldStartAlive() {
        assertThat(warrior.isAlive()).isTrue();
        assertThat(warrior.getHp()).isGreaterThan(0);
    }

    // ...existing code...

    @Test
    void differentClassesShouldHaveDifferentStats() {
        Character dwarf = CharacterFactory.createDwarf(java.util.UUID.randomUUID(), "Gimli");
        Character archer = CharacterFactory.createArcher(java.util.UUID.randomUUID(), "Legolas");

        assertThat(dwarf.getMaxHp()).isGreaterThan(archer.getMaxHp());
        assertThat(archer.getStats().agility()).isGreaterThan(dwarf.getStats().agility());
    }
}

