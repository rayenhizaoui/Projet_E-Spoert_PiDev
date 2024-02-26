package controller;
import Service.Service;
import Service.ServiceEv;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import model.Evenement;


import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import static java.lang.Integer.parseInt;

public class AjouterEvenementController {

    @FXML
    private AnchorPane id;

    @FXML
    private DatePicker txtdateEvent;

    @FXML
    private TextField txtduree;

    @FXML
    private TextField txtlieu;

    @FXML
    private TextField txtnomEvent;




    private  final ServiceEv ser = new ServiceEv();
    public AjouterEvenementController() throws SQLException {

    }




    @FXML
    void ajouterEvenement(ActionEvent event) throws ParseException {


        String nomEvent = txtnomEvent.getText();

        String lieu = (txtlieu.getText());

        LocalDate selectedDate = txtdateEvent.getValue();
        int duree = parseInt(txtduree.getText());

        // Vérifier si le champ nomEvent est vide
        if (nomEvent.isEmpty()) {
            // Afficher un message d'erreur si le champ nomEvent est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un nom pour l'événement.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le champ nomEvent est vide
        }

        // Vérifier si le champ lieu est vide
        if (lieu.isEmpty()) {
            // Afficher un message d'erreur si le champ lieu est vide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez saisir un lieu pour l'événement.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le champ lieu est vide
        }

         // Vérifier si la durée est un entier valid
        try {
            duree = Integer.parseInt(String.valueOf(duree));
        } catch (NumberFormatException e) {
            // Afficher une alerte si la durée n'est pas un entier valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La durée doit être un entier valide.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si la durée n'est pas valide
        }

        // Vérification que la date est sélectionnée
        if (selectedDate != null) {
            Date dateEvent = Date.valueOf(selectedDate);

            // Création de l'objet Arbitre avec les valeurs récupérées
            Evenement evenement = new Evenement( nomEvent, lieu,  dateEvent , duree );

            try {
                // Appel de la méthode ajouter() de votre service avec l'arbitre en paramètre
                ser.ajouter(evenement);
                System.out.println("evenement ajouté avec succès!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("L' evenement a été ajouté avec succès !");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout d'un evenement: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout d'un  evenement: " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            // Affichage d'un message d'erreur si aucune date n'est sélectionnée
            System.out.println("Veuillez sélectionner une date d'evenement.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner une date d'evenement.");
            alert.showAndWait();
        }
    }


}


