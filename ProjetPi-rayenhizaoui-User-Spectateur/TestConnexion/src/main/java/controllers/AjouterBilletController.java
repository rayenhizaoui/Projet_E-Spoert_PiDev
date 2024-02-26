package controllers;
import Entites.Billet;
import Entites.Spectateur;
import Service.ServiceBillet;
import Service.ServiceSpectateurCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class AjouterBilletController {




    @FXML
    private TextField txtcin;

    @FXML
    private TextField txttype;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtplace;

    @FXML
    private TextField txtid;


    private final ServiceBillet ser=new ServiceBillet();




    @FXML
    void initialize() {

        // Ajouter un gestionnaire d'événements pour le champ CIN
        txtcin.setOnKeyReleased(event -> {
            String text = txtcin.getText();
            // Vérifier si le texte n'est pas composé uniquement de chiffres
            if (!text.matches("\\d*")) {
                // Si non, remplacer tous les caractères non numériques par une chaîne vide
                txtcin.setText(text.replaceAll("[^\\d]", ""));
                // Déplacer le curseur à la fin du champ
                txtcin.positionCaret(txtcin.getText().length());
            }

            // Limiter le nombre maximal de chiffres à 8
            if (text.length() > 8) {
                // Si le nombre de chiffres dépasse 8, raccourcir le texte à 8 caractères
                txtcin.setText(text.substring(0, 8));
                // Déplacer le curseur à la fin du champ
                txtcin.positionCaret(txtcin.getText().length());
            }
        });



// Ajouter un gestionnaire d'événements pour le champ de l'username
        txttype.setOnKeyReleased(event -> {
            // Obtenir le texte actuel dans le champ de l'username
            String text = txttype.getText();

            // Limiter le nombre maximal de caractères à 10
            if (text.length() > 10) {
                // Si le nombre de caractères dépasse 10, raccourcir le texte à 10 caractères
                txttype.setText(text.substring(0, 10));
                // Déplacer le curseur à la fin du champ
                txttype.positionCaret(txttype.getText().length());
            }
        });


// Ajouter un gestionnaire d'événements pour le champ de l'username
        txtplace.setOnKeyReleased(event -> {
            // Obtenir le texte actuel dans le champ de l'username
            String text = txtplace.getText();

            // Limiter le nombre maximal de caractères à 10
            if (text.length() > 10) {
                // Si le nombre de caractères dépasse 10, raccourcir le texte à 10 caractères
                txtplace.setText(text.substring(0, 10));
                // Déplacer le curseur à la fin du champ
                txtplace.positionCaret(txtplace.getText().length());
            }
        });

// Ajouter un gestionnaire d'événements pour le champ d'email
        txtemail.setOnKeyReleased(event -> {
            // Obtenir le texte actuel dans le champ d'email
            String text = txtemail.getText();

            // Limiter le nombre maximal de caractères à 20
            if (text.length() > 20) {
                // Si le nombre de caractères dépasse 20, raccourcir le texte à 20 caractères
                txtemail.setText(text.substring(0, 20));
                // Déplacer le curseur à la fin du champ
                txtemail.positionCaret(txtemail.getText().length());
            }


        });

    }







    @FXML
    void AfficherBillet(ActionEvent event) {
        try{
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AfficherBillet.fxml"));
            Parent root=loader.load();
            txtcin.getScene().setRoot(root);
            AfficherSpectateurController dc = loader.getController();

        }   catch (IOException e){
            throw new RuntimeException(e);}
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void AjouterBillet(ActionEvent event) {
        int id = Integer.parseInt(txtid.getText());
        int cin = Integer.parseInt(txtcin.getText());
        String type = txttype.getText();
        String email = txtemail.getText();
        String place = txtplace.getText();
        Billet b4 = new Billet(id,cin,type,email,place);


        // Valider le champ de CIN
        if (String.valueOf(cin).length() != 8) {
            // Afficher une alerte indiquant que le CIN n'est pas valide
            afficherAlerte("Le CIN doit contenir exactement 8 chiffres");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }

        // Valider le champ d'email
        if (!email.contains("@") || !email.contains(".")) {
            // Afficher une alerte indiquant que l'email n'est pas valide
            afficherAlerte("L'email doit contenir à la fois '@' et '.'");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }


        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("Billet ajoute avec succes");
        alert1.showAndWait();

        try {
            ser.ajouter(b4);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

}
