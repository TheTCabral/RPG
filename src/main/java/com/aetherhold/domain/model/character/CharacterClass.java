package com.aetherhold.domain.model.character;

/**
 * Enum das 7 classes de personagem em Aetherhold.
 * Cada classe tem um nome para exibição e descrição de lore.
 */
public enum CharacterClass {
    DWARF("Anão", "Guerreiro robusto das montanhas. Mestre em defesa e resistência."),
    ARCHER("Arqueiro", "Aventureiro ágil com precisão letal. Especialista em fuga e mobilidade."),
    BARD("Bardo", "Músico versátil que cura e inspira. Mestre em suporte e regeneração."),
    WARRIOR("Guerreiro", "Campeão valente com força impressionante. Especialista em ataque e HP."),
    ROGUE("Ladino", "Sombra silenciosa com reflexos afiados. Mestre em crítico e evasão."),
    MAGE("Mago", "Erudito das artes arcanas. Especialista em magia e mana."),
    PALADIN("Paladino", "Campeão sagrado que cura ao atacar. Mestre em equilíbrio de ataque e defesa.");

    private final String displayName;
    private final String lore;

    CharacterClass(String displayName, String lore) {
        this.displayName = displayName;
        this.lore = lore;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLore() {
        return lore;
    }
}

