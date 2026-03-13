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
    public Personagem(String nome, int vida, int forca, int defesa, int velocidade, int experiencia, int nivel, int pontosParaUpar) {}
    public void atacar() {}
    public void defender() {}
    public void curar() {}
    public void habilidadeEspecial() {}
}
