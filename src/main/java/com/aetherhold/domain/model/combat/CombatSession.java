package com.aetherhold.domain.model.combat;

import com.aetherhold.domain.model.character.Character;
import com.aetherhold.domain.model.enemy.Enemy;

import java.util.*;

/**
 * Sessão de combate — encapsula estado completo de uma batalha.
 */
public class CombatSession {
    private final UUID sessionId;
    private final Character hero;
    private final Enemy enemy;
    private int turn;
    private final List<String> combatLog;
    private boolean heroDefending;
    private boolean combatOver;
    private Character victor;  // null enquanto o combate prossegue

    public CombatSession(UUID sessionId, Character hero, Enemy enemy) {
        this.sessionId = sessionId;
        this.hero = hero;
        this.enemy = enemy;
        this.turn = 1;
        this.combatLog = new ArrayList<>();
        this.heroDefending = false;
        this.combatOver = false;
        this.victor = null;

        logMessage("=== COMBATE INICIADO ===");
        logMessage(String.format("%s enfrenta %s!", hero.getName(), enemy.getName()));
    }

    // ===== GETTERS =====

    public UUID getSessionId() {
        return sessionId;
    }

    public Character getHero() {
        return hero;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public int getTurn() {
        return turn;
    }

    public List<String> getCombatLog() {
        return new ArrayList<>(combatLog);
    }

    public boolean isHeroDefending() {
        return heroDefending;
    }

    public boolean isOver() {
        return combatOver;
    }

    public Character getVictor() {
        return victor;
    }

    // ===== COMBAT FLOW =====

    /**
     * Aplica resultado de um ataque ao alvo.
     */
    public void applyAttackResult(AttackResult result, Character attacker, Character target) {
        if (result.isHit()) {
            int actualDamage = target.takeDamage(result.damageDealt());
            String message = String.format(
                "%s ataca %s! %s (dano: %d)",
                attacker.getName(),
                target.getName(),
                result.message(),
                actualDamage
            );
            logMessage(message);

            if (result.isCritical()) {
                logMessage("*** CRÍTICO! ***");
            }
        } else {
            logMessage(String.format("%s ataca %s, mas erra!", attacker.getName(), target.getName()));
        }

        checkCombatEnd();
    }

    /**
     * Próximo turno — incrementa contador e aplica efeitos de status.
     */
    public void nextTurn() {
        turn++;
        logMessage(String.format("--- Turno %d ---", turn));

        // Resetar defesa
        heroDefending = false;

        // Tick de efeitos de status em ambos
        hero.tickStatusEffects();
        enemy.tickStatusEffects();

        checkCombatEnd();
    }

    /**
     * Marca que herói vai defender (reduz dano neste turno).
     */
    public void setHeroDefending(boolean defending) {
        this.heroDefending = defending;
        if (defending) {
            logMessage(hero.getName() + " assume postura defensiva!");
        }
    }

    /**
     * Verifica se o combate terminou (alguém morreu).
     */
    private void checkCombatEnd() {
        if (!hero.isAlive()) {
            combatOver = true;
            victor = enemy;
            logMessage("=== DERROTA ===");
            logMessage(hero.getName() + " foi derrotado!");
        } else if (!enemy.isAlive()) {
            combatOver = true;
            victor = hero;
            logMessage("=== VITÓRIA ===");
            logMessage(hero.getName() + " venceu o combate!");
        }
    }

    /**
     * Adiciona mensagem ao log de combate.
     */
    public void logMessage(String message) {
        combatLog.add(message);
    }

    /**
     * Retorna as últimas N linhas do log.
     */
    public List<String> getRecentLog(int lines) {
        int start = Math.max(0, combatLog.size() - lines);
        return new ArrayList<>(combatLog.subList(start, combatLog.size()));
    }

    @Override
    public String toString() {
        return String.format(
            "CombatSession{%s vs %s, turn=%d, over=%s}",
            hero.getName(), enemy.getName(), turn, combatOver
        );
    }
}

