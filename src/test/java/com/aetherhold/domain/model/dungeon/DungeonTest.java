package com.aetherhold.domain.model.dungeon;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class DungeonFloorTest {
    // ...existing code...
}

class DungeonFloorTest {

    private DungeonFloor floor;

    @BeforeEach
    void setUp() {
        floor = new DungeonFloor(UUID.randomUUID(), 1, 5, 5);
    }

    @Test
    void shouldInitializeWithEmptyRooms() {
        assertThat(floor.getWidth()).isEqualTo(5);
        assertThat(floor.getHeight()).isEqualTo(5);
        assertThat(floor.getRoom(0, 0).getType()).isEqualTo(RoomType.EMPTY);
    }

    @Test
    void shouldSetRoomType() {
        floor.setRoom(2, 2, RoomType.COMBAT);

        Room room = floor.getRoom(2, 2);
        assertThat(room.getType()).isEqualTo(RoomType.COMBAT);
    }

    @Test
    void shouldMoveHeroWithinBounds() {
        boolean moved = floor.moveHero(1, 1);

        assertThat(moved).isTrue();
        assertThat(floor.getHeroX()).isEqualTo(1);
        assertThat(floor.getHeroY()).isEqualTo(1);
    }

    @Test
    void shouldNotMoveHeroOutOfBounds() {
        boolean moved = floor.moveHero(-1, 0);

        assertThat(moved).isFalse();
        assertThat(floor.getHeroX()).isEqualTo(0);
        assertThat(floor.getHeroY()).isEqualTo(0);
    }

    @Test
    void shouldRenderMap() {
        String map = floor.renderMap();

        assertThat(map).contains("@");  // Herói
        assertThat(map).contains("."); // Salas vazias
    }
}

class DungeonTest {

    private Dungeon dungeon;

    @BeforeEach
    void setUp() {
        dungeon = new Dungeon(UUID.randomUUID(), 10);
    }

    @Test
    void shouldCreateTenFloors() {
        assertThat(dungeon.getTotalFloors()).isEqualTo(10);
    }

    @Test
    void shouldStartAtFloorOne() {
        assertThat(dungeon.getCurrentFloorNumber()).isEqualTo(1);
    }

    @Test
    void shouldAdvanceFloors() {
        for (int i = 1; i < 10; i++) {
            boolean advanced = dungeon.advanceFloor();
            assertThat(advanced).isTrue();
            assertThat(dungeon.getCurrentFloorNumber()).isEqualTo(i + 1);
        }

        // Tentar avançar do 10º andar
        boolean lastAdvance = dungeon.advanceFloor();
        assertThat(lastAdvance).isFalse();
        assertThat(dungeon.isCompleted()).isTrue();
    }

    @Test
    void shouldGetFloorByNumber() {
        DungeonFloor floor5 = dungeon.getFloor(5);
        assertThat(floor5).isNotNull();
        assertThat(floor5.getFloorNumber()).isEqualTo(5);
    }
}

