package controller;

import Service.Service;
import Service.ServiceEv;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Evenement;
import model.Recompense;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

public class UpdateEvenementController {

    @FXML
    private Button Delete_button;

    @FXML
    private DatePicker txtdateEvent;

    @FXML
    private TextField txtduree;

    @FXML
    private TextField txtlieu;

    @FXML
    private TextField txtnomEvent;

    @FXML
    private Button update_button;

    @FXML
    private TextField txtidrecompense;

    @FXML private ComboBox<Recompense> cbRecompense;

    private  final Service serrec = new Service();

    public UpdateEvenementController() throws SQLException {
    }
    private Evenement currentEvenement;

    @FXML
    public void initialize() throws SQLException {

        ObservableList<Recompense> equipments = FXCollections.observableArrayList(serrec.readAll());
        cbRecompense.setItems(equipments);
        cbRecompense.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (currentEvenement != null) {
                currentEvenement.setRecompense(newVal);
            }
        });
        System.out.println(cbRecompense.getValue());

    }
    @FXML
    void deleteE(ActionEvent event) throws SQLException {
        ServiceEv cntrl = new ServiceEv() ;
        cntrl.delete(getId());
        ((Stage)txtnomEvent.getScene().getWindow()).close();

    }

    @FXML
    void updateE(ActionEvent event) throws SQLException {
        // Ensure currentEvenement is not null
        if (currentEvenement == null) {

            return;
        }
        Recompense selectedEquipement = cbRecompense.getValue();
        System.out.println("aaaaaaaaaaaaa"+ selectedEquipement);
        currentEvenement.setRecompense(selectedEquipement);
        currentEvenement.setNomEvent(txtnomEvent.getText());
        currentEvenement.setLieu(txtlieu.getText());
        currentEvenement.setDuree(Integer.parseInt(txtduree.getText()));

        LocalDate selectedDate = txtdateEvent.getValue();
        if (selectedDate != null) {
            currentEvenement.setDateEvent(Date.valueOf(selectedDate));
        }
        String qrCodeData = generateQRCodeData(txtnomEvent.getText(), txtlieu.getText(), Date.valueOf(selectedDate), Integer.parseInt(txtduree.getText()), selectedEquipement);
        String qrCodeImage = generateQRCode(qrCodeData);
        currentEvenement.setQrCode(qrCodeImage);
        // Perform the update operation
        ServiceEv ser = new ServiceEv();
        ser.update(currentEvenement, currentEvenement.getId());
    }

    private int id;
    private int getId() {
        return id;
    }

    private void setId(int id) {
        this.id=id;


    }


    public void setEvent(Evenement e) {
        this.currentEvenement = e; // Store the current Evenement for later use
        if (e != null) {
            setId(e.getId());
            txtnomEvent.setText(e.getNomEvent());
            txtlieu.setText(e.getLieu());
            txtduree.setText(String.valueOf(e.getDuree()));

            // Conversion of java.sql.Date to LocalDate for the DatePicker
            Date dateEvent = (Date) e.getDateEvent();
            LocalDate parsedDate = dateEvent.toLocalDate();
            txtdateEvent.setValue(parsedDate);
            System.out.println("sqdqsssssssss" + e.getRecompense());
            cbRecompense.getSelectionModel().select(e.getRecompense());


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
