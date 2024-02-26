package controller;
import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
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
import model.Evenement;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ListeEvenementController  implements Initializable {
    @FXML
    private Button Add_btn;

    @FXML
    private TableColumn<?, ?> dateEvent_col;

    @FXML
    private TableColumn<?, ?> duree_col;

    @FXML
    private TableView<Evenement> events_table;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<?, ?> lieu_col;

    @FXML
    private TableColumn<?, ?> nomEvent_col;

    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;





    @FXML
    void pdf(MouseEvent event) {

    }



    Connection mc;
    PreparedStatement ste;


    public ObservableList<Evenement> getEvents() throws SQLException {

        ObservableList<Evenement> evenement_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT * from Evenement";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                evenement_list.add(new Evenement( rs.getInt("id"),  rs.getString("nomEvent"),  rs.getString("lieu"), rs.getDate("dateEvent"),  rs.getInt("duree") ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenement_list;
    }

    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Evenement> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomEvent_col.setCellValueFactory(new PropertyValueFactory<>("nomEvent")); // Vérifiez le nom de la propriété
        lieu_col.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        dateEvent_col.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        duree_col.setCellValueFactory(new PropertyValueFactory<>("duree"));
        events_table.setItems(listM);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Définir le gestionnaire d'événements pour la table
        events_table.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                Evenement selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });

        // Définir le gestionnaire d'événements pour le bouton "Add"
        Add_btn.setOnAction(event -> {
            getAddView(event);

        });



    }


    @FXML
    private void getUpdateView(MouseEvent event) {
        Evenement selectedEvent = onEdit();
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvenement.fxml"));
                Parent root = loader.load();
                UpdateEvenementController controller = loader.getController();
                controller.setEvent(selectedEvent); // Passer l'événement sélectionné au contrôleur de la vue de mise à jour
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





    private Evenement getEventStorage() {
        return event;

    }

    Evenement event = null;

    private void setEventStorage(Evenement e) {
        Evenement event = e;
    }





    private Evenement onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Evenement selectedEvent = (Evenement) events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Evenement();
    }







    @FXML
    private void getAddView(ActionEvent event) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterEvenement.fxml"));
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












