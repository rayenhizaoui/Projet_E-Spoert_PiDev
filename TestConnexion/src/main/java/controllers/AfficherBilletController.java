package controllers;
import Entites.Billet;
import Entites.Spectateur;
import Service.ServiceBillet;
import Service.ServiceSpectateurCrud;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
//import javafx.event.ActionEvent;
//import java.awt.event.ActionEvent;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.util.*;

import javafx.event.ActionEvent;


import javafx.scene.image.ImageView;

//import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.ByteArrayOutputStream;
//import net.glxn.qrgen.javase.QRCode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.image.Image;
//import net.glxn.qrgen.javase.QRCode;
//import com.google.zxing.qrcode.QRCode;
//import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.QRCodeWriter;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;










import java.util.List;
import java.util.Map;
import java.util.HashMap;











public class AfficherBilletController {

    @FXML
    private TableView<Billet> viewtable;

    @FXML
    private TableColumn<Billet, Integer> colid;

    @FXML
    private TableColumn<Billet, Integer> colcin;

    @FXML
    private TableColumn<Billet, String > coltype;

    @FXML
    private TableColumn<Billet, String> colemail;

    @FXML
    private TableColumn<Billet,String> colplace;

    @FXML
    private ImageView qrCodeImageView;
    private final ServiceBillet ser=new ServiceBillet();
/*
    @FXML
    void SupprimerBillet(ActionEvent event) {// Récupérer l'utilisateur sélectionné dans le TableView
        Billet selectedBillet = viewtable.getSelectionModel().getSelectedItem();

        if (selectedBillet != null) {
            // Appeler la méthode supprimerUtilisateur de votre service avec l'utilisateur sélectionné
            ser.supprimerBillet(selectedBillet);
            // Suppression réussie, rafraîchir le TableView pour refléter les changements
            refreshTableView();
            // Affichez un message de confirmation à l'utilisateur si nécessaire
            System.out.println("Billet supprimé avec succès.");
        } else {
            // Affichez un message à l'utilisateur indiquant qu'aucun utilisateur n'est sélectionné
            System.out.println("Veuillez sélectionner une billet à supprimer.");
        }

    }

*/










    @FXML
    void initialize() {






        try {
            List<Billet> list = ser.readAll();
            ObservableList<Billet> obers = FXCollections.observableList(list);

            // Rendre le TableView éditable
            viewtable.setEditable(true);

            // Rendre les colonnes éditables si nécessaire
            colcin.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
            coltype.setCellFactory(TextFieldTableCell.forTableColumn());
            colemail.setCellFactory(TextFieldTableCell.forTableColumn());
            colplace.setCellFactory(TextFieldTableCell.forTableColumn());



            // Associer les données à la TableView
            viewtable.setItems(obers);

            // Associer les propriétés de l'entité User aux colonnes de la TableView
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colcin.setCellValueFactory(new PropertyValueFactory<>("cin"));
            coltype.setCellValueFactory(new PropertyValueFactory<>("type"));
            colemail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colplace.setCellValueFactory(new PropertyValueFactory<>("place"));


 /*           // Générer et afficher le code QR
            QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
            Billet firstBillet = list.isEmpty() ? null : list.get(0); // Choisissez un billet pour générer le code QR (ici le premier billet)
            qrCodeGenerator.displayQRCode(firstBillet, qrCodeImageView);
*/
/*
            // Générer et afficher le code QR
            QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
            Billet firstBillet = list.isEmpty() ? null : list.get(0); // Choisissez un billet pour générer le code QR (ici le premier billet)
            qrCodeGenerator.displayQRCode(firstBillet, qrCodeImageView);
*/


            viewtable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    QRCodeGenerator qrCodeGenerator = new QRCodeGenerator();
                    qrCodeGenerator.displayQRCode(newSelection, qrCodeImageView); // Afficher le code QR du nouveau billet sélectionné
                }
            });





            // Écouter les modifications dans les cellules éditables et mettre à jour la base de données
            colcin.setOnEditCommit(event -> {
                Billet billet = event.getRowValue();
                billet.setCin(event.getNewValue());
                updateDatabase(billet);
            });
            coltype.setOnEditCommit(event -> {
                Billet billet = event.getRowValue();
                billet.setType(event.getNewValue());
                updateDatabase(billet);
            });
            colemail.setOnEditCommit(event -> {
                Billet billet = event.getRowValue();
                billet.setEmail(event.getNewValue());
                updateDatabase(billet);});

            colplace.setOnEditCommit(event -> {
                Billet billet = event.getRowValue();
                billet.setPlace(event.getNewValue());
                updateDatabase(billet);});


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateDatabase(Billet billet) {
        ser.modifierBillet(billet); // Utilisez votre service pour mettre à jour les données dans la base de données
        // Affichez un message de confirmation à l'utilisateur si nécessaire
    }











    // Méthode pour rafraîchir le TableView après la suppression
    private void refreshTableView() {
        try {
            // Effacer la liste actuelle des utilisateurs dans le TableView
            viewtable.getItems().clear();
            // Recharger la liste des utilisateurs à partir de la base de données
            List<Billet> billetList = ser.readAll();
            // Mettre à jour le TableView avec la nouvelle liste des utilisateurs
            viewtable.getItems().addAll(billetList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

@FXML
    public void SupprimerBillet(javafx.event.ActionEvent actionEvent) {
        // Récupérer l'utilisateur sélectionné dans le TableView
        Billet selectedBillet = viewtable.getSelectionModel().getSelectedItem();

        if (selectedBillet != null) {
            // Appeler la méthode supprimerUtilisateur de votre service avec l'utilisateur sélectionné
            ser.supprimerBillet(selectedBillet);
            // Suppression réussie, rafraîchir le TableView pour refléter les changements
            refreshTableView();
            // Affichez un message de confirmation à l'utilisateur si nécessaire
            System.out.println("Billet supprimé avec succès.");
        } else {
            // Affichez un message à l'utilisateur indiquant qu'aucun utilisateur n'est sélectionné
            System.out.println("Veuillez sélectionner une billet à supprimer.");
        }

    }





    // Classe QRCodeGenerator
    public class QRCodeGenerator {
        public Image generateQRCode(Billet billet) {
            String billetData = billet.getId() + "," + billet.getCin() + "," + billet.getType() + "," + billet.getEmail() + "," + billet.getPlace();
            Map<EncodeHintType, Object> hints = new HashMap<>();
            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            try {
                BitMatrix bitMatrix = new QRCodeWriter().encode(billetData, BarcodeFormat.QR_CODE, 250, 250, hints);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                MatrixToImageWriter.writeToStream(bitMatrix, "PNG", stream);
                byte[] byteArray = stream.toByteArray();
                ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArray);
                return new Image(inputStream);
            } catch (WriterException | IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void displayQRCode(Billet billet, ImageView imageView) {
            Image qrImage = generateQRCode(billet);
            imageView.setImage(qrImage);
        }
    }


    @FXML
    void Admin1(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }




}