package Entites;

public class joueur {
    private int cin;
    private String nom;
    private String prenom;
    private String genre;
    private int tel;
    private String email;
    public joueur(int cin, String nom, String prenom,String genre,int tel, String email) {
        this.cin = cin;
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.tel=tel;
        this.email=email;
    }

    public joueur(String nom, String prenom, String genre,int tel, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.genre = genre;
        this.tel=tel;
        this.email=email;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "joueur{" +
                "cin=" + cin +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", genre='" + genre + '\'' +
                ", tel=" + tel +
                ", email='" + email + '\'' +
                '}';
    }
}
