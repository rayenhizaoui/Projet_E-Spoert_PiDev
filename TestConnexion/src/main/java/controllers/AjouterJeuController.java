package controllers;
import Entites.jeu;
import Service.ServiceJeu;
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
public class AjouterJeuController {
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
    private final ServiceJeu ser=new ServiceJeu();

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
    void afficherjeu(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherJeu.fxml"));
            Parent root = loader.load();
            AfficherJeuControllers dc = loader.getController();


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
    void supprimerjeu(ActionEvent event) {

        int id = Integer.parseInt(txtid.getText());

        // Appeler la méthode de suppression du service
        try {
            ser.delete(new jeu(id, "", "",0, ""));
            afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "Le jeu a été supprimé avec succès.");
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", "Une erreur s'est produite lors de la suppression du jeu : " + e.getMessage());
        }

    }


    @FXML
    void excel(MouseEvent mouseEvent) throws IOException {
        try {
            // Créer un nouveau classeur Excel
            XSSFWorkbook workbook = new XSSFWorkbook();

            Sheet sheet = workbook.createSheet("Jeux");

            // Créer une en-tête
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Type");
            headerRow.createCell(3).setCellValue("Score");
            headerRow.createCell(4).setCellValue("Résultat");

            // Récupérer les données des jeux depuis le service
            ObservableList<jeu> jeux = FXCollections.observableArrayList(ser.readAll()); // Conversion ici

            // Remplir les données dans les lignes suivantes
            int rowNum = 1;
            for (jeu jeu : jeux) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(jeu.getId());
                row.createCell(1).setCellValue(jeu.getNom());
                row.createCell(2).setCellValue(jeu.getType());
                row.createCell(3).setCellValue(jeu.getScore());
                row.createCell(4).setCellValue(jeu.getResultat());
            }

            // Enregistrer le classeur Excel
            FileOutputStream fileOut = new FileOutputStream("Jeux.xlsx");
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            // Afficher une boîte de dialogue de confirmations
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Export Excel");
            alert.setHeaderText(null);
            alert.setContentText("Les données ont été exportées vers Jeux.xlsx avec succès.");
            alert.showAndWait();

        } catch (SQLException | IOException e) {
            // Afficher une boîte de dialogue d'erreur en cas d'échec
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Export Excel - Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Une erreur s'est produite lors de l'exportation des données vers Excel : " + e.getMessage());
            alert.showAndWait();
        }
        Runtime.getRuntime().exec("cmd /c start Jeux.xlsx");

    }

    public void game(MouseEvent mouseEvent) {
        SpaceInvaders spaceInvaders = new SpaceInvaders();

        // Créer un nouveau stage pour le jeu
        Stage stage = new Stage();

        try {
            // Démarrer le jeu en appelant la méthode start avec le stage
            spaceInvaders.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer les éventuelles exceptions
        }
    }

    public void pong(MouseEvent mouseEvent) {
        Pong pongGame = new Pong(); // Créer une instance de la classe Pong
        Stage stage = new Stage(); // Créer un nouveau stage pour le jeu
        try {
            // Appeler la méthode start avec le stage
            pongGame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer les éventuelles exceptions
        }

    }

    public void snake(MouseEvent mouseEvent) {
        snake snakeGame = new snake(); // Créez une instance de la classe snake
        Stage stage = new Stage(); // Créez un nouveau stage pour le jeu
        try {
            // Appelez la méthode start avec le stage
            snakeGame.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
            // Gérez les éventuelles exceptions
        }

    }


    @FXML
    void retour(ActionEvent event) throws IOException{
        Parent view4=FXMLLoader.load(getClass().getResource("/AjouterEquipe.fxml"));
        Scene scene4=new Scene(view4);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    public void XO(MouseEvent mouseEvent) {
        TicTacToe ticTacToe = new TicTacToe();

    }

}
