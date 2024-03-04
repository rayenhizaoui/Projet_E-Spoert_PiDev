package controller;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;


import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Equipement;
import model.Reservation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.util.Callback;
import javafx.scene.control.cell.TextFieldTableCell;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.FileNotFoundException;
public class ListreservationController implements Initializable {



    @FXML
    private TableColumn<Reservation, String> nom_col;
    @FXML
    private TableColumn<Reservation, String> type_col;

    @FXML
    private TableColumn<Reservation, Date> datedebutres_col;

    @FXML
    private TableColumn<Reservation, Date> datefinres_col;

    @FXML
    private TableColumn<Reservation, Float> deposit_col;

    @FXML
    private TableView<Reservation> events_table;

    @FXML
    private TableColumn<Reservation, Integer> id_col;

    @FXML
    private TextField keywordTextFld;
    @FXML
    public TableColumn equipement_col;


    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;


    @FXML
    void pdf(MouseEvent event) {
          /*  String filename = "reservation_list.pdf";

            try {
                PdfWriter writer = new PdfWriter(filename);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Liste des réservations"));

                ObservableList<Reservation> allReservations = events_table.getItems();
                for (Reservation reservation : allReservations) {
                    document.add(new Paragraph("ID: " + reservation.getId()));
                    document.add(new Paragraph("Nom: " + reservation.getNom()));
                    document.add(new Paragraph("Date début: " + reservation.getDatedebutres()));
                    document.add(new Paragraph("Date fin: " + reservation.getDatefinres()));
                    document.add(new Paragraph("Type: " + reservation.getType()));
                    document.add(new Paragraph("Dépôt: " + reservation.getDeposit()));
                    document.add(new Paragraph("----------------------------------"));
                }

                // Fermer le document
                document.close();

                System.out.println("PDF créé avec succès.");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

            String filename = "reservation_list.pdf";

            try {
                PdfWriter writer = new PdfWriter(filename);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Liste des réservations"));

                ObservableList<Reservation> allReservations = events_table.getItems();

                // Créer un tableau avec 6 colonnes
                float[] columnWidths = {1, 3, 2, 2, 2, 2}; // largeur relative des colonnes
                Table table = new Table(columnWidths);
                table.addCell(new Cell().add(new Paragraph("ID")));
                table.addCell(new Cell().add(new Paragraph("Nom")));
                table.addCell(new Cell().add(new Paragraph("Date début")));
                table.addCell(new Cell().add(new Paragraph("Date fin")));
                table.addCell(new Cell().add(new Paragraph("Type")));
                table.addCell(new Cell().add(new Paragraph("Dépositt")));

                // Ajouter les données de réservation dans le tableau
                for (Reservation reservation : allReservations) {
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getId()))));
                    table.addCell(new Cell().add(new Paragraph(reservation.getNom())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getDatedebutres()))));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getDatefinres()))));
                    table.addCell(new Cell().add(new Paragraph(reservation.getType())));
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(reservation.getDeposit()))));
                }

                // Ajouter le tableau au document
                document.add(table);

                // Fermer le document
                document.close();

                System.out.println("PDF créé avec succès.");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }





   /* @FXML
    void showEvents(MouseEvent event) {

    }*/
   @FXML
   void refresh(MouseEvent event) {
       try {
           showEvents(); // Récupère les nouvelles données
           events_table.refresh(); // Rafraîchit l'affichage de la TableView
       } catch (SQLException e) {
           e.printStackTrace();
       }
   }

    Connection mc;
    PreparedStatement ste;
// liste pour récupérer les donéées de la base
public ObservableList<Reservation> getEvents() throws SQLException {
    ObservableList<Reservation> Reservation_list = FXCollections.observableArrayList();
    mc = Connexion.getInstance().getCon();
    String sql = "SELECT r.*, e.* FROM reservation r LEFT JOIN equipement e ON r.id_equipement = e.id"; // Adjusted SQL query
    try {
        ste = mc.prepareStatement(sql);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            Equipement equipement = new Equipement(rs.getInt("e.id"), rs.getString("e.nom"),rs.getInt("nombre"),rs.getString("image") , rs.getDouble("rating"),rs.getString("QrCode")); // Create Equipement object with required fields
            Reservation_list.add(new Reservation(rs.getInt("r.id"), rs.getString("r.nom"), rs.getDate("r.datedebutres"), rs.getDate("r.datefinres"), rs.getString("r.type"), rs.getFloat("r.deposit"), equipement));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return Reservation_list;
}

    //afficher les réservations dans une table
    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Reservation> listM = getEvents();
        // title_col.setCellValueFactory(cellData -> cellData.getValue());
        id_col.setCellValueFactory(new PropertyValueFactory<Reservation, Integer>("id"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Reservation, String>("nom"));
        datedebutres_col.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("datedebutres"));
        datefinres_col.setCellValueFactory(new PropertyValueFactory<Reservation, Date>("datefinres"));
        type_col.setCellValueFactory(new PropertyValueFactory<Reservation, String>("type"));
        deposit_col.setCellValueFactory(new PropertyValueFactory<Reservation, Float>("deposit"));
        equipement_col.setCellValueFactory(new PropertyValueFactory<Reservation, String>("equipement"));

        events_table.setItems(listM);

    }
// l'initialisation de votre interface utilisateur lorsque le contrôleur est chargé
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        events_table.setOnMouseClicked((MouseEvent event) -> {//double clique affichage
            if (event.getClickCount() > 0) {
                Reservation selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });


    }
//obtenir l'élément sélectionné dans la tabl
    public Reservation onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Reservation selectedEvent = (Reservation) events_table.getSelectionModel().getSelectedItem();
            return selectedEvent;

        }

        return new Reservation();


    }

    Reservation event = null;

    public void setEventStorage(Reservation e) {
        event = e;

    }

    public Reservation getEventStorage() {
        return event;
    }

    @FXML
    void getUpdateView(MouseEvent e) {
        try {

            System.out.println(e.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/updatereservation.fxml"));
            Parent root = loader.load();
            UpdatereservationController controller = loader.getController();
            controller.setEvent(getEventStorage());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
            System.out.println("erreur");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }


    @FXML
    void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterreservation.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
    @FXML
    void searchReservation() {
        String keyword = keywordTextFld.getText();
        try {
            ObservableList<Reservation> searchResults = searchReservationByName(keyword);
            events_table.setItems(searchResults);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<Reservation> searchReservationByName(String keyword) throws SQLException {
        ObservableList<Reservation> searchResults = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCon();
        String sql = "SELECT r.*, e.* FROM reservation r LEFT JOIN equipement e ON r.id_equipement = e.id WHERE r.nom LIKE ?";
        try {
            ste = mc.prepareStatement(sql);
            ste.setString(1, "%" + keyword + "%");
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Equipement equipement = new Equipement(rs.getInt("e.id"), rs.getString("e.nom"),rs.getInt("nombre"),rs.getString("image"),rs.getDouble("rating"),rs.getString("QrCode")); // Create Equipement object with required fields
                searchResults.add(new Reservation(rs.getInt("r.id"), rs.getString("r.nom"), rs.getDate("r.datedebutres"), rs.getDate("r.datefinres"), rs.getString("r.type"), rs.getFloat("r.deposit"), equipement));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return searchResults;
    }



}





