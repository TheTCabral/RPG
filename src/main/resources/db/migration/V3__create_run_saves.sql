-- V3__create_run_saves.sql
CREATE TABLE run_saves (
    id VARCHAR(36) PRIMARY KEY,
    character_id VARCHAR(36) NOT NULL,
    dungeon_id VARCHAR(36),
    game_state_json LONGTEXT,
    saved_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_run_save_character FOREIGN KEY (character_id) REFERENCES characters(id) ON DELETE CASCADE,
    CONSTRAINT fk_run_save_dungeon FOREIGN KEY (dungeon_id) REFERENCES dungeons(id) ON DELETE SET NULL
);

CREATE INDEX idx_run_save_character ON run_saves(character_id);

