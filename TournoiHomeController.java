package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class TournoiHomeController {

    @FXML
    private StackPane ContentArea;

    @FXML
    private Button EventMenu;

    @FXML
    private Button VenueMenu;

    @FXML
    private Button btnPackages;

    @FXML
    private ImageView close_btn;

    @FXML
    private Button btnBack;
    @FXML
    private Button maps;
    @FXML
    public void eventMenu(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/tableView.fxml"));
        ContentArea.getChildren().clear();
        ContentArea.getChildren().add(fxml);
    }
    @FXML
    public void VenueMenu(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/tablelocal.fxml"));
        ContentArea.getChildren().clear();
        ContentArea.getChildren().add(fxml);
    }
    public void WeatherMenu(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/Weather.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(fxml);
    }
    @FXML
    public void maps(ActionEvent actionEvent) throws IOException {
        Parent fxml = FXMLLoader.load(getClass().getResource("/maps.fxml"));
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(fxml);
    }
}