package controllers;
import Entites.equipe;
import Entites.jeu;
import Service.ServiceJeu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;






import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Cell;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.SQLException;








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



    @FXML
    private TextField txtid;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txttype;

    @FXML
    private TextField txtscore;

    @FXML
    private TextField txtresultat;




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





    @FXML
    void supprimerjeu(ActionEvent event) {

        int id = Integer.parseInt(txtid.getText());

        // Appeler la méthode de suppression du service
        try {
            ser.delete(new jeu(id, "", "",0, ""));
            afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "Le jeu a été supprimé avec succès.");
            refreshTableView();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", "Une erreur s'est produite lors de la suppression du jeu : " + e.getMessage());
        }

    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }





    @FXML
    void modifierjeu(ActionEvent event) {
        int id=Integer.parseInt(txtid.getText());
        String nom=txtnom.getText();
        String type=txttype.getText();
        int score=Integer.parseInt(txtscore.getText());
        String resultat=txtresultat.getText();
        jeu j1=new jeu(id,nom,type,score,resultat);
        try {
            ser.update(j1);
            Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("jeu modifié avec succés");
            refreshTableView();
            alert1.showAndWait();
        }catch (SQLException e)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }




}


    @FXML
    void AjouterJeu(ActionEvent event) {
        int id=Integer.parseInt(txtid.getText());
        String nom=txtnom.getText();
        String type=txttype.getText();
        int score=Integer.parseInt(txtscore.getText());
        String resultat=txtresultat.getText();

        jeu j1=new jeu(id,nom,type,score,resultat);
        try {
            ser.ajouter(j1);
            Alert alert1=new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("jeu ajouté avec succés");
            refreshTableView();
            alert1.showAndWait();
        }catch (SQLException e)
        {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }



    private void refreshTableView() {
        try {
            tableview.getItems().clear();
            List<jeu> jeuList = ser.readAll();
            tableview.getItems().addAll(jeuList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void admin2(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


}











