package controllers;

import Entites.Spectateur;
import Entites.User;
import Service.ServiceSpectateurCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.sql.SQLException;
import java.util.List;

public class AfficherSpectateurController {





    @FXML
    private Label lbnames;

    @FXML
    private TableView<Spectateur> tableviewss;

    @FXML
    private TableColumn<Spectateur, Integer> colidss;

    @FXML
    private TableColumn<Spectateur, String> colnomss;

    @FXML
    private TableColumn<Spectateur, String> colprenomss;

    @FXML
    private TableColumn<Spectateur, Integer> colagess;

    private final ServiceSpectateurCrud ser=new ServiceSpectateurCrud();

    @FXML
    void supprimerSpectateur(ActionEvent event) {
        // Récupérer l'utilisateur sélectionné dans le TableView
        Spectateur selectedSpectateur = tableviewss.getSelectionModel().getSelectedItem();

        if (selectedSpectateur != null) {
            // Appeler la méthode supprimerUtilisateur de votre service avec l'utilisateur sélectionné
            ser.supprimerSpectateur(selectedSpectateur);
            // Suppression réussie, rafraîchir le TableView pour refléter les changements
            refreshTableView();
            // Affichez un message de confirmation à l'utilisateur si nécessaire
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            // Affichez un message à l'utilisateur indiquant qu'aucun utilisateur n'est sélectionné
            System.out.println("Veuillez sélectionner un utilisateur à supprimer.");
        }
    }






        @FXML
        void initialize() {
            try {
                List<Spectateur> list = ser.readAll();
                ObservableList<Spectateur> obers = FXCollections.observableList(list);

                // Rendre le TableView éditable
                tableviewss.setEditable(true);

                // Rendre les colonnes éditables si nécessaire
                colnomss.setCellFactory(TextFieldTableCell.forTableColumn());
                colprenomss.setCellFactory(TextFieldTableCell.forTableColumn());
                colagess.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));



                // Associer les données à la TableView
                tableviewss.setItems(obers);

                // Associer les propriétés de l'entité User aux colonnes de la TableView
                colidss.setCellValueFactory(new PropertyValueFactory<>("id"));
                colnomss.setCellValueFactory(new PropertyValueFactory<>("nom"));
                colprenomss.setCellValueFactory(new PropertyValueFactory<>("prenom"));
                colagess.setCellValueFactory(new PropertyValueFactory<>("age"));

                // Écouter les modifications dans les cellules éditables et mettre à jour la base de données
                colnomss.setOnEditCommit(event -> {
                    Spectateur spectateur = event.getRowValue();
                    spectateur.setNom(event.getNewValue());
                    updateDatabase(spectateur);
                });
                colprenomss.setOnEditCommit(event -> {
                    Spectateur spectateur = event.getRowValue();
                    spectateur.setPrenom(event.getNewValue());
                    updateDatabase(spectateur);
                });
                colagess.setOnEditCommit(event -> {
                    Spectateur spectateur = event.getRowValue();
                    spectateur.setAge(event.getNewValue());
                    updateDatabase(spectateur);
                });


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        private void updateDatabase(Spectateur spectateur) {
            ser.modifierSpectateur(spectateur); // Utilisez votre service pour mettre à jour les données dans la base de données
            // Affichez un message de confirmation à l'utilisateur si nécessaire
        }











        // Méthode pour rafraîchir le TableView après la suppression
        private void refreshTableView() {
            try {
                // Effacer la liste actuelle des utilisateurs dans le TableView
                tableviewss.getItems().clear();
                // Recharger la liste des utilisateurs à partir de la base de données
                List<Spectateur> spectateurList = ser.readAll();
                // Mettre à jour le TableView avec la nouvelle liste des utilisateurs
                tableviewss.getItems().addAll(spectateurList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }



