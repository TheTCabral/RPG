package com.aetherhold.domain.model.character;

import com.aetherhold.domain.model.skill.Skill;
import com.aetherhold.domain.model.status.StatusEffect;

import java.util.*;

/**
 * Classe abstrata base para todos os personagens do jogo.
 * Contém lógica de HP, Stats, habilidades e efeitos de status.
 *
 * Esta classe é IMUTÁVEL — operações retornam nova instância.
 */
public abstract class Character {
    protected final UUID id;
    protected final String name;
    protected final CharacterClass characterClass;
    protected int hp;
    protected int maxHp;
    protected final Stats stats;
    protected final List<Skill> skills;
    protected final List<StatusEffect> activeEffects;
    protected int gold;

    public Character(
        UUID id,
        String name,
        CharacterClass characterClass,
        int hp,
        int maxHp,
        Stats stats,
        List<Skill> skills,
        int gold
    ) {
        this.id = id;
        this.name = name;
        this.characterClass = characterClass;
        this.hp = Math.max(0, Math.min(hp, maxHp));
        this.maxHp = maxHp;
        this.stats = stats;
        this.skills = new ArrayList<>(skills);
        this.activeEffects = new ArrayList<>();
        this.gold = gold;
    }

    // ===== GETTERS =====

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CharacterClass getCharacterClass() {
        return characterClass;
    }

    public int getHp() {
        return hp;
    }

    public int getMaxHp() {
        return maxHp;
    }

    public Stats getStats() {
        return stats;
    }

    public List<Skill> getSkills() {
        return new ArrayList<>(skills);
    }

    public List<StatusEffect> getActiveEffects() {
        return new ArrayList<>(activeEffects);
    }

    public int getGold() {
        return gold;
    }

    // ===== COMBAT MECHANICS =====

    /**
     * Calcula o bônus de ataque com base em força e nível.
     */
    public int getAttackBonus() {
        return (stats.strength() / 2) + stats.level();
    }

    /**
     * Calcula a defesa com base em agilidade.
     */
    public int getDefense() {
        return stats.defense() + (stats.agility() / 3);
    }

    /**
     * Calcula o modificador de dano mágico com base em inteligência.
     */
    public int getSpellBonus() {
        return (stats.intelligence() / 2) + stats.level();
    }

    /**
     * Calcula a armadura (redução de dano).
     * Padrão: 0. Pode ser sobrescrito por subclasses ou itens.
     */
    public int getArmor() {
        return 0;
    }

    /**
     * Aplica dano ao personagem.
     * Retorna dano real recebido após armadura.
     */
    public int takeDamage(int rawDamage) {
        // Aplicar efeitos de status que reduzem dano
        double multiplier = 1.0;
        for (StatusEffect effect : activeEffects) {
            if (effect.getName().equals("Abençoado") && effect.isActive()) {
                multiplier *= 1.25;  // Bênção aumenta dano recebido (bom para inimigos!)
            } else if (effect.getName().equals("Maldito") && effect.isActive()) {
                multiplier *= 0.75;  // Maldição reduz dano recebido
            }
        }

        int damage = Math.max(0, rawDamage - getArmor());
        damage = (int) (damage * multiplier);
        hp = Math.max(0, hp - damage);

        if (!isAlive()) {
            onDeath();
        }

        return damage;
    }

    /**
     * Cura o personagem.
     */
    public int heal(int amount) {
        int healed = amount;
        hp = Math.min(maxHp, hp + amount);
        return healed;
    }

    /**
     * Verifica se o personagem está vivo.
     */
    public boolean isAlive() {
        return hp > 0;
    }

    /**
     * Hook chamado quando o personagem morre.
     */
    protected void onDeath() {
        // Pode ser sobrescrito em subclasses
    }

    // ===== XP AND LEVELING =====

    /**
     * Ganha experiência.
     * Retorna true se level up aconteceu.
     */
    public boolean gainXp(int amount) {
        Stats newStats = stats.withXp(stats.currentXp() + amount);

        if (newStats.currentXp() >= newStats.xpToNextLevel()) {
            levelUp();
            return true;
        }

        return false;
    }

    /**
     * Sobe um nível.
     */
    public void levelUp() {
        Stats newStats = stats.levelUp();

        // Aumentar stats base conforme a classe
        applyLevelUpBonus(newStats);

        // Restaurar HP e Mana ao subir de nível
        heal(getMaxHp());  // Full heal
    }

    /**
     * Sobrescrito em subclasses para aplicar bonus específicos de classe ao level up.
     */
    protected abstract void applyLevelUpBonus(Stats newStats);

    // ===== STATUS EFFECTS =====

    /**
     * Aplica um efeito de status.
     */
    public void applyStatus(StatusEffect effect) {
        // Não permitir duplicatas — substituir se já existe
        activeEffects.removeIf(e -> e.getName().equals(effect.getName()));
        activeEffects.add(effect);
    }

    /**
     * Remove um efeito de status.
     */
    public void removeStatus(String effectName) {
        activeEffects.removeIf(e -> e.getName().equals(effectName));
    }

    /**
     * Executa tick de todos os efeitos ativos.
     * Chamado no início do turno do personagem.
     */
    public void tickStatusEffects() {
        activeEffects.forEach(StatusEffect::tick);
        activeEffects.forEach(effect -> {
            if (effect.isActive()) {
                effect.onTick(this);
            }
        });
        activeEffects.removeIf(StatusEffect::isExpired);
    }

    /**
     * Verifica se o personagem está atordoado.
     */
    public boolean isStunned() {
        return activeEffects.stream()
            .anyMatch(e -> e.getName().equals("Atordoado") && e.isActive());
    }

    // ===== ABSTRACT METHODS =====

    /**
     * Retorna o nome exibível da classe.
     */
    public String getClassDisplayName() {
        return characterClass.getDisplayName();
    }

    @Override
    public String toString() {
        return String.format(
            "%s (%s) - HP: %d/%d - LVL: %d",
            name, getClassDisplayName(), hp, maxHp, stats.level()
        );
    }
}

