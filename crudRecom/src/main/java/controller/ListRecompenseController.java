package controller;

import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Recompense;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListRecompenseController implements Initializable {

    @FXML
    private TableColumn<Recompense, Date> dateRecompense_col;

    @FXML
    private TableColumn<Recompense, String> equipeGagnante_col;

    @FXML
    private TableView<Recompense> events_table;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Recompense, Integer> montantRecompense_col;


    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;

    @FXML
    private Button Add_btn;

    @FXML
    void pdf(MouseEvent event) {

    }

    Connection mc;
    PreparedStatement ste;


    public ObservableList<Recompense> getEvents() throws SQLException {

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





    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Recompense> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        equipeGagnante_col.setCellValueFactory(new PropertyValueFactory<>("equipeGagnante")); // Vérifiez le nom de la propriété
        montantRecompense_col.setCellValueFactory(new PropertyValueFactory<>("montantRecompense"));
        dateRecompense_col.setCellValueFactory(new PropertyValueFactory<>("dateRecompense"));
        events_table.setItems(listM);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Définir le gestionnaire d'événements pour la table
        events_table.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                Recompense selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });

        // Définir le gestionnaire d'événements pour le bouton "Add"
        Add_btn.setOnAction(event -> getAddView(event));
    }


    public Recompense onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Recompense selectedEvent = (Recompense) events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Recompense();
    }
    Recompense event = null;
    public void setEventStorage(Recompense e){
        event= e;

    }
    public Recompense getEventStorage(){
        return event;
    }




    @FXML
    private void getUpdateView(MouseEvent event) {
        try {

            System.out.println(event.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRecompense.fxml"));
            Parent root = loader.load();
            UpdateRecompenseContoller controller = loader.getController();
            controller.setEvent(getEventStorage());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
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
}
