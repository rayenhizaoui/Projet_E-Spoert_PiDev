package controllers;

import Service.Service_l;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import Entites.Local;

import java.sql.SQLException;


public class AjouterlocalController {

        @FXML
        private TextField adressetxt;

        @FXML
        private TextField capacitetxt;

        @FXML
        private TextField categorietxt;

        @FXML
        private Button clean_button;

        @FXML
        private Button save_button;

        @FXML
        void clean(ActionEvent event) {

        }

    @FXML
        void savelocal() throws SQLException {
            savelocal(null);
        }

    @FXML
        void savelocal(ActionEvent event) throws SQLException {
        int capacite = Integer.parseInt(capacitetxt.getText());
        String adresse = adressetxt.getText();
        String categorie = categorietxt.getText();
        if (adressetxt.getText().compareTo("") == 0 || adressetxt.getText().compareTo("") == 0 || capacite == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Please fill all data");
            alert.showAndWait();
        } else if (categorietxt.getText().isEmpty() || categorietxt.getText().matches("^[0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Catégorie invalide : veuillez saisir une chaîne de caractères non numérique.");
            alert.showAndWait();
        } else if ((capacite <= 0) || !capacitetxt.getText().matches("^[0-9]*$")) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Capacité invalide : veuillez saisir un entier positif.");
            alert.showAndWait();
        } else {
            Service_l ser = new Service_l();
            Local local = new Local(adresse, capacite, categorie);
            ser.ajouterl(local);
        }


    }}