package controller;

import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import model.Tournoi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListTournoiController implements Initializable {

    @FXML
    private TableColumn<Tournoi, Integer> duree_col;

    @FXML
    private TableView<Tournoi> events_table;

    @FXML
    private TableColumn<Tournoi, Integer> fraisParticipant_col;

    @FXML
    private TableColumn<Tournoi, Integer> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Tournoi, String> location_col;

    @FXML
    private TableColumn<Tournoi, Integer> nbParticipants_col;

    @FXML
    private TableColumn<Tournoi, String> nomEquipe_col;

    @FXML
    private TableColumn<Tournoi, String> nom_col;

    @FXML
    private Button print_btn;

    @FXML
    private TableColumn<Tournoi, String> typeJeu_col;




    @FXML
    void pdf(MouseEvent event) {

    }
    Connection mc;
    PreparedStatement ste;
    public ObservableList<Tournoi> getEvents() throws SQLException {

        ObservableList<Tournoi> Tournoi_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCnx();
        String sql="SELECT * from tournoi";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()){
                Tournoi_list.add(new Tournoi(rs.getInt("id"),rs.getString("nom"), rs.getString("nomEquipe"), rs.getInt("nombreParticipants"), rs.getInt("duree"), rs.getString("typeJeu"), rs.getInt("fraisParticipation"),rs.getString("location") ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Tournoi_list;
    }

    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Tournoi> listM = getEvents();
        // title_col.setCellValueFactory(cellData -> cellData.getValue());
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        duree_col.setCellValueFactory(new PropertyValueFactory<>("duree"));
        location_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("location"));
        fraisParticipant_col.setCellValueFactory(new PropertyValueFactory<Tournoi, Integer>("fraisParticipation"));
        nbParticipants_col.setCellValueFactory(new PropertyValueFactory<Tournoi, Integer>("nombreParticipants"));
        nomEquipe_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomEquipe"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nom"));
        typeJeu_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("typeJeu"));
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
                        Tournoi selectedEvent = onEdit();
                        setEventStorage(selectedEvent);
                        getUpdateView(event);
                    }
                }


        );



    }
    public Tournoi onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Tournoi selectedEvent = events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Tournoi();
    }
    Tournoi event = null;
    public void setEventStorage(Tournoi e){
        event= e;

    }
    public Tournoi getEventStorage(){
        return event;
    }

    @FXML
    private void getUpdateView(MouseEvent e) {
        try {

            System.out.println(e.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEventView.fxml"));
            Parent root = loader.load();
            Update_tController controller = loader.getController();
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
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Ajouter_t.fxml"));
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
