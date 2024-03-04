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


public class StatistiqueController {

    @FXML
    private PieChart pieChart;

    @FXML
    private Label labelGagnant;

    @FXML
    private Label labelPerdant;

    public void afficherStatistique(List<Integer> scores) {
        int gagnants = 0;
        int perdants = 0;

        // Compter le nombre de gagnants et de perdants en fonction des scores
        for (int score : scores) {
            if (score > 100) {
                gagnants++;
            } else {
                perdants++;
            }
        }

        double total = gagnants + perdants;
        double pourcentageGagnants = (gagnants / total) * 100;
        double pourcentagePerdants = (perdants / total) * 100;

        labelGagnant.setText("Joueur gagnant : " + gagnants + " (" + String.format("%.2f", pourcentageGagnants) + "%)");
        labelPerdant.setText("Joueur perdant : " + perdants + " (" + String.format("%.2f", pourcentagePerdants) + "%)");

        pieChart.getData().clear();
        pieChart.getData().add(new PieChart.Data("Joueur gagnant", gagnants));
        pieChart.getData().add(new PieChart.Data("Joueur perdant", perdants));
    }

    public void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherJeu.fxml"));
            Parent root = loader.load();

            // Récupérer la scène actuelle et la modifier pour afficher la vue afficherjeu.fxml
            Stage stage = (Stage) pieChart.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




