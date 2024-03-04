package Entites;

public class equipe {
    private int id;
    private int idJeu;
    private String nom;
    private int nbrJoueur;
    private String listeJoueur;

    public equipe(int id, int id_jeu, String nom, int nbr_joueur, String liste_joueur) {
        this.id = id;
        this.idJeu = id_jeu;
        this.nom = nom;
        this.nbrJoueur = nbr_joueur;
        this.listeJoueur = liste_joueur;
    }

    public equipe(int id_jeu, String nom, int nbr_joueur, String liste_joueur) {
        this.idJeu = id_jeu;
        this.nom = nom;
        this.nbrJoueur = nbr_joueur;
        this.listeJoueur = liste_joueur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdJeu() {
        return idJeu;
    }

    public void setIdJeu(int id_jeu) {
        this.idJeu = id_jeu;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNbrJoueur() {
        return nbrJoueur;
    }

    public void setNbrJoueur(int nbr_joueur) {
        this.nbrJoueur = nbr_joueur;
    }

    public String getListeJoueur() {
        return listeJoueur;
    }

    public void setListeJoueur(String liste_joueur) {
        this.listeJoueur = liste_joueur;
    }

    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id +
                ", idJeu=" + idJeu +
                ", nom='" + nom + '\'' +
                ", nbrJoueur=" + nbrJoueur +
                ", listeJoueur='" + listeJoueur + '\'' +
                '}';
    }
}


