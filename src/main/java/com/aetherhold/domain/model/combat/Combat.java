package com.aetherhold.domain.model.combat;

import com.aetherhold.domain.shared.DiceRoller;
import com.aetherhold.domain.model.character.Character;

/**
 * Enum de ações disponíveis em combate.
 */
enum CombatAction {
    ATTACK("Atacar"),
    USE_SKILL("Usar Habilidade"),
    USE_ITEM("Usar Item"),
    DEFEND("Defender"),
    FLEE("Fugir");

    private final String displayName;

    CombatAction(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

/**
 * Record de resultado de ataque.
 */
record AttackResult(
    int rollValue,           // Resultado do d20
    int targetDefense,       // Defesa do alvo
    boolean isHit,           // Conectou ou errou
    int damageDealt,         // Dano final (0 se erro)
    boolean isCritical,      // Crítico?
    String message           // Mensagem para log
) {
    public static AttackResult miss(int roll, int defense) {
        return new AttackResult(roll, defense, false, 0, false, "ERRO! O ataque errou.");
    }

    public static AttackResult hit(int roll, int damage) {
        return new AttackResult(roll, roll, true, damage, false, String.format("Conecta! Dano: %d", damage));
    }

    public static AttackResult critical(int roll, int damage) {
        return new AttackResult(roll, roll, true, damage, true, String.format("CRÍTICO! Dano: %d", damage));
    }
}

/**
 * Calculadora de dano — lógica pura de combate.
 * Todos os métodos são estáticos (sem estado).
 */
class DamageCalculator {

    /**
     * Resolve um ataque completo.
     * Retorna resultado com hit/miss, dano e crítico.
     */
    public static AttackResult resolve(Character attacker, Character target, CombatAction action, DiceRoller dice) {
        if (action != CombatAction.ATTACK) {
            throw new IllegalArgumentException("DamageCalculator apenas resolve ataques básicos");
        }

        // Roll do ataque (d20 + bônus)
        int roll = dice.d20() + attacker.getAttackBonus();
        int targetDefense = target.getDefense();

        // Miss check
        if (roll < targetDefense) {
            return AttackResult.miss(roll, targetDefense);
        }

        // Calcular dano base
        int baseDamage = calculateBaseDamage(attacker, dice);

        // Aplicar efeitos de status no dano
        double damageMultiplier = 1.0;
        boolean stunned = false;

        for (var effect : target.getActiveEffects()) {
            if (effect.getName().equals("Atordoado") && effect.isActive()) {
                stunned = true;
            }
        }

        // Atordoado reduz dano recebido
        if (stunned) {
            damageMultiplier *= 0.5;
        }

        int finalDamage = (int) (baseDamage * damageMultiplier);
        finalDamage = Math.max(0, finalDamage - target.getArmor());

        // Crítico check (natural 20 ou classe específica)
        boolean isCritical = (roll == 20) || checkClassCritical(attacker, dice);

        if (isCritical) {
            finalDamage *= 2;
            return AttackResult.critical(roll, finalDamage);
        }

        return AttackResult.hit(roll, finalDamage);
    }

    /**
     * Calcula o dano base de um ataque (sem crítico, sem efeitos).
     */
    private static int calculateBaseDamage(Character attacker, DiceRoller dice) {
        // Dano base: d6 + modificador de força
        int baseDamage = dice.d6() + attacker.getStats().strengthModifier();
        return Math.max(1, baseDamage);  // Mínimo 1 de dano
    }

    /**
     * Verifica se a classe do atacante tem chance de crítico especial.
     * Ex: Rogue tem 25% de chance base.
     */
    private static boolean checkClassCritical(Character attacker, DiceRoller dice) {
        String className = attacker.getCharacterClass().name();

        if (className.equals("ROGUE")) {
            return dice.chance() < 0.25;
        }

        return false;
    }
}

/**
 * Calculadora de iniciativa — determina ordem de ação em combate.
 */
class InitiativeCalculator {

    /**
     * Sorts combatentes por iniciativa (maior primeiro).
     * Iniciativa = AGI + 1d6.
     */
    public static class Combatant {
        public final Character character;
        public final int initiative;

        public Combatant(Character character, int initiative) {
            this.character = character;
            this.initiative = initiative;
        }
    }

    public static Combatant[] sort(Character hero, Character enemy, DiceRoller dice) {
        int heroInitiative = hero.getStats().agility() + dice.d6();
        int enemyInitiative = enemy.getStats().agility() + dice.d6();

        Combatant heroCombatant = new Combatant(hero, heroInitiative);
        Combatant enemyCombatant = new Combatant(enemy, enemyInitiative);

        // Ordenar descrescente
        if (heroInitiative >= enemyInitiative) {
            return new Combatant[]{heroCombatant, enemyCombatant};
        } else {
            return new Combatant[]{enemyCombatant, heroCombatant};
        }
    }
}

