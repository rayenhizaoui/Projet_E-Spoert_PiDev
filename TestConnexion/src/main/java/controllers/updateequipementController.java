package controllers;

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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import Entites.Equipement;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Map;

public class updateequipementController {

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnombre;
    @FXML private TextField tfImagePath;


    @FXML
    void updateequipement(ActionEvent event) throws SQLException {
        String nom = txtnom.getText();
        int nombre = Integer.parseInt(txtnombre.getText());
        String image = tfImagePath.getText() ;
        String qrCodeData = generateQRCodeData(nom, nombre);
        String qrCodeImage = generateQRCode(qrCodeData);
        Equipement equipement = new Equipement(nom, nombre,image,qrCodeImage); // Utilisation du chemin d'accès à l'image
        ServiceEq ser = new ServiceEq();
        ser.update(equipement, getId());
        ((Stage) txtnom.getScene().getWindow()).close();
    }

    @FXML
    void deleteequipement(ActionEvent event) throws SQLException {
        ServiceEq cntrl = new ServiceEq();
        cntrl.delete(getId());
        ((Stage) txtnom.getScene().getWindow()).close();
    }

    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @FXML
    public void setEvent(Equipement e) {
        setId(e.getId());
        System.out.println(getId());
        txtnom.setText(e.getNom());
        txtnombre.setText(String.valueOf(e.getNombre()));
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
