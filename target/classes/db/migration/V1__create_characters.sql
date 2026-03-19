-- V1__create_characters.sql
CREATE TABLE characters (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    character_class VARCHAR(50) NOT NULL,
    hp INT NOT NULL,
    max_hp INT NOT NULL,
    level INT NOT NULL DEFAULT 1,
    current_xp INT NOT NULL DEFAULT 0,
    strength INT NOT NULL,
    agility INT NOT NULL,
    intelligence INT NOT NULL,
    defense INT NOT NULL,
    mana INT NOT NULL,
    gold INT NOT NULL DEFAULT 0,
    inventory_json LONGTEXT,
    active_effects_json LONGTEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT ck_character_class CHECK (character_class IN (
        'DWARF', 'ARCHER', 'BARD', 'WARRIOR', 'ROGUE', 'MAGE', 'PALADIN'
    ))
);

CREATE INDEX idx_character_name ON characters(name);

