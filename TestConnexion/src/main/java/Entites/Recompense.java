package Entites;

import java.util.Date;

public class Recompense {

    private int id;
    private String equipeGagnante; // Correction du nom de la variable
    private int montantRecompense;
    private Date dateRecompense;

    // Constructeur avec les quatre champs
    public Recompense(int id, String equipeGagnante, int montantRecompense, Date dateRecompense) {
        this.id = id;
        this.equipeGagnante = equipeGagnante;
        this.montantRecompense = montantRecompense;
        this.dateRecompense = dateRecompense;
    }

    // Constructeur avec trois champs (pour utilisation dans Service)
    public Recompense(String equipeGagnante, int montantRecompense, Date dateRecompense) {
        this.equipeGagnante = equipeGagnante;
        this.montantRecompense = montantRecompense;
        this.dateRecompense = dateRecompense;
    }

    public Recompense() {

    }


    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipeGagnante() {
        return equipeGagnante;
    }

    public void setEquipeGagnante(String equipeGagnante) {
        this.equipeGagnante = equipeGagnante;
    }

    public int getMontantRecompense() {
        return montantRecompense;
    }

    public void setMontantRecompense(int montantRecompense) {
        this.montantRecompense = montantRecompense;
    }

    public Date getDateRecompense() {
        return dateRecompense;
    }

    public void setDateRecompense(Date dateRecompense) {
        this.dateRecompense = dateRecompense;
    }

    @Override
    public String toString() {
        return equipeGagnante ;
    }
}
