package controllers;

import Entites.equipe;
import Entites.jeu;
import Service.ServiceEquipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherEquipeControllers {
    @FXML
    public TableView<equipe> tableView;


    @FXML
    private TableColumn<equipe, Integer> colid;

    @FXML
    private TableColumn<equipe, Integer> colidjeu;

    @FXML
    private TableColumn<equipe, String> colnom;

    @FXML
    private TableColumn<equipe, Integer> colnbrjoueur;

    @FXML
    private TableColumn<equipe, String> collistejoueur;
    @FXML
    private TextField txtRecherche;

    private final ServiceEquipe serviceEquipe = new ServiceEquipe();

    @FXML
    void initialize() {
        try {
            List<equipe> list = serviceEquipe.readAll();
            ObservableList<equipe> obers = FXCollections.observableList(list);
            System.out.println(list);

            tableView.setItems(obers);
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colidjeu.setCellValueFactory(new PropertyValueFactory<>("idJeu"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colnbrjoueur.setCellValueFactory(new PropertyValueFactory<>("nbrJoueur"));
            collistejoueur.setCellValueFactory(new PropertyValueFactory<>("listeJoueur"));
        } catch (SQLException e) {
            throw new RuntimeException(e); // Vous pouvez choisir de gérer l'exception d'une autre manière ici
        }
    }

    @FXML
    void retournerAInterfaceAjout(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterEquipe.fxml"));
            Parent root=loader.load();
            AjouterEquipeController ajouterEquipeController = loader.getController();

            Stage stage = (Stage) tableView.getScene().getWindow(); // Récupérer la scène actuelle
            stage.setScene(new Scene(root)); // Changer la scène pour afficher l'interface d'ajout
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void recherche(ActionEvent event) {
        try {
            String searchTerm = txtRecherche.getText();

            // Si le champ de recherche est vide, afficher toutes les équipes
            if (searchTerm.isEmpty()) {
                List<equipe> allTeams = serviceEquipe.readAll();
                ObservableList<equipe> allTeamsObservable = FXCollections.observableList(allTeams);
                tableView.setItems(allTeamsObservable);
            } else {
                // Effectuer la recherche en utilisant la méthode search de ServiceEquipe
                List<equipe> resultList = serviceEquipe.search(searchTerm);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<equipe> obers = FXCollections.observableList(resultList);
                tableView.setItems(obers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<equipe> getEquipeList() {
        return tableView.getItems();
    }

    public void statistique1(ActionEvent event) {
        List<Integer> nbrjoueur = new ArrayList<>();
        for (equipe equipe : tableView.getItems()) {
            nbrjoueur.add(equipe.getNbrJoueur());
        }

        // Afficher les statistiques en utilisant le contrôleur de vue StatistiqueController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistique1.fxml"));
        Parent root;
        try {
            root = loader.load();
            Statistique1Controller controller = loader.getController();
            controller.afficherStatistique1(nbrjoueur);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

