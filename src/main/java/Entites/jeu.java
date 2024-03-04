package Entites;

public class jeu {
    private int id;
    private String nom;
    private String type;
    private int score;
    private String resultat;

    public jeu(int id, String nom, String type, int score, String resultat) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.score = score;
        this.resultat = resultat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getResultat() {
        return resultat;
    }

    public void setResultat(String resultat) {
        this.resultat = resultat;
    }

    @Override
    public String toString() {
        return "Jeu{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", type='" + type + '\'' +
                ", score=" + score +
                ", resultat='" + resultat + '\'' +
                '}';
    }
}
