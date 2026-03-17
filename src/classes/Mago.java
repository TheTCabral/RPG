package classes;
import personagem.Personagem;
public class Mago extends Personagem {
    protected int mana;
    protected int manaMaxima;
    public Mago() {
    }
    public Mago(String nome, int vidaAtual, int vidaMaxima, int defesa, int ataque, int velocidade, int experiencia, int nivel, int pontosParaUpar, int mana, int manaMaxima) {
        super(nome, vidaAtual, vidaMaxima, defesa, ataque, velocidade, experiencia, nivel, pontosParaUpar);
        this.manaMaxima = manaMaxima;
        this.mana = Math.min(mana, manaMaxima);
    }
    @Override
    public void atacar() {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode atacar!");
            return;
        }
        System.out.println(nome + " lança um ataque mágico com força de " + ataque + "!");
    }
    public void curar(int quantidade) {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode curar!");
            return;
        }
        if (mana < 20) {
            System.out.println("Mana insuficiente! É necessário 20 de mana para curar.");
            return;
        }
        mana -= 20;
        super.curar(quantidade);
    }
    @Override
    public void fugir() {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode fugir!");
            return;
        }
        System.out.println(nome + " (Mago) tenta fugir rapidamente!");
    }
    @Override
    public void usarHabilidade() {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode usar habilidades!");
            return;
        }
        if (mana >= 50) {
            System.out.println(nome + " usa Bola de Fogo! Um ataque devastador!");
            mana -= 50;
        } else {
            System.out.println("Mana insuficiente para usar Bola de Fogo! É necessário 50 de mana.");
        }
    }
    public void recuperarMana(int quantidade) {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode recuperar mana!");
            return;
        }
        mana = Math.min(mana + quantidade, manaMaxima);
        System.out.println(nome + " recuperou mana! Mana: " + mana + "/" + manaMaxima);
    }
    public int getMana() {
        return mana;
    }
    public int getManaMaxima() {
        return manaMaxima;
    }
}