package classes;

import personagem.Personagem;

public class Mago extends Personagem {
    int mana;

    public Mago() {

    }

    public Mago(String nome, int vidaAtual, int vidaMaxima, int defesa, int velocidade, int experiencia, int nivel, int pontosParaUpar, int mana) {
        super(nome, vidaAtual, vidaMaxima, defesa, velocidade, experiencia, nivel, pontosParaUpar);
        this.mana = mana;
    }

    public void atacar() {


    }
    public void curar() {

        }

    public void fugir() {

    }

    public void usarHabilidade() {
        if (mana >= 10) {
            System.out.println("Habilidade especial usada!");
            mana -= 10;
        } else {
            System.out.println("Mana insuficiente para usar a habilidade especial.");
        }
    }

    public void usarMana() {
        if (mana > 0) {
            mana--;
        }
    }

}
