-- V2__create_dungeons.sql
CREATE TABLE dungeons (
    id VARCHAR(36) PRIMARY KEY,
    character_id VARCHAR(36) NOT NULL,
    current_floor INT NOT NULL DEFAULT 1,
    total_floors INT NOT NULL,
    floors_json LONGTEXT,
    completed BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_dungeon_character FOREIGN KEY (character_id) REFERENCES characters(id) ON DELETE CASCADE
);

CREATE INDEX idx_dungeon_character ON dungeons(character_id);

