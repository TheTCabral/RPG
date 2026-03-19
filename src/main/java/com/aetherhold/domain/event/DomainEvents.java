package com.aetherhold.domain.event;

import com.aetherhold.domain.model.character.Character;
import com.aetherhold.domain.model.enemy.Enemy;

import java.time.Instant;
import java.util.UUID;

/**
 * Eventos de domínio — Records imutáveis que representam fatos acontecidos.
 * Publicados pela infraestrutura após ações do domínio.
 */

/**
 * Combate iniciado.
 */
record CombatStartedEvent(
    UUID combatSessionId,
    Character hero,
    Enemy enemy,
    Instant timestamp
) {
}

/**
 * Personagem morreu.
 */
record CharacterDiedEvent(
    Character character,
    Character killer,
    Instant timestamp
) {
}

/**
 * Personagem subiu de nível.
 */
record LevelUpEvent(
    Character character,
    int newLevel,
    Instant timestamp
) {
}

/**
 * Andar do dungeon foi completado.
 */
record DungeonFloorCompletedEvent(
    Character hero,
    int floorNumber,
    int experienceGained,
    Instant timestamp
) {
}

/**
 * Jogo foi ganho (boss derrotado).
 */
record GameWonEvent(
    Character hero,
    int totalFloors,
    Instant timestamp
) {
}

/**
 * Inimigo foi derrotado — evento para calcular loot.
 */
record EnemyDefeatedEvent(
    Character hero,
    Enemy enemy,
    int experience,
    int gold,
    Instant timestamp
) {
}

