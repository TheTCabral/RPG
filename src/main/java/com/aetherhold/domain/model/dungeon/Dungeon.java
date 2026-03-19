package com.aetherhold.domain.model.dungeon;

import java.util.*;

/**
 * Enum de tipos de sala.
 */
enum RoomType {
    COMBAT("Combate", 'E'),        // Inimigo
    TREASURE("Tesouro", 'T'),      // Loot
    SHOP("Loja", 'S'),             // Vendedor
    REST("Descanso", 'R'),         // Cura e descanso
    EMPTY("Vazio", '.'),           // Nada
    BOSS("Boss", 'B');             // Chefe do andar

    private final String displayName;
    private final char mapSymbol;

    RoomType(String displayName, char mapSymbol) {
        this.displayName = displayName;
        this.mapSymbol = mapSymbol;
    }

    public String getDisplayName() {
        return displayName;
    }

    public char getMapSymbol() {
        return mapSymbol;
    }
}

/**
 * Uma sala no dungeon.
 */
class Room {
    private final int x;
    private final int y;
    private final RoomType type;
    private boolean visited;

    public Room(int x, int y, RoomType type) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.visited = false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public RoomType getType() {
        return type;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public String toString() {
        return String.format("Room(%d,%d):%s", x, y, type.getDisplayName());
    }
}

/**
 * Um andar do dungeon — contém várias salas em grid.
 */
class DungeonFloor {
    private final UUID id;
    private final int floorNumber;  // 1-10
    private final int width;
    private final int height;
    private final Room[][] grid;
    private int heroX, heroY;       // Posição do herói
    private boolean completed;

    public DungeonFloor(UUID id, int floorNumber, int width, int height) {
        this.id = id;
        this.floorNumber = floorNumber;
        this.width = width;
        this.height = height;
        this.grid = new Room[height][width];
        this.heroX = 0;
        this.heroY = 0;
        this.completed = false;

        // Inicializar grid com salas vazias
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = new Room(x, y, RoomType.EMPTY);
            }
        }
    }

    public UUID getId() {
        return id;
    }

    public int getFloorNumber() {
        return floorNumber;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Room getRoom(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        return grid[y][x];
    }

    public void setRoom(int x, int y, RoomType type) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = new Room(x, y, type);
        }
    }

    public int getHeroX() {
        return heroX;
    }

    public int getHeroY() {
        return heroY;
    }

    public void setHeroPosition(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            this.heroX = x;
            this.heroY = y;
        }
    }

    public Room getCurrentRoom() {
        return getRoom(heroX, heroY);
    }

    public boolean moveHero(int dx, int dy) {
        int newX = heroX + dx;
        int newY = heroY + dy;

        Room target = getRoom(newX, newY);
        if (target != null) {
            heroX = newX;
            heroY = newY;
            return true;
        }
        return false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void markCompleted() {
        this.completed = true;
    }

    /**
     * Renderiza o mapa ASCII do andar.
     */
    public String renderMap() {
        StringBuilder sb = new StringBuilder();
        sb.append("=".repeat(width * 2 + 2)).append("\n");

        for (int y = 0; y < height; y++) {
            sb.append("|");
            for (int x = 0; x < width; x++) {
                if (heroX == x && heroY == y) {
                    sb.append("@ ");  // Herói
                } else {
                    sb.append(grid[y][x].getType().getMapSymbol()).append(" ");
                }
            }
            sb.append("|\n");
        }

        sb.append("=".repeat(width * 2 + 2)).append("\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("DungeonFloor{floor=%d, size=%dx%d, completed=%s}", floorNumber, width, height, completed);
    }
}

/**
 * O dungeon completo — contém vários andares.
 */
public class Dungeon {
    private final UUID id;
    private final List<DungeonFloor> floors;
    private int currentFloor;  // Índice do andar atual (0-9)
    private boolean completed;

    public Dungeon(UUID id, int totalFloors) {
        this.id = id;
        this.floors = new ArrayList<>();
        this.currentFloor = 0;
        this.completed = false;

        // Criar andares (com tamanho crescente e mais inimigos em andares profundos)
        for (int i = 1; i <= totalFloors; i++) {
            int size = 5 + (i / 2);  // Cresce com profundidade
            floors.add(new DungeonFloor(UUID.randomUUID(), i, size, size));
        }
    }

    public UUID getId() {
        return id;
    }

    public int getTotalFloors() {
        return floors.size();
    }

    public int getCurrentFloorNumber() {
        return currentFloor + 1;
    }

    public DungeonFloor getCurrentFloor() {
        return floors.get(currentFloor);
    }

    public DungeonFloor getFloor(int floorNumber) {
        if (floorNumber < 1 || floorNumber > floors.size()) {
            return null;
        }
        return floors.get(floorNumber - 1);
    }

    /**
     * Avança para o próximo andar.
     */
    public boolean advanceFloor() {
        if (currentFloor < floors.size() - 1) {
            currentFloor++;
            return true;
        } else {
            completed = true;
            return false;
        }
    }

    public boolean isCompleted() {
        return completed;
    }

    @Override
    public String toString() {
        return String.format("Dungeon{total=%d, current=%d, completed=%s}", floors.size(), currentFloor + 1, completed);
    }
}

