package com.aetherhold.domain.model.enemy;

import com.aetherhold.domain.model.character.Character;
import com.aetherhold.domain.model.character.CharacterClass;
import com.aetherhold.domain.model.character.Stats;
import com.aetherhold.domain.model.item.Item;
import com.aetherhold.domain.model.skill.Skill;

import java.util.*;

/**
 * Enum de tipos de comportamento de inimigos.
 */
enum BehaviorType {
    AGGRESSIVE("Agressivo"),
    DEFENSIVE("Defensivo"),
    STEALTHY("Furtivo"),
    BOSS("Boss");

    private final String displayName;

    BehaviorType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

/**
 * Classe de inimigo — combatente não-jogador.
 * Herda de Character, mas com campos adicionais para IA e loot.
 */
public class Enemy extends Character {
    private final BehaviorType behavior;
    private final List<Item> lootTable;
    private final int minFloor;  // Nível mínimo do dungeon onde aparece
    private final int maxFloor;  // Nível máximo

    public Enemy(
        UUID id,
        String name,
        CharacterClass characterClass,
        int hp,
        int maxHp,
        Stats stats,
        List<Skill> skills,
        BehaviorType behavior,
        List<Item> lootTable,
        int minFloor,
        int maxFloor
    ) {
        super(id, name, characterClass, hp, maxHp, stats, skills, 0);
        this.behavior = behavior;
        this.lootTable = new ArrayList<>(lootTable);
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
    }

    public BehaviorType getBehavior() {
        return behavior;
    }

    public List<Item> getLootTable() {
        return new ArrayList<>(lootTable);
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    /**
     * Verifica se o inimigo pode aparecer em um nível específico.
     */
    public boolean canAppearOn(int floor) {
        return floor >= minFloor && floor <= maxFloor;
    }

    @Override
    protected void applyLevelUpBonus(Stats newStats) {
        // Inimigos não fazem level up durante o jogo
    }
}

/**
 * Factory para criar inimigos pré-configurados.
 * Não é um bean Spring — é instanciado manualmente na configuração.
 */
class EnemyFactory {

    /**
     * Cria um inimigo genérico com parâmetros customizados.
     */
    public Enemy createEnemy(
        String name,
        CharacterClass characterClass,
        int hp,
        int strength,
        int agility,
        int intelligence,
        int defense,
        int mana,
        BehaviorType behavior,
        List<Item> loot,
        int minFloor,
        int maxFloor
    ) {
        UUID id = UUID.randomUUID();
        Stats stats = new Stats(strength, agility, intelligence, defense, mana, mana, 1, 0, 100);
        return new Enemy(
            id, name, characterClass, hp, hp, stats,
            new ArrayList<>(),  // Skills vazias por padrão
            behavior, loot, minFloor, maxFloor
        );
    }

    /**
     * Cria um Goblin Saqueador (inimigo fraco, aparece cedo).
     */
    public Enemy createGoblin() {
        return createEnemy(
            "Goblin Saqueador",
            CharacterClass.WARRIOR,
            30, 8, 12, 6, 6, 10,
            BehaviorType.AGGRESSIVE,
            new ArrayList<>(),
            1, 4
        );
    }

    /**
     * Cria um Esqueleto (inimigo médio).
     */
    public Enemy createSkeleton() {
        return createEnemy(
            "Esqueleto Guerreiro",
            CharacterClass.WARRIOR,
            45, 10, 8, 4, 10, 20,
            BehaviorType.AGGRESSIVE,
            new ArrayList<>(),
            2, 6
        );
    }

    /**
     * Cria um Vampiro (inimigo forte, ágil).
     */
    public Enemy createVampire() {
        return createEnemy(
            "Vampiro das Sombras",
            CharacterClass.ROGUE,
            70, 14, 16, 10, 8, 40,
            BehaviorType.STEALTHY,
            new ArrayList<>(),
            5, 8
        );
    }

    /**
     * Cria um Cavaleiro Lich (inimigo muito forte).
     */
    public Enemy createLichKnight() {
        return createEnemy(
            "Cavaleiro Lich",
            CharacterClass.MAGE,
            90, 16, 10, 16, 14, 80,
            BehaviorType.DEFENSIVE,
            new ArrayList<>(),
            7, 9
        );
    }

    /**
     * Cria o BOSS final — Malachar, o Devorador de Almas.
     */
    public Enemy createMalachar() {
        return createEnemy(
            "Malachar, o Devorador",
            CharacterClass.MAGE,
            300, 22, 14, 24, 18, 200,
            BehaviorType.BOSS,
            new ArrayList<>(),
            10, 10
        );
    }
}

