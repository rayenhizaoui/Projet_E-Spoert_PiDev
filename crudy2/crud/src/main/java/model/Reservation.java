package model;

import java.util.Date;

public class Reservation {
    private int id;
    private String nom;
    private Date datedebutres;
    private Date datefinres;
    private String type ;
    private Float deposit;

    public Reservation() {
    }

    public Reservation(String nom, Date datedebutres, Date datefinres, String type, Float deposit) {
        this.nom = nom;
        this.datedebutres = datedebutres;
        this.datefinres = datefinres;
        this.type = type;
        this.deposit = deposit;
    }

    public Reservation(int id, String nom, Date datedebutres, Date datefinres, String type, Float deposit) {
        this.id = id;
        this.nom = nom;
        this.datedebutres = datedebutres;
        this.datefinres = datefinres;
        this.type = type;
        this.deposit = deposit;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDatedebutres() {
        return datedebutres;
    }

    public Date getDatefinres() {
        return datefinres;
    }

    public String getType() {
        return type;
    }

    public Float getDeposit() {
        return deposit;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDatedebutres(Date datedebutres) {
        this.datedebutres = datedebutres;
    }

    public void setDatefinres(Date datefinres) {
        this.datefinres = datefinres;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setDeposit(Float deposit) {
        this.deposit = deposit;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", datedebutres=" + datedebutres +
                ", datefinres=" + datefinres +
                ", type='" + type + '\'' +
                ", deposit=" + deposit +
                '}';
    }
}