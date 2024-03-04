package controller;
import javafx.scene.control.*;

import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Evenement;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.ResourceBundle;
public class frontEcontroller {
    @FXML
    private Button Add_btn;
    @FXML
    private TableColumn<Evenement, String> qrCodeColumn;

    @FXML
    private TableColumn<Evenement, Integer> id_col;

    @FXML
    private TableColumn<Evenement, String> nomEvent_col;


    @FXML
    private TableColumn<Evenement, String> lieu_col;

    @FXML
    private TableColumn<Evenement, String> dateEvent_col;

    @FXML
    private TableColumn<Evenement, Integer> duree_col;


    @FXML
    private TableView<Evenement> events_table;

    @FXML
    private TextField searchTextFld;

    Connection mc;
    PreparedStatement ste;
    ResultSet rs;


    public void initialize(URL url, ResourceBundle resourceBundle) {
        readAllEvents();
        setupQrCodeColumn();
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void setupQrCodeColumn() {

        qrCodeColumn.setCellFactory(column -> new TableCell<Evenement, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    byte[] decodedBytes = Base64.getDecoder().decode(item);
                    Image image = new Image(new ByteArrayInputStream(decodedBytes));

                    imageView.setImage(image);
                    imageView.setFitHeight(50); // Adjust size as needed
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });
    }

    @FXML
    private void showEvents() throws SQLException {
        ObservableList<Evenement> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomEvent_col.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        lieu_col.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        dateEvent_col.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        duree_col.setCellValueFactory(new PropertyValueFactory<>("duree"));
        qrCodeColumn.setCellValueFactory(new PropertyValueFactory<>("qrCode"));
        events_table.setItems(listM);
    }

    private ObservableList<Evenement> getEvents() throws SQLException {
        ObservableList<Evenement> evenement_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT * from Evenement";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                evenement_list.add(new Evenement(
                        rs.getInt("id"),
                        rs.getString("nomEvent"),
                        rs.getString("lieu"),
                        rs.getDate("dateEvent"),
                        rs.getInt("duree"),
                        rs.getString("QrCode")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenement_list;
    }

    private void readAllEvents() {
        ObservableList<Evenement> evenementList = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT * FROM Evenement";
        try {
            ste = mc.prepareStatement(sql);
            rs = ste.executeQuery();
            while (rs.next()) {
                Evenement evenement = new Evenement(
                        rs.getInt("id"), rs.getString("nomEvent"),
                        rs.getString("lieu"),
                        rs.getDate("dateEvent"),
                        rs.getInt("duree"),
                        rs.getString("QrCode"));
                evenementList.add(evenement);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void getAddView(javafx.event.ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterEvenement.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

