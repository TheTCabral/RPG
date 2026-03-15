public class Mago extends Personagem {
    int mana;

    public Mago() {

    }

    public Mago(String nome, int vida, int forca, int defesa, int velocidade, int experiencia, int nivel, int pontosParaUpar, int mana) {
    super(nome, vida, forca, defesa, velocidade, experiencia, nivel, pontosParaUpar);
    this.mana = mana;
    }

    public void atacar() {
        if (stamina >= 10) {
            System.out.println("Ataque mágico realizado!");
            stamina -= 10;
        } else {
            System.out.println("Stamina insuficiente para atacar.");
        }
    }
    public void curar() {
        if (mana >= 5 && vida < 100) {
            System.out.println("Mana usada para curar!");
            mana -= 5;
            vida += 10;
        }else if (vida >= 100) {
            System.out.println("Vida já está no máximo, não é necessário curar.");
        } else {
            System.out.println("Mana insuficiente para curar.");
    }
        }

    public void fugir() {

    }

    public void habilidadeEspecial() {

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
