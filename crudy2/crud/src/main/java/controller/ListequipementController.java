package controller;

import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
import model.Equipement;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListequipementController implements Initializable {

    @FXML
    private TableView<Equipement> events_table;

    @FXML
    private TextField keywordTextFld;
    @FXML
    private TableColumn<Equipement, String> id_col;

    @FXML
    private TableColumn<Equipement, String> nom_col;

    @FXML
    private TableColumn<Equipement, Integer> nombre_col;

    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;
    @FXML
    void pdf(MouseEvent event) {

    }

    /*@FXML
    void showEvents(MouseEvent event) {

    }*/
    @FXML
    void refresh(MouseEvent event) {
        try {
            showEvents(); // Récupère les nouvelles données
            events_table.refresh(); // Rafraîchit l'affichage de la TableView
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    Connection mc;
    PreparedStatement ste;
    public ObservableList<Equipement> getEvents() throws SQLException {

        ObservableList<Equipement>Equipement_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql="SELECT * from equipement";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next())
                Equipement_list.add(new Equipement(rs.getInt("id") , rs.getString("nom"), rs.getInt("nombre")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Equipement_list;
    }

    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Equipement> listM = getEvents();
        // title_col.setCellValueFactory(cellData -> cellData.getValue());
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));

        nom_col.setCellValueFactory(new PropertyValueFactory<Equipement, String>("nom"));
        nombre_col.setCellValueFactory(new PropertyValueFactory<Equipement, Integer>("nombre"));
        events_table.setItems(listM);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        events_table.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 0) {
                Equipement selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });
    }




    public Equipement onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Equipement selectedEvent = events_table.getSelectionModel().getSelectedItem();
            return selectedEvent;

        }

        return new Equipement();
    }
    Equipement event ;
    public void setEventStorage(Equipement e){
        event= e;

    }
    public Equipement getEventStorage(){
        return event;
    }
    @FXML
    void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterequipement.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void getUpdateView(MouseEvent event) {
        try {

           System.out.println(event.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateequipement.fxml"));
            Parent root = loader.load();
            updateequipementController controller = loader.getController();
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


}
