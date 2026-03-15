public abstract class Personagem {
    String nome;
    int vida;
    int forca;
    int defesa;
    int velocidade;
    int experiencia;
    int nivel;
    int pontosParaUpar;
    int stamina;

    public Personagem() {}
    public Personagem(String nome, int vida, int forca, int defesa, int velocidade, int experiencia, int nivel, int pontosParaUpar) {
        this.nome = nome;
        this.vida = vida;
        this.forca = forca;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.experiencia = experiencia;
        this.nivel = nivel;
        this.pontosParaUpar = pontosParaUpar;
    }
    public void atacar() {}
    public void defender() {}
    public void curar() {}
    public void fugir (){}
    public void habilidadeEspecial() {}
}
