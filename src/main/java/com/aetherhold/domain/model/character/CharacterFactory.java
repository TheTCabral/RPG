package com.aetherhold.domain.model.character;

import java.util.*;

/**
 * Factory para criar personagens das 7 classes.
 * Centraliza a criação de instâncias.
 */
public class CharacterFactory {

    public static Character createWarrior(UUID id, String name) {
        return new Warrior(id, name, new ArrayList<>());
    }

    public static Character createArcher(UUID id, String name) {
        return new Archer(id, name, new ArrayList<>());
    }

    public static Character createDwarf(UUID id, String name) {
        return new Dwarf(id, name, new ArrayList<>());
    }

    public static Character createBard(UUID id, String name) {
        return new Bard(id, name, new ArrayList<>());
    }

    public static Character createRogue(UUID id, String name) {
        return new Rogue(id, name, new ArrayList<>());
    }

    public static Character createMage(UUID id, String name) {
        return new Mage(id, name, new ArrayList<>());
    }

    public static Character createPaladin(UUID id, String name) {
        return new Paladin(id, name, new ArrayList<>());
    }
}

