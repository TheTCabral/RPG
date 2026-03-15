package personagem;

public abstract class Personagem {
    String nome;
    int vidaAtual;
    int vidaMaxima;
    int defesa;
    int velocidade;
    int experiencia;
    int nivel;
    int pontosParaUpar;
    int stamina;

    public Personagem() {}
    public Personagem(String nome, int vidaAtual, int vidaMaxima, int defesa, int velocidade, int experiencia, int nivel, int pontosParaUpar) {
        this.nome = nome;
        this.vidaAtual = vidaAtual;
        this.vidaMaxima = vidaMaxima;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.pontosParaUpar = pontosParaUpar;
    }
    public void atacar() {}
    public void defender() {}
    public void fugir (){}
}
