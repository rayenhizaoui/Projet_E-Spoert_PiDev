package controller;

import Service.Service_l;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Local;

import java.sql.SQLException;
import static java.lang.Integer.parseInt;

import javafx.scene.control.*;

public class Update_lController{

    @FXML
    private Button Delete_button;

    @FXML
    private TextField adressetxt;

    @FXML
    private TextField capacitetxt;

    @FXML
    private TextField categorietxt;

    @FXML
    private Button update_button;

    @FXML
    void deletel(MouseEvent event) throws SQLException {
        Service_l cntrl = new Service_l();
        cntrl.deletel(getId());
        ((Stage)categorietxt.getScene().getWindow()).close();

        // ((Stage)typejeutxt.getScene().getWindow()).close();
    }
    @FXML
    void updatel(MouseEvent event ) throws SQLException {
        int capacite = Integer.parseInt(capacitetxt.getText());
        //System.out.println("here");
        String adresse = adressetxt.getText();
        String categorie = categorietxt.getText();
        if (adressetxt.getText().compareTo("") == 0 || adressetxt.getText().compareTo("") == 0 || capacite==0 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Please fill all data");
            alert.showAndWait();
        }
        else if (categorietxt.getText().isEmpty() || categorietxt.getText().matches("^[0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Catégorie invalide : veuillez saisir une chaîne de caractères non numérique.");
            alert.showAndWait();
        }


       else if( (capacite <= 0) || !capacitetxt.getText().matches("^[0-9]*$")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Capacité invalide : veuillez saisir un entier positif.");
            alert.showAndWait();
        }
       else {
            Service_l ser = new Service_l();
            Local local = new Local(adresse, capacite, categorie);
            ser.updatel(local, getId());
            ((Stage) categorietxt.getScene().getWindow()).close();
        }

    }
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEvent(Local l){
        setId(l.getId());
        System.out.println(getId());
        adressetxt.setText(String.valueOf(l.getAdresse()));
        categorietxt.setText(l.getCategorie());
        capacitetxt.setText(String.valueOf(l.getCapacite()));
    }
}