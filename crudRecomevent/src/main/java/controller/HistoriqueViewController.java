package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Historique;

import java.util.Date;
import java.util.List;

public class HistoriqueViewController {
    @FXML
    private TableView<Historique> historiqueTable;
    @FXML
    private TableColumn<Historique, String> operationTypeColumn;
    @FXML
    private TableColumn<Historique, String> descriptionColumn;
    @FXML
    private TableColumn<Historique, Date> operationDateColumn;

    @FXML
    public void initialize() {
        // Set up the columns to use the Historique properties
        operationTypeColumn.setCellValueFactory(new PropertyValueFactory<>("operationType"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        operationDateColumn.setCellValueFactory(new PropertyValueFactory<>("operationDate"));
    }

    public void setHistoriqueData(List<Historique> historiqueList) {
        // Convert the list to an observable list and set it as the items for the table
        historiqueTable.setItems(FXCollections.observableArrayList(historiqueList));
    }
}
