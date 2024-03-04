package controllers;

import Entites.User;
import Entites.UserRole;
import Service.ServiceUserCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.IntegerStringConverter;

import javax.management.relation.Role;
import java.sql.SQLException;
import java.util.List;

public class AfficherUserController {

    @FXML
    private Label lbname;

    @FXML
    private TableColumn<User, Integer> colid;
    @FXML
    private TableView<User> tableview;

    @FXML
    private TableColumn<User, Integer> colcin;

    @FXML
    private TableColumn<User, String> colusername;

    @FXML
    private TableColumn<User, Integer> colnumero;

    @FXML
    private TableColumn<User, String> colemail;

    @FXML
    private TableColumn<User, String> coladresse;

    @FXML
    private TableColumn<User, String> colpassword;

    @FXML
    private TableColumn<User, String> colrole;

    private final ServiceUserCrud ser = new ServiceUserCrud();


    @FXML
    void initialize() {
        try {
            List<User> list = ser.readAll();
            ObservableList<User> obers = FXCollections.observableList(list);

            // Rendre le TableView éditable
            tableview.setEditable(true);

            // Rendre les colonnes éditables si nécessaire
            colusername.setCellFactory(TextFieldTableCell.forTableColumn());
            colnumero.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            colemail.setCellFactory(TextFieldTableCell.forTableColumn());
            coladresse.setCellFactory(TextFieldTableCell.forTableColumn());
            colpassword.setCellFactory(TextFieldTableCell.forTableColumn());
            colrole.setCellFactory(TextFieldTableCell.forTableColumn());

            // Associer les données à la TableView
            tableview.setItems(obers);

            // Associer les propriétés de l'entité User aux colonnes de la TableView
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colcin.setCellValueFactory(new PropertyValueFactory<>("cin"));
            colusername.setCellValueFactory(new PropertyValueFactory<>("username"));
            colnumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            coladresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
            colrole.setCellValueFactory(new PropertyValueFactory<>("roles"));

            // Écouter les modifications dans les cellules éditables et mettre à jour la base de données
            colusername.setOnEditCommit(event -> {
                User user = event.getRowValue();
                user.setUsername(event.getNewValue());
                updateDatabase(user);
            });
            colnumero.setOnEditCommit(event -> {
                User user = event.getRowValue();
                user.setNumero(event.getNewValue());
                updateDatabase(user);
            });
            colemail.setOnEditCommit(event -> {
                User user = event.getRowValue();
                user.setEmail(event.getNewValue());
                updateDatabase(user);
            });
            coladresse.setOnEditCommit(event -> {
                User user = event.getRowValue();
                user.setAdresse(event.getNewValue());
                updateDatabase(user);
            });
            colpassword.setOnEditCommit(event -> {
                User user = event.getRowValue();
                user.setPassword(event.getNewValue());
                updateDatabase(user);
            });
            colrole.setOnEditCommit(event -> {
                User user = event.getRowValue();
                user.setRoles(event.getNewValue());
                updateDatabase(user);
            });
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }





    private void updateDatabase(User user) {
        ser.modifierUtilisateur(user); // Utilisez votre service pour mettre à jour les données dans la base de données
        // Affichez un message de confirmation à l'utilisateur si nécessaire
    }

    private void refreshTableView() {
        try {
            tableview.getItems().clear();
            List<User> userList = ser.readAll();
            tableview.getItems().addAll(userList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

/*    @FXML
    private TextField txtcins;
*/
    public void SupprimerUser(ActionEvent actionEvent) {
        User selectedUser = tableview.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            ser.supprimerUtilisateur(selectedUser);
            refreshTableView();
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Veuillez sélectionner un utilisateur à supprimer.");
        }
    }
}
