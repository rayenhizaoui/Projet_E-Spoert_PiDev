package controllers;

import Entites.equipe;
import Service.ServiceEquipe;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Optional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AjouterEquipeController {

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtidjeu;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnbrjoueur;

    @FXML
    private TextField txtlistejoueur;

    private final ServiceEquipe serv = new ServiceEquipe();


    @FXML
    void ajouterEquipe(ActionEvent event) {

        int id = Integer.parseInt(txtid.getText());
        int id_jeu = Integer.parseInt(txtidjeu.getText());
        String nom = txtnom.getText();
        int nbr_joueur = Integer.parseInt(txtnbrjoueur.getText());
        String liste_joueur = txtlistejoueur.getText();

        equipe e1 = new equipe(id, id_jeu, nom, nbr_joueur, liste_joueur);


        try {
            serv.ajouter(e1);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("equipe ajouté avec succés");
            alert1.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }

    @FXML
    void afficherEquipe(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipe.fxml"));
            Parent root = loader.load();
            AfficherEquipeControllers dc = loader.getController();


            txtnom.getScene().setRoot(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void supprimerEquipe(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtid.getText());

        // Appeler la méthode de suppression du service
        try {
            serv.delete(new equipe(id, 0, "", 0, ""));
            afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "L'équipe a été supprimée avec succès.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", "Une erreur s'est produite lors de la suppression de l'équipe : " + e.getMessage());
        }
    }

    @FXML
    void modifierEquipe(ActionEvent event) {
        int id = Integer.parseInt(txtid.getText());
        int cinJoueur = Integer.parseInt(txtidjeu.getText());
        String nom = txtnom.getText();
        int nbrJoueur = Integer.parseInt(txtnbrjoueur.getText());
        String listeJoueur = txtlistejoueur.getText();

        equipe equipe = new equipe(id, cinJoueur, nom, nbrJoueur, listeJoueur);
        try {
            serv.update(equipe);
            afficherAlerte(Alert.AlertType.CONFIRMATION, "Confirmation", "Équipe modifiée avec succès");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification de l'équipe : " + e.getMessage());
        }
    }


    @FXML
    void imageButtonClicked(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatbot.fxml"));
            Parent root = loader.load();
            Stage chatbotStage = new Stage();
            chatbotStage.initModality(Modality.APPLICATION_MODAL);
            chatbotStage.setTitle("Chatbot");
            chatbotStage.setScene(new Scene(root));
            chatbotStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void pdf(MouseEvent mouseEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherEquipe.fxml"));
            Parent root = loader.load();
            AfficherEquipeControllers controller = loader.getController();
            ObservableList<equipe> equipes = controller.tableView.getItems();

            // Création du fichier PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            double y = page.getMediaBox().getHeight() - 50;
            for (equipe equipe : equipes) {
                contentStream.beginText();
                contentStream.newLineAtOffset(50F, (float) y);
                contentStream.showText("ID: " + equipe.getId() +",ID de jeu: " + equipe.getIdJeu() + ", Nom: " + equipe.getNom() + ", Nombre de joueurs: " + equipe.getNbrJoueur()+",liste joueur: " + equipe.getListeJoueur());
                contentStream.endText();
                y -= 20;
            }
            contentStream.close();

            // Sauvegarde du fichier PDF
            File file = new File("equipes.pdf");
            document.save(file);
            document.close();

            // Affichage du fichier PDF
            Desktop.getDesktop().open(file);

            afficherAlerte(Alert.AlertType.INFORMATION, "PDF créé", "Le fichier PDF a été créé avec succès.");
        } catch (IOException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la création du PDF : " + e.getMessage());
        }
    }


    }











