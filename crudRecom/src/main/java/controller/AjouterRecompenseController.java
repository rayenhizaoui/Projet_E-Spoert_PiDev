
package controller;

import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Recompense;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;

import static java.lang.Integer.parseInt;

public class AjouterRecompenseController {






    @FXML
    private AnchorPane id;

    @FXML
    private DatePicker txtdaterecompense;

    @FXML
    private TextField txtequipeGagnante;

    @FXML
    private TextField txtmontantRecompense;

    private  final Service ser = new Service();

    public AjouterRecompenseController() throws SQLException {
    }




    @FXML
    void ajouterRecompense(ActionEvent event) throws ParseException {


        String equipeGagnante = txtequipeGagnante.getText();

        int montantRecompense = parseInt(txtmontantRecompense.getText());

        LocalDate selectedDate = txtdaterecompense.getValue();
//controle saisie de equipegagnante
        if (equipeGagnante.isEmpty()) {
            // Afficher un message d'erreur si le champ est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir une équipe gagnante.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le champ est vide
        }

        if (montantRecompense <= 0) {
            // Afficher un message d'erreur si le montant de la récompense est inférieur ou égal à zéro
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le montant de la récompense doit être supérieur à zéro.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le montant de la récompense est inférieur ou égal à zéro
        }
        // Vérification que la date est sélectionnée
        if (selectedDate != null) {
            Date dateRecompense = Date.valueOf(selectedDate);
            // Création de l'objet Arbitre avec les valeurs récupérées
            Recompense recompense = new Recompense( equipeGagnante, montantRecompense,  dateRecompense);

            try {
                // Appel de la méthode ajouter() de votre service avec l'arbitre en paramètre
                ser.ajouter(recompense);
                System.out.println("Recompense ajouté avec succès!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La recompense a été ajouté avec succès !");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout d'une recompense: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout d'une recompense: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Affichage d'un message d'erreur si aucune date n'est sélectionnée
            System.out.println("Veuillez sélectionner une date d'embauche.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une date d'embauche.");
            alert.showAndWait();
        }
    }

}
