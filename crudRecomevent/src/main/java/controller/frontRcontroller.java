package controller;
import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Evenement;
import model.Recompense;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Date;
import java.util.ResourceBundle;
public class frontRcontroller {
    @FXML
    private TableColumn<Recompense, Date> dateRecompense_col;

    @FXML
    private TableColumn<Recompense, String> equipeGagnante_col;

    @FXML
    private TableView<Recompense> events_table;
    @FXML
    private TableColumn<Recompense, String> qrCodeColumn;
    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Recompense, Integer> montantRecompense_col;

    @FXML
    private TextField search_btn;
    @FXML
    private Button print_btn;
    @FXML
    private Button events_btn;
    @FXML
    private Button showEvents;

    @FXML
    private Button Add_btn;
    Connection mc;
    PreparedStatement ste;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        readAllEvents();

        showEvents();
    }
    public void ListRecompenseController() throws SQLException {
    }
    @FXML
    private void showEvents() {
        ObservableList<Recompense> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        equipeGagnante_col.setCellValueFactory(new PropertyValueFactory<>("equipeGagnante")); // Vérifiez le nom de la propriété
        montantRecompense_col.setCellValueFactory(new PropertyValueFactory<>("montantRecompense"));
        dateRecompense_col.setCellValueFactory(new PropertyValueFactory<>("dateRecompense"));

        events_table.setItems(listM);

    }

    private ObservableList<Recompense> getEvents() {
        ObservableList<Recompense> recompenses_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT * from recompense";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                recompenses_list.add(new Recompense(rs.getInt("id"),  rs.getString("equipeGagnante"),  rs.getInt("montantRecompense"), rs.getDate("dateRecompense")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recompenses_list;
    }




    private void readAllEvents() {
        ObservableList<Recompense> recompensesList = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT * FROM Recompense";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs;
            rs = ste.executeQuery();
            while (rs.next()) {
                Recompense recompense = new Recompense(
                        rs.getString("equipeGagnante"),
                        Integer.parseInt(rs.getString("montantRecompense")),
                        rs.getDate("dateRecompense"));

                recompensesList.add(recompense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterRecompense.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    public void voirevents(ActionEvent actionEvent) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/frontEV.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
