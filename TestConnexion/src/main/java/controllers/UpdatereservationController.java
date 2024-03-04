package controllers;
import Service.Service;
import Service.ServiceEq;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;


import javafx.stage.Stage;
import Entites.Equipement;
import Entites.Reservation;
import  javafx.stage.Stage;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.Instant;
public class UpdatereservationController {
    @FXML
    private TextField txtdeposit;

    @FXML
    private DatePicker txtdatedebutres;

    @FXML
    private DatePicker txtdatefinres;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txttype;
    private Button update_button;
    private Service Serviceres ;
    private ServiceEq Serviceeq ;

    @FXML private ComboBox<Equipement> cbEquipement;
    @FXML
    public void initialize() throws SQLException {
        Serviceeq = new ServiceEq();
        ObservableList<Equipement> equipments = FXCollections.observableArrayList(Serviceeq.readAll());
        cbEquipement.setItems(equipments);
    }

    @FXML
    void deletereservation(ActionEvent event) throws SQLException {
        Service cntrl = new Service();
        cntrl.delete(getId());
        ((Stage) txtnom.getScene().getWindow()).close();
    }

    @FXML
    void updatereservation(ActionEvent event) throws SQLException {
        String nom = txtnom.getText();
        LocalDate selectedDate = txtdatedebutres.getValue();
        LocalDate selecteddate1 = txtdatefinres.getValue();
        Date datedebutres = Date.valueOf(selectedDate);
        Date datefinres = Date.valueOf(selecteddate1);
        Equipement selectedEquipement = cbEquipement.getValue();

        String type = txttype.getText();
        Float deposit = Float.valueOf(txtdeposit.getText());
        Reservation reservation = new Reservation (nom, datedebutres, datefinres, type, deposit,selectedEquipement);
        Service ser = new Service();
        ser.update(reservation, getId());
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
    public void setEvent(Reservation e) {

        setId(e.getId());
        System.out.println(getId());
        txtnom.setText(e.getNom());
        Date datedebutres = (Date) e.getDatedebutres();
        LocalDate parsedDate = datedebutres.toLocalDate();
         txtdatedebutres.setValue(parsedDate);;
         Date datefinres = (Date) e.getDatefinres();
         LocalDate parsedDate1 = datefinres.toLocalDate();
         txtdatefinres.setValue(parsedDate1);
         txttype.setText(e.getType());
        txtdeposit.setText(String.valueOf(e.getDeposit()));
         if (e.getEquipement() != null) {
             cbEquipement.getSelectionModel().select(e.getEquipement());
         }

    }


}
