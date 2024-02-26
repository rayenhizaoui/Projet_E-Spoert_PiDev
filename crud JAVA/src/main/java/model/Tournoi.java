package model;



public class Tournoi {
    private int id;
    private  String nom;
    private String nomEquipe;
    private int nombreParticipants;
    private int duree;
    private String typeJeu;
    private int fraisParticipation;
     private String location ;

    public Tournoi(int id, String nom, String nomEquipe, int nombreParticipants, int duree, String typeJeu, int fraisParticipation, String location) {
        this.id = id;
        this.nom = nom;
        this.nomEquipe = nomEquipe;
        this.nombreParticipants = nombreParticipants;
        this.duree = duree;
        this.typeJeu = typeJeu;
        this.fraisParticipation = fraisParticipation;
        this.location = location;
    }

    // Constructeur
    public Tournoi(String nom, String  nomEquipe, String location, int nombreParticipants, int duree, String typeJeu, int fraisParticipation) {
        this.nom=nom;
        this.nomEquipe = nomEquipe;
        this.location=location;
        this.nombreParticipants = nombreParticipants;
        this.duree = duree;
        this.typeJeu = typeJeu;
        this.fraisParticipation = fraisParticipation;
    }

    public Tournoi() {

    }


    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomEquipe() {
        return nomEquipe;
    }
    public String getNom() {
        return nom;
    }
    public void setNomEquipe(String nomEquipe) {
        this.nomEquipe = nomEquipe;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public int getNombreParticipants() {
        return nombreParticipants;
    }

    public void setNombreParticipants(int nombreParticipants) {
        this.nombreParticipants = nombreParticipants;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getTypeJeu() {
        return typeJeu;
    }

    public void setTypeJeu(String typeJeu) {
        this.typeJeu = typeJeu;
    }

    public int getFraisParticipation() {
        return fraisParticipation;
    }

    public void setFraisParticipation(int fraisParticipation) {
        this.fraisParticipation = fraisParticipation;
    }

    // Méthode toString
    @Override
    public String toString() {
        return "Tournoi{" +
                "id=" + id +
                ", nomEquipe='" + nomEquipe + '\'' +
                ", location='" + location+ '\'' +
                ", nombreParticipants=" + nombreParticipants +
                ", duree=" + duree +
                ", typeJeu='" + typeJeu + '\'' +
                ", fraisParticipation=" + fraisParticipation +
                '}';
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

