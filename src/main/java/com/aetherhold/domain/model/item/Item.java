package com.aetherhold.domain.model.item;

import java.util.*;

/**
 * Enum de raridade de itens.
 */
enum Rarity {
    COMMON("Comum", 1.0),
    UNCOMMON("Incomum", 1.5),
    RARE("Raro", 2.0),
    EPIC("Épico", 3.0),
    LEGENDARY("Lendário", 5.0);

    private final String displayName;
    private final double valueMultiplier;

    Rarity(String displayName, double valueMultiplier) {
        this.displayName = displayName;
        this.valueMultiplier = valueMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public double getValueMultiplier() {
        return valueMultiplier;
    }
}

/**
 * Classe abstrata base para todos os itens.
 */
public abstract class Item {
    protected final UUID id;
    protected final String name;
    protected final String description;
    protected final Rarity rarity;
    protected final int value;

    public Item(UUID id, String name, String description, Rarity rarity, int value) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.rarity = rarity;
        this.value = value;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, rarity.getDisplayName());
    }
}

/**
 * Arma — adiciona dano ao ataque.
 */
class Weapon extends Item {
    private final int damageBonus;
    private final String damageDice;  // Ex: "1d8", "2d6"

    public Weapon(UUID id, String name, String description, Rarity rarity, int value, int damageBonus, String damageDice) {
        super(id, name, description, rarity, value);
        this.damageBonus = damageBonus;
        this.damageDice = damageDice;
    }

    public int getDamageBonus() {
        return damageBonus;
    }

    public String getDamageDice() {
        return damageDice;
    }
}

/**
 * Armadura — reduz dano.
 */
class Armor extends Item {
    private final int armorValue;

    public Armor(UUID id, String name, String description, Rarity rarity, int value, int armorValue) {
        super(id, name, description, rarity, value);
        this.armorValue = armorValue;
    }

    public int getArmorValue() {
        return armorValue;
    }
}

/**
 * Consumível — poção, pergaminho, etc.
 */
class Consumable extends Item {
    private final String effectType;  // "healing", "mana", "status_cure", etc.
    private final int effectValue;

    public Consumable(UUID id, String name, String description, Rarity rarity, int value, String effectType, int effectValue) {
        super(id, name, description, rarity, value);
        this.effectType = effectType;
        this.effectValue = effectValue;
    }

    public String getEffectType() {
        return effectType;
    }

    public int getEffectValue() {
        return effectValue;
    }
}

/**
 * Inventário do personagem — gerencia itens, equipamento.
 */
class Inventory {
    private final List<Item> items;
    private Weapon equippedWeapon;
    private Armor equippedArmor;

    public Inventory() {
        this.items = new ArrayList<>();
        this.equippedWeapon = null;
        this.equippedArmor = null;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(UUID itemId) {
        items.removeIf(item -> item.getId().equals(itemId));
    }

    public Optional<Item> getItem(UUID itemId) {
        return items.stream().filter(item -> item.getId().equals(itemId)).findFirst();
    }

    public List<Item> getItems() {
        return new ArrayList<>(items);
    }

    public void equipWeapon(Weapon weapon) {
        this.equippedWeapon = weapon;
    }

    public void equipArmor(Armor armor) {
        this.equippedArmor = armor;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }

    public Armor getEquippedArmor() {
        return equippedArmor;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int getItemCount() {
        return items.size();
    }
}

