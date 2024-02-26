package controller;

import Service.ServiceEq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Equipement;

import java.sql.SQLException;

public class updateequipementController {

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnombre;


    @FXML
    void updateequipement(ActionEvent event) throws SQLException {

        String nom = txtnom.getText();
        int nombre = Integer.parseInt(txtnombre.getText());
        Equipement equipement = new Equipement(nom, nombre);
        ServiceEq ser = new ServiceEq();
        ser.update(equipement , getId());
        ((Stage) txtnom.getScene().getWindow()).close();
    }
    @FXML
    void deleteequipement(ActionEvent event) throws SQLException {

        ServiceEq cntrl = new ServiceEq() ;
        cntrl.delete(getId());
        ((Stage)txtnom.getScene().getWindow()).close();

    }


    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    @FXML//remplir les champs
    public void  setEvent (Equipement e){
        setId(e.getId());
        System.out.println(getId());
        txtnom.setText(e.getNom());
        txtnombre.setText(String.valueOf(e.getNombre()));

    }

}