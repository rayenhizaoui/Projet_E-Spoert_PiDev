package controllers;

import Service.ServiceR;
import Utils.DataSource;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Entites.Recompense;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListRecompenseController implements Initializable {

    @FXML
    private TableColumn<Recompense, Date> dateRecompense_col;

    @FXML
    private TableColumn<Recompense, String> equipeGagnante_col;

    @FXML
    private TableView<Recompense> events_table;

    @FXML
    private TableColumn<?, ?> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Recompense, Integer> montantRecompense_col;


    @FXML
    private Button print_btn;

    @FXML
    private Button showEvents;

    @FXML
    private Button Add_btn;

    public ListRecompenseController() throws SQLException {
    }



    Connection mc;
    PreparedStatement ste;

    private final ServiceR ser = new ServiceR();
    public ObservableList<Recompense> getEvents() throws SQLException {

        ObservableList<Recompense> recompenses_list = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * from recompense";
        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()) {
                recompenses_list.add(new Recompense(rs.getInt("id"),  rs.getString("equipeGagnante"),  rs.getInt("montantRecompense"), rs.getDate("dateRecompense")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recompenses_list;
    }





    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Recompense> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        equipeGagnante_col.setCellValueFactory(new PropertyValueFactory<>("equipeGagnante")); // Vérifiez le nom de la propriété
        montantRecompense_col.setCellValueFactory(new PropertyValueFactory<>("montantRecompense"));
        dateRecompense_col.setCellValueFactory(new PropertyValueFactory<>("dateRecompense"));
        events_table.setItems(listM);
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // Définir le gestionnaire d'événements pour la table
        events_table.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                Recompense selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });

        // Définir le gestionnaire d'événements pour le bouton "Add"
        Add_btn.setOnAction(event -> getAddView(event));
    }


    public Recompense onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Recompense selectedEvent = (Recompense) events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Recompense();
    }
    Recompense event = null;
    public void setEventStorage(Recompense e){
        event= e;

    }
    public Recompense getEventStorage(){
        return event;
    }




    @FXML
    private void getUpdateView(MouseEvent event) {
        try {

            System.out.println(event.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateRecompense.fxml"));
            Parent root = loader.load();
            UpdateRecompenseContoller controller = loader.getController();
            controller.setEvent(getEventStorage());
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    @FXML
    private void getAddView(ActionEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterRecompense.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public void searchbynom(ActionEvent actionEvent) {
        String keyword = keywordTextFld.getText(); // Récupérer le texte de recherche
        ObservableList<Recompense> filteredlist = getFilteredRecompense(keyword);
        events_table.setItems(filteredlist); // Mettre à jour le contenu de la table avec la liste filtrée
    }

    private ObservableList<Recompense> getFilteredRecompense(String keyword) {
        ObservableList<Recompense> filteredList = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * FROM Recompense WHERE equipeGagnante LIKE ?";
        try {
            ste = mc.prepareStatement(sql);
            ste.setString(1, "%" + keyword.toLowerCase() + "%"); // Assurez-vous que la requête est insensible à la casse
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Recompense recompense = new Recompense(rs.getInt("id"), rs.getString("equipeGagnante"), rs.getInt("montantRecompense"), rs.getDate("dateRecompense"));
                filteredList.add(recompense);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredList;
    }


    public void getpdf(ActionEvent actionEvent) {

        String filename = "recompense_list.pdf";

        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Liste des recompenses"));

            ObservableList<Recompense> allrecompense = events_table.getItems();

            // Créer un tableau avec 3 colonnes
            float[] columnWidths = {1, 2, 3,4,5}; // largeur relative des colonnes
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("ID")));
            table.addCell(new Cell().add(new Paragraph("equipeGagnante")));
            table.addCell(new Cell().add(new Paragraph("montantRecompense")));
            table.addCell(new Cell().add(new Paragraph("dateRecompense")));


            // Ajouter les données des équipements dans le tableau
            for (Recompense recompense : allrecompense) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(recompense.getId()))));
                table.addCell(new Cell().add(new Paragraph(recompense.getEquipeGagnante())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(recompense.getMontantRecompense()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(recompense.getDateRecompense()))));

            }

            // Ajouter le tableau au document
            document.add(table);

            // Fermer le document
            document.close();

            System.out.println("PDF créé avec succès.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pdf(MouseEvent mouseEvent) {
    }


    @FXML
    void retour(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }

}

