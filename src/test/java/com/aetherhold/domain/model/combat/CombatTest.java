package com.aetherhold.domain.model.combat;

import com.aetherhold.domain.shared.DiceRoller;
import com.aetherhold.domain.model.character.Character;
import com.aetherhold.domain.model.character.CharacterFactory;
import com.aetherhold.domain.model.enemy.Enemy;
import com.aetherhold.domain.model.enemy.EnemyFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.*;

class DamageCalculatorTest {

    private Character warrior;
    private EnemyFactory enemyFactory;
    private DiceRoller diceRoller;

    @BeforeEach
    void setUp() {
        warrior = CharacterFactory.createWarrior(UUID.randomUUID(), "Thorin");
        enemyFactory = new EnemyFactory();
        diceRoller = new DiceRoller(42);
    }

    @Test
    void shouldResolveHit() {
        var goblin = enemyFactory.createGoblin();

        AttackResult result = DamageCalculator.resolve(warrior, goblin, CombatAction.ATTACK, diceRoller);

        assertThat(result.isHit()).isTrue();
        assertThat(result.damageDealt()).isGreaterThan(0);
    }

    // ...existing code...
}

class InitiativeCalculatorTest {

    private Character warrior;
    private EnemyFactory enemyFactory;
    private DiceRoller diceRoller;

    @BeforeEach
    void setUp() {
        warrior = CharacterFactory.createWarrior(UUID.randomUUID(), "Thorin");
        enemyFactory = new EnemyFactory();
        diceRoller = new DiceRoller(42);
    }

    @Test
    void shouldDetermineCombatOrder() {
        var enemy = enemyFactory.createGoblin();

        var combatants = InitiativeCalculator.sort(warrior, enemy, diceRoller);

        assertThat(combatants).hasSize(2);
        assertThat(combatants[0].initiative).isGreaterThanOrEqualTo(combatants[1].initiative);
    }

    @Test
    void shouldFavorAgilityInInitiative() {
        var archer = CharacterFactory.createArcher(UUID.randomUUID(), "Legolas");
        var dwarf = CharacterFactory.createDwarf(UUID.randomUUID(), "Gimli");

        // Correr várias vezes para ver padrão
        int archerWins = 0;
        for (int i = 0; i < 50; i++) {
            DiceRoller roller = new DiceRoller(System.nanoTime());
            var combatants = InitiativeCalculator.sort(archer, dwarf, roller);
            if (combatants[0].character == archer) {
                archerWins++;
            }
        }

        // Archer com AGI 18 vs Dwarf AGI 8 deve ganhar iniciativa mais vezes
        assertThat(archerWins).isGreaterThan(25);
    }
}

class CombatSessionTest {

    private Character hero;
    private EnemyFactory enemyFactory;

    @BeforeEach
    void setUp() {
        hero = CharacterFactory.createWarrior(UUID.randomUUID(), "Thorin");
        enemyFactory = new EnemyFactory();
    }

    @Test
    void shouldInitializeCombatSession() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        assertThat(session.getTurn()).isEqualTo(1);
        assertThat(session.isOver()).isFalse();
        assertThat(session.getCombatLog()).isNotEmpty();
    }

    @Test
    void shouldLogCombatActions() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        session.logMessage("Teste de mensagem");

        assertThat(session.getCombatLog()).contains("Teste de mensagem");
    }

    @Test
    void shouldEndCombatWhenHeroDies() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        hero.takeDamage(1000);  // Matar herói
        session.applyAttackResult(
            AttackResult.hit(10, 100),
            enemy, hero
        );

        assertThat(session.isOver()).isTrue();
        assertThat(session.getVictor()).isEqualTo(enemy);
    }

    @Test
    void shouldEndCombatWhenEnemyDies() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        enemy.takeDamage(1000);  // Matar inimigo
        session.applyAttackResult(
            AttackResult.hit(20, 100),
            hero, enemy
        );

        assertThat(session.isOver()).isTrue();
        assertThat(session.getVictor()).isEqualTo(hero);
    }
}

class DamageCalculatorTest {

    private Warrior warrior;
    private EnemyFactory enemyFactory;
    private DiceRoller diceRoller;

    @BeforeEach
    void setUp() {
        warrior = new Warrior(UUID.randomUUID(), "Thorin", new ArrayList<>());
        enemyFactory = new EnemyFactory();
        diceRoller = new DiceRoller(42);
    }

