package com.aetherhold.domain.model.status;

import com.aetherhold.domain.model.character.Character;

/**
 * Classe abstrata base para todos os efeitos de status.
 * Status afetam o personagem durante combate.
 */
public abstract class StatusEffect {
    protected final String name;
    protected final String description;
    protected int duration;  // Turnos restantes

    public StatusEffect(String name, String description, int duration) {
        this.name = name;
        this.description = description;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDuration() {
        return duration;
    }

    public boolean isActive() {
        return duration > 0;
    }

    /**
     * Executa o efeito a cada turno do personagem afetado.
     */
    public abstract void onTick(Character target);

    /**
     * Decrementa a duração do efeito.
     */
    public void tick() {
        if (duration > 0) {
            duration--;
        }
    }

    /**
     * Retorna true se o efeito expirou.
     */
    public boolean isExpired() {
        return duration <= 0;
    }
}

/**
 * Efeito de envenenamento — causa dano a cada turno.
 */
class Poisoned extends StatusEffect {
    private static final int DAMAGE_PER_TURN = 3;

    public Poisoned(int duration) {
        super("Envenenado", "Sofre dano a cada turno", duration);
    }

    @Override
    public void onTick(Character target) {
        target.takeDamage(DAMAGE_PER_TURN);
    }
}

/**
 * Efeito de atordoamento — reduz ataques.
 */
class Stunned extends StatusEffect {
    public Stunned(int duration) {
        super("Atordoado", "Não consegue atacar corretamente", duration);
    }

    @Override
    public void onTick(Character target) {
        // Efeito aplicado durante cálculo de dano (reduz 50% do dano)
    }

    public boolean shouldPreventAction() {
        return true;  // Personagem atordoado não pode agir
    }
}

/**
 * Efeito de bênção — aumenta o dano.
 */
class Blessed extends StatusEffect {
    private static final double DAMAGE_MULTIPLIER = 1.25;

    public Blessed(int duration) {
        super("Abençoado", "Dano aumentado em 25%", duration);
    }

    @Override
    public void onTick(Character target) {
        // Efeito aplicado durante cálculo de dano
    }

    public double getDamageMultiplier() {
        return DAMAGE_MULTIPLIER;
    }
}

/**
 * Efeito de maldição — reduz o dano.
 */
class Cursed extends StatusEffect {
    private static final double DAMAGE_MULTIPLIER = 0.75;

    public Cursed(int duration) {
        super("Maldito", "Dano reduzido em 25%", duration);
    }

    @Override
    public void onTick(Character target) {
        // Efeito aplicado durante cálculo de dano
    }

    public double getDamageMultiplier() {
        return DAMAGE_MULTIPLIER;
    }
}

