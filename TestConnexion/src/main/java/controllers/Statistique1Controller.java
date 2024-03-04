package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Statistique1Controller {
    @FXML
    private PieChart pieChart1;

    @FXML
    private Label labelsuffisant;

    @FXML
    private Label labelinsuffisant;

    public void afficherStatistique1(List<Integer> nbrJoueurs) {
        int suffisant = 0;
        int insuffisant = 0;

        // Compter le nombre d'équipes avec un nombre de joueurs suffisant ou insuffisant
        for (int nbrJoueur : nbrJoueurs) {
            if (nbrJoueur > 50) {
                suffisant++;
            } else {
                insuffisant++;
            }
        }

        double total = suffisant + insuffisant;
        double pourcentageSuffisant = (suffisant / total) * 100;
        double pourcentageInsuffisant = (insuffisant / total) * 100;

        labelsuffisant.setText("Nombre d'équipes avec suffisamment de joueurs : " + suffisant + " (" + String.format("%.2f", pourcentageSuffisant) + "%)");
        labelinsuffisant.setText("Nombre d'équipes avec un nombre de joueurs insuffisant : " + insuffisant + " (" + String.format("%.2f", pourcentageInsuffisant) + "%)");

        pieChart1.getData().clear();
        pieChart1.getData().add(new PieChart.Data("Nombre d'équipes avec suffisamment de joueurs", suffisant));
        pieChart1.getData().add(new PieChart.Data("Nombre d'équipes avec un nombre de joueurs insuffisant", insuffisant));

        // Définir les couleurs des sections du PieChart
        pieChart1.getData().forEach(data -> {
            if (data.getName().contains("suffisamment")) {
                data.getNode().setStyle("-fx-pie-color: blue;");
            } else {
                data.getNode().setStyle("-fx-pie-color: red;");
            }
        });
    }

    public void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipe.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle et la modifier pour afficher la vue AfficherEquipe.fxml
            Stage stage = (Stage) pieChart1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