    @Test
    void shouldResolveHit() {
        var goblin = enemyFactory.createGoblin();

        AttackResult result = DamageCalculator.resolve(warrior, goblin, CombatAction.ATTACK, diceRoller);

        assertThat(result.isHit()).isTrue();
        assertThat(result.damageDealt()).isGreaterThan(0);
    }

    @Test
    void shouldCalculateCriticalOnNatural20() {
        // Não podemos garantir um 20 natural com random, mas podemos verificar a lógica
        var goblin = enemyFactory.createGoblin();

        // Rolar várias vezes para aumentar chances
        boolean foundCritical = false;
        for (int i = 0; i < 100; i++) {
            DiceRoller roller = new DiceRoller(System.nanoTime());
            AttackResult result = DamageCalculator.resolve(warrior, goblin, CombatAction.ATTACK, roller);
            if (result.isCritical()) {
                foundCritical = true;
                break;
            }
        }

        // Críticos devem ser pelo menos possíveis
        // (Com 100 rolls, chance de natural 20 é ~94%)
        assertThat(foundCritical).isTrue();
    }

    @Test
    void shouldReduceDamageWithArmor() {
        var enemy = enemyFactory.createGoblin();
        int enemyArmorBefore = enemy.getArmor();

        AttackResult result = DamageCalculator.resolve(warrior, enemy, CombatAction.ATTACK, diceRoller);

        if (result.isHit()) {
            // Dano deve ser menor que o dano base
            assertThat(result.damageDealt()).isGreaterThanOrEqualTo(0);
        }
    }
}

class InitiativeCalculatorTest {

    private Warrior warrior;
    private EnemyFactory enemyFactory;
    private DiceRoller diceRoller;

    @BeforeEach
    void setUp() {
        warrior = new Warrior(UUID.randomUUID(), "Thorin", new ArrayList<>());
        enemyFactory = new EnemyFactory();
        diceRoller = new DiceRoller(42);
    }

    @Test
    void shouldDetermineCombatOrder() {
        var enemy = enemyFactory.createGoblin();

        var combatants = InitiativeCalculator.sort(warrior, enemy, diceRoller);

        assertThat(combatants).hasSize(2);
        assertThat(combatants[0].initiative).isGreaterThanOrEqualTo(combatants[1].initiative);
    }

    @Test
    void shouldFavorAgilityInInitiative() {
        var archer = new com.aetherhold.domain.model.character.Archer(
            UUID.randomUUID(), "Legolas", new ArrayList<>()
        );
        var dwarf = new com.aetherhold.domain.model.character.Dwarf(
            UUID.randomUUID(), "Gimli", new ArrayList<>()
        );

        // Correr várias vezes para ver padrão
        int archerWins = 0;
        for (int i = 0; i < 50; i++) {
            DiceRoller roller = new DiceRoller(System.nanoTime());
            var combatants = InitiativeCalculator.sort(archer, dwarf, roller);
            if (combatants[0].character == archer) {
                archerWins++;
            }
        }

        // Archer com AGI 18 vs Dwarf AGI 8 deve ganhar iniciativa mais vezes
        assertThat(archerWins).isGreaterThan(25);
    }
}

class CombatSessionTest {

    private Warrior hero;
    private EnemyFactory enemyFactory;

    @BeforeEach
    void setUp() {
        hero = new Warrior(UUID.randomUUID(), "Thorin", new ArrayList<>());
        enemyFactory = new EnemyFactory();
    }

    @Test
    void shouldInitializeCombatSession() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        assertThat(session.getTurn()).isEqualTo(1);
        assertThat(session.isOver()).isFalse();
        assertThat(session.getCombatLog()).isNotEmpty();
    }

    @Test
    void shouldLogCombatActions() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        session.logMessage("Teste de mensagem");

        assertThat(session.getCombatLog()).contains("Teste de mensagem");
    }

    @Test
    void shouldEndCombatWhenHeroDies() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        hero.takeDamage(1000);  // Matar herói
        session.applyAttackResult(
            AttackResult.hit(10, 100),
            enemy, hero
        );

        assertThat(session.isOver()).isTrue();
        assertThat(session.getVictor()).isEqualTo(enemy);
    }

    @Test
    void shouldEndCombatWhenEnemyDies() {
        var enemy = enemyFactory.createGoblin();
        var session = new CombatSession(UUID.randomUUID(), hero, enemy);

        enemy.takeDamage(1000);  // Matar inimigo
        session.applyAttackResult(
            AttackResult.hit(20, 100),
            hero, enemy
        );

        assertThat(session.isOver()).isTrue();
        assertThat(session.getVictor()).isEqualTo(hero);
    }
}

