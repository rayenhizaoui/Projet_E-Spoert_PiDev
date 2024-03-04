package model;

import java.sql.ResultSet;

public class Equipement {
    private Integer id;
    private String nom;
    private int nombre;

    private String image ;
    private double rating;

    private String QrCode ;

    private int id_sponsor; // 8allllllllllllllllllta !!!!zz
    public Equipement()
    {}
    public Equipement(String nom , int nombre ,String image , double rating ,String QrCode )
    {
        this.nom=nom;
        this.nombre = nombre;
        this.image = image ;
        this.rating = rating ;
        this.QrCode= QrCode ;
    }
    public Equipement(Integer id, String nom , int nombre ,String image , double rating , String QrCode )
    {
        this.id=id;
        this.nom=nom;
        this.nombre = nombre;
        this.image = image ;
        this.rating = rating ;
        this.QrCode = QrCode ;
    }

    public Equipement(String nom, Integer nombre, String image) {
        this.nom=nom;
        this.nombre = nombre;
        this.image = image ;
    }

    public Equipement(String nom, Integer nombre, String image, String QrCode) {
        this.nom=nom;
        this.nombre = nombre;
        this.image = image ;
        this.QrCode = QrCode ;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getQrCode() {
        return QrCode;
    }

    public void setQrCode(String qrCode) {
        QrCode = qrCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return nom ;
    }
}
