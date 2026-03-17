package personagem;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@ToString(exclude = {"vidaAtual", "vidaMaxima"})
@EqualsAndHashCode(exclude = {"vidaAtual", "vidaMaxima"})
public abstract class Personagem {
    protected String nome;
    protected int vidaAtual;
    protected int vidaMaxima;
    protected int defesa;
    protected int ataque;
    protected int velocidade;
    protected int experiencia;
    protected int nivel;
    protected int pontosParaUpar;
    public Personagem() {}
    public Personagem(String nome, int vidaAtual, int vidaMaxima, int defesa, int ataque,
                      int velocidade, int experiencia, int nivel, int pontosParaUpar) {
        this.nome = nome;
        this.vidaMaxima = vidaMaxima;
        this.vidaAtual = Math.min(vidaAtual, vidaMaxima);
        this.defesa = Math.max(0, defesa);
        this.ataque = Math.max(0, ataque);
        this.velocidade = velocidade;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.pontosParaUpar = pontosParaUpar;
    }


    public void receberDano(int dano) {
        if (!estaVivo()) {
            System.out.println(nome + " Iiiiiih, Faleceu e ja não da para fazer mais nada!!");
            return;
        }
        int danoReal = Math.max(0, dano - defesa);
        vidaAtual = Math.max(0, vidaAtual - danoReal);
        System.out.println(nome + " recebeu " + danoReal + " de dano. Vida: " + vidaAtual + "/" + vidaMaxima);
    }
    public void atacar() {
        if (!estaVivo()) {
            System.out.println(nome + " esse ja era, não da para fazer mais nada!");
            return;
        }
        System.out.println(nome + " ataca com força de " + ataque + "!");
    }
    public void defender() {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode se defender!");
            return;
        }
        System.out.println(nome + " se defende com defesa de " + defesa + "!");
    }
    public void fugir() {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode fugir!");
            return;
        }
        System.out.println(nome + " tenta fugir!");
    }
    public abstract void usarHabilidade();

    public boolean estaVivo() {
        return vidaAtual > 0;
    }

    public void setVidaAtual(int vida) {
        this.vidaAtual = Math.max(0, Math.min(vida, vidaMaxima));
    }

    public void setDefesa(int defesa) {
        this.defesa = Math.max(0, defesa);
    }

    public void setAtaque(int ataque) {
        this.ataque = Math.max(0, ataque);
    }
    public void curar(int quantidade) {
        if (!estaVivo()) {
            System.out.println(nome + " está morto e não pode ser curado!");
            return;
        }
        vidaAtual = Math.min(vidaAtual + quantidade, vidaMaxima);
        System.out.println(nome + " foi curado! Vida: " + vidaAtual + "/" + vidaMaxima);
    }
}