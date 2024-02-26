package controller;
import Service.ServiceEq;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import model.Equipement;


import java.sql.SQLException;

public class AjouterequipementController {
    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnombre;
    private  final ServiceEq ser = new ServiceEq();
    public AjouterequipementController() throws SQLException {
    }
    @FXML
    void ajouterEquipement(ActionEvent event) {
        String nom = txtnom.getText();
        String nombreText = txtnombre.getText();




        // Validation du nom pour s'assurer qu'il ne contient que des lettres
        if (!nom.matches("[a-zA-Z]+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le nom ne peut contenir que des lettres.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode si la validation échoue
        }


// Validation du nombre pour s'assurer qu'il ne contient que des chiffres
        if (!nombreText.matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le nombre ne peut contenir que des chiffres.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode si la validation du nombre échoue
        }

        Integer nombre = Integer.parseInt(nombreText);
// Vérifier si le nombre est supérieur à zéro
        if (nombre <= 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le nombre doit être supérieur à zéro.");
            alert.showAndWait();
            return; // Arrête l'exécution de la méthode si le nombre est inférieur ou égal à zéro
        }
        Equipement equipement = new Equipement(nom,nombre);


     try {
        ser.ajouter(equipement); //appel ajouter
        System.out.println("Equipement ajouté avec succès!");
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText("Le equipement a été ajouté avec succès !");
        alert.showAndWait();

    } catch (SQLException e) {
        System.out.println("Erreur lors de l'ajout du equipement: " + e.getMessage());
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText("Une erreur s'est produite lors de l'ajout du equipement : " + e.getMessage());
        alert.showAndWait();
    }
}
}





