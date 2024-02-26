package controller;

import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Tournoi;
import java.sql.SQLException;
import model.Tournoi;
import java.sql.SQLException;
import static java.lang.Integer.parseInt;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
public class Update_tController {

    @FXML
    private Button Delete_button;

    @FXML
    private TextField dureetxt;

    @FXML
    private TextField localtxt;

    @FXML
    private TextField nbrpartxt;

    @FXML
    private TextField nomtxt;

    @FXML
    private TextField pricetxt;

    @FXML
    private TextField txtnomeq;

    @FXML
    private TextField typejeutxt;

    @FXML
    private Button update_button;



    @FXML
    void updateView(ActionEvent event) throws SQLException {

        //int duration = 0;
        int duree = Integer.parseInt(dureetxt.getText());
        int nombrePart= Integer.parseInt((nbrpartxt.getText()));
        String nom = nomtxt.getText();
        String location = String.valueOf(localtxt.getText());
        int price = Integer.parseInt(pricetxt.getText());
        String NomEquipe = txtnomeq.getText();
        String TypeJeu = String.valueOf(typejeutxt.getText());

        if (dureetxt.getText().isEmpty() || nbrpartxt.getText().isEmpty() || nomtxt.getText().isEmpty() ||
                localtxt.getText().isEmpty() ||  txtnomeq.getText().isEmpty() ||
                typejeutxt.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
        else if (price<=0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Prix invalide : veuillez saisir un entier positif.");
            alert.showAndWait();
            return; // Sortir de la méthode si le prix n'est pas valide
        }

// Vérification de la validité de la durée
       else if (duree<=0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Durée invalide : veuillez saisir un entier positif.");
            alert.showAndWait();
            return; // Sortir de la méthode si la durée n'est pas valide
        }

// Vérification de la validité du nombre de participants
        else if ( nombrePart<=0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("Nombre de participants invalide : veuillez saisir un entier positif.");
            alert.showAndWait();
            return; // Sortir de la méthode si le nombre de participants n'est pas valide
        }
        else if (nomtxt.getText().isEmpty() || nomtxt.getText().matches("^[0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("nom invalide : veuillez saisir une chaîne de caractères non numérique.");
            alert.showAndWait();
        }
        else if (txtnomeq.getText().isEmpty() || txtnomeq.getText().matches("^[0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("nom equipe invalide : veuillez saisir une chaîne de caractères non numérique.");
            alert.showAndWait();
        }
        else if (localtxt.getText().isEmpty() || localtxt.getText().matches("^[0-9]*$")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("nom de local invalide : veuillez saisir une chaîne de caractères non numérique.");
            alert.showAndWait();
        }
else {
            Service ser = new Service();
            Tournoi tournoi = new Tournoi(nom, NomEquipe, location, nombrePart, duree, TypeJeu, price);
            ser.update(tournoi, getId());
            ((Stage) typejeutxt.getScene().getWindow()).close();
        }

    }
    @FXML
    private void DeleteView(MouseEvent event) throws SQLException {

        Service cntrl = new Service() ;
        cntrl.delete(getId());
        ((Stage)typejeutxt.getScene().getWindow()).close();

    }
   // @FXML
    //void deleteV (ActionEvent event) throws SQLException {
      //  Service ser = new Service();
        //Tournoi tournoi= new Tournoi(); // Créez un objet Tournoi avec id=1 ou ajustez selon votre implémentation
        //tournoi.setId(1); // Fixez l'id à 1
        //ser.delete(tournoi);
   // }
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @FXML
    public void setEvent(Tournoi e){
        setId(e.getId());
        System.out.println(getId());
        dureetxt.setText(String.valueOf(e.getDuree()));
        localtxt.setText(e.getLocation());
        nbrpartxt.setText(String.valueOf(e.getNombreParticipants()));
        nomtxt.setText(e.getNom());
        pricetxt.setText(String.valueOf(e.getFraisParticipation()));
        txtnomeq.setText(e.getNomEquipe());
        typejeutxt.setText(e.getTypeJeu());

    }
}
