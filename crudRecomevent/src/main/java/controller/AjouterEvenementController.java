package controller;
import Service.Service;
import Service.ServiceEv;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Evenement;
import model.Recompense;


import java.io.ByteArrayOutputStream;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

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

    @FXML private ComboBox<Recompense> cbRecompense;


    private  final ServiceEv ser = new ServiceEv();
    private  final Service serrec = new Service();
    public AjouterEvenementController() throws SQLException {

    }

    @FXML
    public void initialize() throws SQLException {

        ObservableList<Recompense> equipments = FXCollections.observableArrayList(serrec.readAll());
        cbRecompense.setItems(equipments);
    }


    @FXML
    void ajouterEvenement(ActionEvent event) throws ParseException {


        String nomEvent = txtnomEvent.getText();

        String lieu = (txtlieu.getText());

        LocalDate selectedDate = txtdateEvent.getValue();
        int duree = parseInt(txtduree.getText());
        Recompense selectedRecompense = cbRecompense.getValue();
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
        // Vérifier si le champ lieu contient uniquement des lettres
        if (!lieu.matches("^[a-zA-ZÀ-ÿ\\s]+$")) {
            // Afficher un message d'erreur si le champ ne contient pas uniquement des lettres
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Le champ 'Lieu' doit contenir uniquement des lettres.");
            alert.show();
            return; // Arrêter l'exécution de la méthode si le champ ne contient pas uniquement des lettres
        }

        // Vérifier si la durée est un entier valide
        if (txtduree.getText().length() != 1 || !txtduree.getText().matches("[1-9]")) {
            // Afficher une alerte si la durée n'est pas un entier valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("La durée doit être un chiffre compris entre 1 et 9.");
            alert.show();
            return;
        }

        // Vérification que la date est sélectionnée
        if (selectedDate != null) {
            Date dateEvent = Date.valueOf(selectedDate);
            String qrCodeData = generateQRCodeData(nomEvent, lieu, dateEvent, duree, selectedRecompense);
            String qrCodeImage = generateQRCode(qrCodeData);

            // Create the Evenement object with all details including the QR code
            Evenement evenement = new Evenement(nomEvent, lieu, dateEvent, duree, selectedRecompense, qrCodeImage);

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

    private String generateQRCodeData(String nomEvent, String lieu, Date dateEvent, int duree, Recompense selectedRecompense) {
        // Convert your Evenement data to a String format suitable for QR Code
        return "Event: " + nomEvent + ", Location: " + lieu + ", Date: " + dateEvent + ", Duration: " + duree + " hours";
    }

    private String generateQRCode(String data) {
        try {
            int size = 200;
            String fileType = "png";
            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

            BitMatrix matrix = new MultiFormatWriter().encode(new String(data.getBytes("UTF-8"), "UTF-8"),
                    BarcodeFormat.QR_CODE, size, size, hintMap);

            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            MatrixToImageWriter.writeToStream(matrix, fileType, pngOutputStream);
            byte[] pngData = pngOutputStream.toByteArray();
            String encodedImage = Base64.getEncoder().encodeToString(pngData);

            return encodedImage; // Return the Base64 encoded QR Code
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Handle this according to your needs
        }
    }


}


