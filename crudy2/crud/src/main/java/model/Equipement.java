package model;

public class Equipement {
    private Integer id;
    private String nom;
    private int nombre;

    private int id_sponsor; // 8allllllllllllllllllta !!!!
    public Equipement()
    {}
    public Equipement(String nom , int nombre)
    {
        this.nom=nom;
        this.nombre = nombre;
    }
    public Equipement(Integer id, String nom , int nombre)
    {
        this.id=id;
        this.nom=nom;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Equipement{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", nombre=" + nombre +
                '}';
    }
}
