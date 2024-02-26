package controllers;
import Entites.Spectateur;
import Entites.User;
import Service.ServiceSpectateurCrud;
import Service.ServiceUserCrud;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;

public class AjouterSpectateurController {



    @FXML
    private TextField txtnoms;

    @FXML
    private TextField txtprenoms;

    @FXML
    private TextField txtages;

    private final ServiceSpectateurCrud ser=new ServiceSpectateurCrud();



    @FXML
    void ajouterSpectateur(ActionEvent event) {

        String nom = txtnoms.getText();
        String prenom = txtprenoms.getText();
        int age = Integer.parseInt(txtages.getText());
        Spectateur s4 = new Spectateur(nom, prenom, age);

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("Spectateur ajoute avec succes");
        alert1.showAndWait();

        try {
            ser.ajouter(s4);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }



/*
        @FXML
        void afficherSpectateur(ActionEvent event) {
            try{
                FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherSpectateur.fxml"));
                Parent root=loader.load();
                txtnoms.getScene().setRoot(root);
                AfficherSpectateurController dc = loader.getController();

            }   catch (IOException e){
                throw new RuntimeException(e);}
        }
*/




















    public void afficherSpectateur(ActionEvent actionEvent) {try{
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherSpectateur.fxml"));
        Parent root=loader.load();
        txtnoms.getScene().setRoot(root);
        AfficherSpectateurController dc = loader.getController();

    }   catch (IOException e){
        throw new RuntimeException(e);}
    }
    }
