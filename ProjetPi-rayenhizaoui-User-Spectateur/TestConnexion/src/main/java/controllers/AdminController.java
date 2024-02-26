package controllers;

import Entites.User;
import Service.ServiceUserCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;


import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;






import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;



public class AdminController {


    @FXML
    private ChoiceBox<String> choicerole;
    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtnumero;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtpassword;



    @FXML
    private TextField txtcin;



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



    private final ServiceUserCrud ser=new ServiceUserCrud();






    @FXML
    void AjouterUser(ActionEvent event) {
        int cin = Integer.parseInt(txtcin.getText());
        String username = txtusername.getText();
        int numero = Integer.parseInt(txtnumero.getText());
        String email = txtemail.getText();
        String adresse = txtadresse.getText();
        String password = txtpassword.getText();
        String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox
        refreshTableView();
        User u4 = new User(cin, username, numero, email, adresse, password, roles);
        refreshTableView();
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("User ajouté avec succès");
        alert1.showAndWait();
        refreshTableView();
        try {
            ser.ajouter(u4);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
        refreshTableView();
    }




    @FXML
    void initialize() {


        // Créer une liste de rôles disponibles
        List<String> roles = Arrays.asList("SPECTATEUR","ADMIN");

        // Convertir la liste en ObservableList pour pouvoir l'utiliser avec la ChoiceBox
        ObservableList<String> rolesList = FXCollections.observableArrayList(roles);

        // Remplir la ChoiceBox avec la liste des rôles
        choicerole.setItems(rolesList);




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








