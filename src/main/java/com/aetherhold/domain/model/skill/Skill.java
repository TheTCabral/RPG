package com.aetherhold.domain.model.skill;

import com.aetherhold.domain.model.character.Character;
import com.aetherhold.domain.model.character.Stats;

/**
 * Classe abstrata base para todas as habilidades do jogo.
 * Pode ser ativa, passiva, ultimate ou relíquia de classe.
 */
public abstract class Skill {
    protected final String name;
    protected final String description;
    protected final int manaCost;

    public Skill(String name, String description, int manaCost) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getManaCost() {
        return manaCost;
    }

    /**
     * Verifica se a habilidade pode ser ativada (tem mana suficiente)
     */
    public boolean canActivate(Character caster) {
        return caster.getStats().mana() >= manaCost;
    }
}

/**
 * Habilidade ativa — pode ser usada em combate pelo jogador ou inimigo.
 */
abstract class ActiveSkill extends Skill {
    public ActiveSkill(String name, String description, int manaCost) {
        super(name, description, manaCost);
    }

    /**
     * Executa a habilidade no alvo.
     * Retorna um objeto com resultado (dano, cura, status, etc).
     */
    public abstract SkillResult execute(Character caster, Character target);
}

/**
 * Resultado da execução de uma habilidade ativa.
 */
record SkillResult(
    int damageDealt,
    int healingDone,
    String statusApplied,  // null se sem status
    String message
) {
    public static SkillResult damage(int damage, String message) {
        return new SkillResult(damage, 0, null, message);
    }

    public static SkillResult healing(int healing, String message) {
        return new SkillResult(0, healing, null, message);
    }

    public static SkillResult damageWithStatus(int damage, String status, String message) {
        return new SkillResult(damage, 0, status, message);
    }
}

/**
 * Habilidade passiva — aplicada permanentemente ao personagem.
 * Modifica stats ou comportamento do personagem.
 */
abstract class PassiveSkill extends Skill {
    public PassiveSkill(String name, String description) {
        super(name, description, 0);
    }

    /**
     * Aplica modificadores de stats.
     * Retorna novo Stats com modificadores aplicados.
     */
    public abstract Stats applyModifier(Stats baseStats);
}

/**
 * Habilidade ultimate — usada uma vez por combate, custa muita mana.
 */
abstract class UltimateSkill extends ActiveSkill {
    private boolean used = false;

    public UltimateSkill(String name, String description, int manaCost) {
        super(name, description, manaCost);
    }

    @Override
    public boolean canActivate(Character caster) {
        return !used && super.canActivate(caster);
    }

    public void markAsUsed() {
        this.used = true;
    }

    public void resetUsage() {
        this.used = false;
    }

    public boolean isUsed() {
        return used;
    }
}

/**
 * Relíquia de classe — habilidade passiva ou ativa exclusiva de uma classe.
 */
abstract class ClassRelic extends Skill {
    public ClassRelic(String name, String description) {
        super(name, description, 0);
    }
}

