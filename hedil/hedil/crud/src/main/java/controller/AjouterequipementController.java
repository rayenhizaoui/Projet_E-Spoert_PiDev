package controller;
import Service.ServiceEq;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Equipement;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.SQLException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

import javafx.scene.control.Button;
public class AjouterequipementController  {
    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnombre;
    @FXML private TextField tfImagePath;
    private  final ServiceEq ser = new ServiceEq();

    public AjouterequipementController() throws SQLException, SQLException {
    }
    @FXML
    void ajouterEquipement(ActionEvent event) {
        String nom = txtnom.getText();
        String nombreText = txtnombre.getText();
        String Image = tfImagePath.getText() ;



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
        String qrCodeData = generateQRCodeData(nom, nombre);
        String qrCodeImage = generateQRCode(qrCodeData);

        Equipement equipement = new Equipement(nom, nombre , Image,qrCodeImage);




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


    @FXML
    private void handleUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            try {

                Path resourcesDir = Path.of("src/main/resources/images");
                if (!Files.exists(resourcesDir)) {
                    Files.createDirectories(resourcesDir);
                }


                Path imagePath = resourcesDir.resolve(selectedFile.getName());
                Files.copy(selectedFile.toPath(), imagePath, StandardCopyOption.REPLACE_EXISTING);


                tfImagePath.setText(selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to upload image.");
                alert.showAndWait();
            }
        }
    }
    private String generateQRCodeData(String nom, Integer nombre) {
        // Convert your Evenement data to a String format suitable for QR Code
        return "nomEqupement: " + nom + ", nombre: " + nombre  ;
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





