package controllers;
import Entites.jeu;
import Service.ServiceJeu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherJeuControllers {

    @FXML
    private TableView<jeu> tableview;

    @FXML
    private TableColumn<jeu, Integer> colid;

    @FXML
    private TableColumn<jeu, String> colnom;

    @FXML
    private TableColumn<jeu, String> coltype;

    @FXML
    private TableColumn<jeu, Integer> colscore;

    @FXML
    private TableColumn<jeu, String> colresultat;
    @FXML
    private TextField txtRecherche;
    private final ServiceJeu ser = new ServiceJeu();

    @FXML
    void initialize() {
        try {
            List<jeu> list = ser.readAll();
            ObservableList<jeu> obers = FXCollections.observableList(list);
            tableview.setItems(obers);
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
            colscore.setCellValueFactory(new PropertyValueFactory<>("score"));
            colresultat.setCellValueFactory(new PropertyValueFactory<>("resultat"));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void retournerAInterfaceAjout(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterJeu.fxml"));
            Parent root = loader.load();
            AjouterJeuController ajouterJeuController = loader.getController();

            // Récupérer la scène actuelle et la modifier pour afficher l'interface d'ajout de jeux
            Stage stage = (Stage) tableview.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void recherche(ActionEvent event) {
        try {
            String searchTerm = txtRecherche.getText();

            // Si le champ de recherche est vide, afficher tous les jeux
            if (searchTerm.isEmpty()) {
                List<jeu> allGames = ser.readAll();
                ObservableList<jeu> allGamesObservable = FXCollections.observableList(allGames);
                tableview.setItems(allGamesObservable);
            } else {
                // Effectuer la recherche en utilisant la méthode search de ServiceJeu
                List<jeu> resultList = ser.search(searchTerm);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<jeu> obers = FXCollections.observableList(resultList);
                tableview.setItems(obers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    private void statistique(ActionEvent event) {
        List<Integer> scores = new ArrayList<>();
        for (jeu game : tableview.getItems()) {
            scores.add(game.getScore());
        }

        // Afficher les statistiques en utilisant le contrôleur de vue StatistiqueController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistique.fxml"));
        Parent root;
        try {
            root = loader.load();
            StatistiqueController controller = loader.getController();
            controller.afficherStatistique(scores);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}











