package controller;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Reservation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.util.Callback;
import javafx.scene.control.cell.TextFieldTableCell;
import java.text.SimpleDateFormat;
public class ListreservationController implements Initializable {


    @FXML
    private TableColumn<Reservation, String> nom_col;
    @FXML
    private TableColumn<Reservation, String> type_col;

    @FXML
    private TableColumn<Reservation, Date> datedebutres_col;

    @FXML
    private TableColumn<Reservation, Date> datefinres_col;

    @FXML
    private TableColumn<Reservation, Float> deposit_col;

    @FXML
    private TableView<Reservation> events_table;

    @FXML
    private TableColumn<Reservation, Integer> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;


    @FXML
    void pdf(MouseEvent event) {

    }

   /* @FXML
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
// liste pour récupérer les donéées de la base
    public ObservableList<Reservation> getEvents() throws SQLException {

        ObservableList<Reservation> Reservation_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT * from reservation";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next())
                Reservation_list.add(new Reservation(rs.getInt("id"), rs.getString("nom"), rs.getDate("datedebutres"), rs.getDate("datefinres"), rs.getString("type"), rs.getFloat("deposit")));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Reservation_list;
    }
//afficher les réservations dans une table
    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Reservation> listM = getEvents();
        // title_col.setCellValueFactory(cellData -> cellData.getValue());
        id_col.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Reservation, String>("nom"));
        datedebutres_col.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("datedebutres"));
        datefinres_col.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("datefinres"));
        type_col.setCellValueFactory(new PropertyValueFactory<Reservation, String>("type"));
        deposit_col.setCellValueFactory(new PropertyValueFactory<Reservation, Float>("deposit"));
        events_table.setItems(listM);

    }
// l'initialisation de votre interface utilisateur lorsque le contrôleur est chargé
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        events_table.setOnMouseClicked((MouseEvent event) -> {//double clique affichage
            if (event.getClickCount() > 0) {
                Reservation selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });


    }
//obtenir l'élément sélectionné dans la tabl
    public Reservation onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Reservation selectedEvent = (Reservation) events_table.getSelectionModel().getSelectedItem();
            return selectedEvent;

        }

        return new Reservation();


    }

    Reservation event = null;

    public void setEventStorage(Reservation e) {
        event = e;

    }

    public Reservation getEventStorage() {
        return event;
    }

    @FXML
    void getUpdateView(MouseEvent e) {
        try {

            System.out.println(e.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatereservation.fxml"));
            Parent root = loader.load();
            UpdatereservationController controller = loader.getController();
            controller.setEvent(getEventStorage());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            System.out.println("erreur");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterreservation.fxml"));
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





