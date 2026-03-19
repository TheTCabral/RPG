package com.aetherhold.domain.shared;

import java.util.Random;

/**
 * Rolador de dados para cálculos de combate.
 * Não é um bean Spring — recebe seed por construtor.
 */
public class DiceRoller {
    private final Random random;

    public DiceRoller(long seed) {
        this.random = new Random(seed);
    }

    public int d4() {
        return random.nextInt(4) + 1;
    }

    public int d6() {
        return random.nextInt(6) + 1;
    }

    public int d8() {
        return random.nextInt(8) + 1;
    }

    public int d10() {
        return random.nextInt(10) + 1;
    }

    public int d12() {
        return random.nextInt(12) + 1;
    }

    public int d20() {
        return random.nextInt(20) + 1;
    }

    /**
     * Rola dados no formato "2d6", "1d8", etc.
     *
     * @param notation Notação do tipo "XdY"
     * @return Resultado da soma
     */
    public int roll(String notation) {
        String[] parts = notation.split("d");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Notação inválida: " + notation);
        }

        int numDice = Integer.parseInt(parts[0]);
        int diceSize = Integer.parseInt(parts[1]);

        int total = 0;
        for (int i = 0; i < numDice; i++) {
            total += random.nextInt(diceSize) + 1;
        }
        return total;
    }

    /**
     * Rola um dado de tamanho arbitrário
     *
     * @param sides Número de lados do dado
     * @return Resultado de 1 a sides (inclusive)
     */
    public int rollDice(int sides) {
        return random.nextInt(sides) + 1;
    }

    /**
     * Gera um número aleatório entre 0.0 e 1.0 para chances
     *
     * @return Número entre 0.0 e 1.0
     */
    public double chance() {
        return random.nextDouble();
    }
}

