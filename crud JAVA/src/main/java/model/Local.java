package model;

public class Local {
    private int id;
    private String adresse;
    private int capacite;
    private String categorie;

    // Constructeur
    public Local(String adresse, int capacite, String categorie) {
        this.adresse = adresse;
        this.capacite = capacite;
        this.categorie = categorie;
    }
    public Local(int id ,String adresse, int capacite, String categorie) {

        this.id=id;
        this.adresse = adresse;
        this.capacite = capacite;
        this.categorie = categorie;
    }
    public Local() {
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse (String adresse) {
        this.adresse = adresse;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Local{" +
                "id=" + id +
                ", adresse='" + adresse + '\'' +
                ", capacite=" + capacite +
                ", categorie='" + categorie + '\'' +
                '}';
    }
}
