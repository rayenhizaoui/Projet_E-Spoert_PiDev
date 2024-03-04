package controllers;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import Entites.Local;

import javafx.collections.transformation.SortedList;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class ListLocalController implements Initializable {


        @FXML
        private TableColumn<Local, String> adressetxt;

        @FXML
        private TableColumn<Local, Integer> capacitetxt;

        @FXML
        private TableColumn<Local, String> categorietxt;

        @FXML
        private TableView<Local> events_table;

        @FXML
        private TableColumn<Local, Integer> id_col;

        @FXML
        private TextField keywordTextFld;

        @FXML
        private Button print_btn;

        @FXML
        private Button showEvents;
    Connection mc;
    PreparedStatement ste;
    Local event = null;
    public ObservableList<Local> getEvents() throws SQLException {

        ObservableList<Local> Tournoi_list = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql="SELECT * from local";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()){
                Tournoi_list.add(new Local(rs.getInt("id"),rs.getString("adresse"),rs.getInt("capacite"),rs.getString("categorie")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Tournoi_list;
    }
    public ObservableList<String> getLocalCombolist() {

        ObservableList<String> VenueList = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * from Local";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                VenueList.add(rs.getString("adresse"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return VenueList;
    }
        @FXML
        void getAddView(MouseEvent event) {

            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/ajouterlocal.fxml"));
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Updatelocal.fxml"));
                Parent root = loader.load();
                Update_lController controller = loader.getController();
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

    public Local getEventStorage(){
        return event;
    }

    @FXML
        void pdf(MouseEvent event) {

        }

        @FXML
        void showEvents() throws SQLException {
            ObservableList<Local> listM = getEvents();
            // title_col.setCellValueFactory(cellData -> cellData.getValue());
            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            adressetxt.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            categorietxt.setCellValueFactory(new PropertyValueFactory<Local, String>("categorie"));
            capacitetxt.setCellValueFactory(new PropertyValueFactory<Local, Integer>("capacite"));
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
                Local selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        }

 );
        FilteredList<Local> filteredData;
        try {
            filteredData = new FilteredList<>( getEvents());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        keywordTextFld.textProperty().addListener((observable,oldValue, newValue)->{
            filteredData.setPredicate(event ->{
                if (newValue.isEmpty() ||  newValue == null){
                    return true;
                }
                String searchKeyword =  newValue.toLowerCase();
                if (event.getAdresse().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;
                }
                else if (event.getCategorie().toLowerCase().indexOf(searchKeyword) > -1){
                    return true;

                }
                 else if (String.valueOf(event.getCapacite()).indexOf(searchKeyword) > -1){
                    return true;

                }
                else
                    return false;
            });
        });
        SortedList<Local> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(events_table.comparatorProperty());
        events_table.setItems(sortedData);
    }

    @FXML

    public Local onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Local selectedEvent = events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Local();
    }
    public void setEventStorage(Local e){
        event= e;
        System.out.println(event.toString());

    }
}


