package com.aetherhold.domain.model.character;

/**
 * Registro imutável que representa os atributos de um personagem.
 * Todos os campos são finais e não podem ser alterados após criação.
 */
public record Stats(
    int strength,      // Força (dano de ataque)
    int agility,       // Agilidade (defesa, iniciativa)
    int intelligence,  // Inteligência (dano mágico)
    int defense,       // Defesa base
    int mana,          // Mana atual
    int maxMana,       // Mana máxima
    int level,         // Nível do personagem
    int currentXp,     // XP atual
    int xpToNextLevel  // XP necessário para próximo nível
) {

    /**
     * Calcula o modificador de atributo (modificador D&D padrão: (valor - 10) / 2)
     *
     * @param attributeValue valor do atributo
     * @return modificador (pode ser negativo)
     */
    public static int calculateModifier(int attributeValue) {
        return (attributeValue - 10) / 2;
    }

    /**
     * Retorna o modificador de força
     */
    public int strengthModifier() {
        return calculateModifier(strength);
    }

    /**
     * Retorna o modificador de agilidade
     */
    public int agilityModifier() {
        return calculateModifier(agility);
    }

    /**
     * Retorna o modificador de inteligência
     */
    public int intelligenceModifier() {
        return calculateModifier(intelligence);
    }

    /**
     * Retorna o modificador de constituição (usado para HP) — baseado em força para simplicidade
     */
    public int constitutionModifier() {
        return calculateModifier(strength);
    }

    /**
     * Cria um novo Stats com mana atualizada
     */
    public Stats withMana(int newMana) {
        return new Stats(
            strength, agility, intelligence, defense,
            Math.max(0, Math.min(newMana, maxMana)), maxMana,
            level, currentXp, xpToNextLevel
        );
    }

    /**
     * Cria um novo Stats com XP atualizado
     */
    public Stats withXp(int newXp) {
        return new Stats(
            strength, agility, intelligence, defense,
            mana, maxMana,
            level, newXp, xpToNextLevel
        );
    }

    /**
     * Cria um novo Stats com level aumentado
     */
    public Stats levelUp() {
        int newLevel = level + 1;
        int newXpToNextLevel = newLevel * 100;
        return new Stats(
            strength, agility, intelligence, defense,
            mana, maxMana,
            newLevel, 0, newXpToNextLevel
        );
    }
}

