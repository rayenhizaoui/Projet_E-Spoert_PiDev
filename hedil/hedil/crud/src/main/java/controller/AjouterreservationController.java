package controller;











import java.time.LocalDate;
import Service.Service;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.Equipement;
import model.Reservation;
import Service.Service;
import java.sql.Date;
import java.sql.SQLException;

import Service.ServiceEq;
public class AjouterreservationController {
    @FXML
    private DatePicker txtdatedebutres;

    @FXML
    private DatePicker txtdatefinres;

    @FXML
    private TextField txtdeposit;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txttype;

    private ServiceEq Serviceeq ;
    private final Service ser = new Service();
    @FXML private ComboBox<Equipement> cbEquipement;
    public AjouterreservationController() throws SQLException {
    }

    @FXML
    public void initialize() throws SQLException {
        Serviceeq = new ServiceEq();
        ObservableList<Equipement> equipments = FXCollections.observableArrayList(Serviceeq.readAll());
        cbEquipement.setItems(equipments);
    }
    @FXML
    void ajouterReservation(ActionEvent event) {
        String nom = txtnom.getText();
        LocalDate selectedDate = txtdatedebutres.getValue();
        LocalDate selecteddate1 = txtdatefinres.getValue();
        Date datedebutres = Date.valueOf(selectedDate);
        Date datefinres = Date.valueOf(selecteddate1);
        Equipement selectedEquipement = cbEquipement.getValue();
        System.out.println(selectedEquipement);
        String type = txttype.getText();
        // Float deposit = Float.valueOf(txtdeposit.getText());
        Float deposit = null;
         // Validation du nom et du type
        if (nom.matches("[a-zA-Z]+") && type.matches("[a-zA-Z]+")) {
            // Validation du dépôt
            try {
                deposit = Float.parseFloat(txtdeposit.getText());
            } catch (NumberFormatException e) {
                System.out.println("Erreur de format pour le dépôt: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Le montant du dépôt doit être un nombre décimal.");
                alert.showAndWait();
                return; // Sortie de la méthode si le dépôt est invalide
            }


            Reservation reservation = new Reservation(nom, datedebutres, datefinres, type, deposit,selectedEquipement);


            try {
                ser.ajouter(reservation); // Appel de la méthode ajouter() de votre service avec le sponsor en paramètre
                System.out.println("reservation ajouté avec succès!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("La reservation a été ajouté avec succès !");
                alert.showAndWait();
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout du reservation: " + e.getMessage());
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(null);
                alert.setContentText("Une erreur s'est produite lors de l'ajout du reservation : " + e.getMessage());
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le nom et le type doivent contenir uniquement des lettres.");
            alert.showAndWait();
        }

    }
}



