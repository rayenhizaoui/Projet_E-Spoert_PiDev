package controllers;

import Entites.User;
import Service.ServiceUserCrud;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
//import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;


import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;

//import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import javafx.scene.paint.Color;
import javafx.beans.binding.Bindings;


import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;


import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import javafx.scene.Group;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.paint.Color;


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

    @FXML
    private TextField txtrecherche;

    /*@FXML
    private Label spectatorPercentageLabel;
    */

    @FXML
    private PieChart roleStatisticsPieChart;

    @FXML
    private Label spectatorPercentageLabel;

    @FXML
    private Circle spectatorPercentageCircle;
/*
    @FXML
    private Group spectatorPercentageCircle;
*/


    private final ServiceUserCrud ser = new ServiceUserCrud();

/*
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

        updateRoleStatisticsPieChart();
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
*/

    @FXML
    void AjouterUser(ActionEvent event) {
        int cin = Integer.parseInt(txtcin.getText());
        String username = txtusername.getText();
        int numero = Integer.parseInt(txtnumero.getText());
        String email = txtemail.getText();
        String adresse = txtadresse.getText();
        String password = txtpassword.getText();
        String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox

        // Rafraîchir la TableView avant d'ajouter un nouvel utilisateur
        refreshTableView();

        // Mettre à jour la PieChart après l'ajout d'un utilisateur
        updateRoleStatisticsPieChart();

        // Créer un nouvel utilisateur avec les données saisies
        User u4 = new User(cin, username, numero, email, adresse, password, roles);

        // Ajouter l'utilisateur à la base de données
        try {
            ser.ajouter(u4);

            // Afficher un message de confirmation
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("Utilisateur ajouté avec succès");
            alert1.showAndWait();

            // Rafraîchir la TableView après l'ajout de l'utilisateur
            refreshTableView();
        } catch (SQLException e) {
            // En cas d'erreur lors de l'ajout de l'utilisateur, afficher un message d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }


    @FXML
    void initialize() {

        updateRoleStatisticsPieChart();
        updateSpectatorPercentageLabel();


        //txtrecherche.textProperty().addListener((observable, oldValue, newValue) -> handleSearchTextChanged(newValue));
        txtrecherche.setOnKeyReleased(this::handleSearchTextChanged);

        // Créer une liste de rôles disponibles
        List<String> roles = Arrays.asList("SPECTATEUR", "ADMIN","JOUEUR","SPONSOR","ARBITRE");

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
        updateRoleStatisticsPieChart();
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
            updateRoleStatisticsPieChart();
            System.out.println("Utilisateur supprimé avec succès.");
        } else {
            System.out.println("Veuillez sélectionner un utilisateur à supprimer.");
        }
    }


    @FXML
    void handleSearchTextChanged(KeyEvent keyEvent) {
        String searchText = txtrecherche.getText().trim().toLowerCase(); // Obtenir le texte de recherche et le normaliser
        ObservableList<User> filteredList = FXCollections.observableArrayList(); // Créer une liste filtrée

        // Si le champ de recherche est vide, restaurer la TableView à son état d'origine
        if (searchText.isEmpty()) {
            refreshTableView();
            return;
        }

        // Parcourir la liste des utilisateurs et ajouter ceux correspondant au texte de recherche à la liste filtrée
        for (User user : tableview.getItems()) {
            if (user.getUsername().toLowerCase().contains(searchText) ||
                    String.valueOf(user.getCin()).contains(searchText) ||
                    String.valueOf(user.getNumero()).contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText) ||
                    user.getAdresse().toLowerCase().contains(searchText) ||
                    user.getPassword().toLowerCase().contains(searchText) ||
                    user.getRoles().toLowerCase().contains(searchText)) {
                filteredList.add(user);
            }
        }

        // Mettre à jour le TableView avec la liste filtrée
        tableview.setItems(filteredList);
    }


    @FXML
    void generatePDF(ActionEvent event) {
        // Appel de la nouvelle méthode generatePDFWithIText
        generatePDFWithIText(tableview);
    }

    // Méthode pour générer un PDF à partir du contenu de la TableView en utilisant iText
    public static void generatePDFWithIText(TableView<User> tableView) {
        // Créer un document PDF
        Document document = new Document();

        try {
            // Créer un sélecteur de fichiers pour choisir l'emplacement où enregistrer le PDF
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Enregistrer le PDF");
            // Définir une extension de fichier par défaut
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers PDF (*.pdf)", "*.pdf");
            fileChooser.getExtensionFilters().add(extFilter);
            File file = fileChooser.showSaveDialog(null);

            if (file != null) {
                // Spécifier le chemin du fichier PDF de sortie avec l'extension .pdf
                String outputPath = file.getAbsolutePath();
                if (!outputPath.toLowerCase().endsWith(".pdf")) {
                    outputPath += ".pdf";
                }
                PdfWriter.getInstance(document, new FileOutputStream(new File(outputPath)));

                // Ouvrir le document pour écrire
                document.open();

                // Créer un tableau iText avec le nombre de colonnes égal au nombre de colonnes de la TableView
                PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());
                pdfTable.setWidthPercentage(100);

                // Ajouter les en-têtes de colonnes au tableau iText
                for (TableColumn<User, ?> column : tableView.getColumns()) {
                    PdfPCell header = new PdfPCell(new Phrase(column.getText()));
                    pdfTable.addCell(header);
                }

                // Ajouter les valeurs de la TableView au tableau iText
                for (User user : tableView.getItems()) {
                    for (TableColumn<User, ?> column : tableView.getColumns()) {
                        Object cellValue = column.getCellData(user);
                        if (cellValue != null) {
                            pdfTable.addCell(cellValue.toString());
                        } else {
                            pdfTable.addCell("");
                        }
                    }
                }

                // Ajouter le tableau iText au document PDF
                document.add(pdfTable);

                // Fermer le document
                document.close();

                System.out.println("PDF généré avec succès à l'emplacement : " + outputPath);
            }
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }
    }


    private double calculateSpectatorPercentage() {
        try {
            List<User> allUsers = ser.readAll();
            long totalUsers = allUsers.size();

            long spectatorCount = allUsers.stream()
                    .filter(user -> user.getRoles().equals("SPECTATEUR"))
                    .count();

            // Calcul du pourcentage de spectateurs
            return (double) spectatorCount / totalUsers * 100.0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    public void updateSpectatorPercentageLabel() {
        double spectatorPercentage = calculateSpectatorPercentage();
        spectatorPercentageLabel.setText("Pourcentage de spectateurs : " + String.format("%.2f", spectatorPercentage) + "%");
    }




    private void updateRoleStatisticsPieChart() {
        // Obtenir le nombre de spectateurs et d'admins depuis votre service
        int numberOfSpectators = ser.countUsersByRole("SPECTATEUR");
        int numberOfAdmins = ser.countUsersByRole("ADMIN");
        int numberOfJoueurs = ser.countUsersByRole("JOUEUR");
        int numberOfSponsor = ser.countUsersByRole("SPONSOR");
        int numberOfArbitre = ser.countUsersByRole("ARBITRE");
        int totalUsers = numberOfSpectators + numberOfAdmins + numberOfJoueurs+numberOfSponsor+numberOfArbitre;

        // Calculer les pourcentages
        double spectatorPercentage = (totalUsers > 0) ? (double) numberOfSpectators / totalUsers * 100 : 0;
        double adminPercentage = (totalUsers > 0) ? (double) numberOfAdmins / totalUsers * 100 : 0;
        double joueurPercentage = (totalUsers > 0) ? (double) numberOfJoueurs / totalUsers * 100 : 0;
        double sponsorPercentage = (totalUsers > 0) ? (double) numberOfSponsor / totalUsers * 100 : 0;
        double arbitrePercentage = (totalUsers > 0) ? (double) numberOfArbitre / totalUsers * 100 : 0;

        // Créer les données pour le PieChart avec les pourcentages
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Spectateurs (" + String.format("%.2f", spectatorPercentage) + "%)", spectatorPercentage),
                new PieChart.Data("Admins (" + String.format("%.2f", adminPercentage) + "%)", adminPercentage),
                new PieChart.Data("Joueurs (" + String.format("%.2f", joueurPercentage) + "%)", joueurPercentage),
                new PieChart.Data("sponsors (" + String.format("%.2f", sponsorPercentage) + "%)", sponsorPercentage),
                new PieChart.Data("arbitre (" + String.format("%.2f", arbitrePercentage) + "%)", arbitrePercentage)
        );

        // Mettre à jour le PieChart avec les nouvelles données
        roleStatisticsPieChart.setData(pieChartData);

        // Facultatif : formater les libellés pour afficher les pourcentages
        roleStatisticsPieChart.setLabelLineLength(10);
        roleStatisticsPieChart.setLabelsVisible(true);
        roleStatisticsPieChart.getData().forEach(data ->
                data.nameProperty().bind(
                        Bindings.concat(
                                data.getName(), " ", String.format("%.2f", data.getPieValue()), "%"
                        )
                )
        );

    }


    @FXML
    void logout(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }



    @FXML
    void backbillet(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/AfficherBillet.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


    @FXML
    void backequipe(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/AfficherEquipe.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


    @FXML
    void backjeu(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/AfficherJeu.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


    @FXML
    void backequipement(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/equipementview.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

    @FXML
    void backreservation(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/reservationview.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }



    @FXML
    void backevent(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/TableViewEvent.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

    @FXML
    void backrecomp(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/TableView.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


    @FXML
    void backtournoi(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/eventhome.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

}
