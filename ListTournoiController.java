package controller;

import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Local;
import model.Tournoi;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.paint.Color.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import com.itextpdf.text.BaseColor;
import javafx.scene.paint.Color;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;

public class ListTournoiController implements Initializable {

    @FXML
    private TableColumn<Tournoi, Integer> duree_col;

    @FXML
    private TableView<Tournoi> events_table;

    @FXML
    private TableColumn<Tournoi, Integer> fraisParticipant_col;

    @FXML
    private TableColumn<Tournoi, Integer> id_col;

    @FXML
    private TextField keywordTextFld;

    @FXML
    private TableColumn<Tournoi, String> location_col;

    @FXML
    private TableColumn<Tournoi, Integer> nbParticipants_col;

    @FXML
    private TableColumn<Tournoi, String> nomEquipe_col;

    @FXML
    private TableColumn<Tournoi, String> nom_col;

    @FXML
    private Button print_btn;

    @FXML
    private TableColumn<Tournoi, String> typeJeu_col;




    @FXML
    void pdf(MouseEvent event) throws SQLException, IOException, DocumentException {
        String sql = "SELECT * from tournoi";
        ste = mc.prepareStatement(sql);
        ResultSet rs = ste.executeQuery();

        Document doc = new Document();
        PdfWriter.getInstance(doc, new FileOutputStream("./Esport4.pdf"));

        doc.open();

        doc.add(new Paragraph(" Esport APP  "));
        doc.add(new Paragraph("   "));
        doc.add(new Paragraph("  List of Tournois  "));
        doc.add(new Paragraph("   "));

        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(110);

        // Convertir la couleur JavaFX en couleur iTextPDF
        Color javafxColor = Color.PINK; // Remplacez cela par votre couleur JavaFX
        float r = (float) javafxColor.getRed();
        float g = (float) javafxColor.getGreen();
        float b = (float) javafxColor.getBlue();
        BaseColor itextColor = new BaseColor(r, g, b);
        PdfPCell cell;

        cell = new PdfPCell(new Phrase("nom", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        cell.setPadding(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("location", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        cell.setPadding(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("nomEquipe", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        cell.setPadding(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("duree", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        cell.setPadding(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Fraisinscription", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        cell.setPadding(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("nombreParticipants", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        cell.setPadding(3);
        table.addCell(cell);

        cell = new PdfPCell(new Phrase("Type_jeu", FontFactory.getFont("Comic Sans MS", 15)));
        cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        cell.setBackgroundColor(itextColor);
        cell.setBorderWidth(1);
        table.addCell(cell);

        while (rs.next()) {
            Tournoi t = new Tournoi();
            t.setId(rs.getInt("id"));
            t.setNom(rs.getString("nom"));
            t.setLocation(rs.getString("location"));
            t.setNomEquipe(rs.getString("nomEquipe"));
            t.setDuree(rs.getInt("duree"));
            t.setFraisParticipation(rs.getInt("fraisParticipation"));
            t.setNombreParticipants(rs.getInt("nombreParticipants"));
           t.setTypeJeu(rs.getString("typeJeu"));



            cell = new PdfPCell(new Phrase(t.getNom(), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(t.getLocation(), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(t.getNomEquipe(), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(t.getDuree()), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(t.getFraisParticipation()), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(String.valueOf(t.getNombreParticipants()), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase(t.getTypeJeu(), FontFactory.getFont("Comic Sans MS", 12)));
            cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
            cell.setBackgroundColor(itextColor);
            cell.setBorderWidth(1);
            cell.setPadding(3);
            table.addCell(cell);
        }

        doc.add(table);
        doc.close();
        Desktop.getDesktop().open(new File("./Esport4.pdf"));
    }

    Connection mc;
    PreparedStatement ste;
    public ObservableList<Tournoi> getEvents() throws SQLException {

        ObservableList<Tournoi> Tournoi_list = FXCollections.observableArrayList();
        mc = Connexion.getInstance().getCnx();
        String sql="SELECT * from tournoi";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()){
                Tournoi_list.add(new Tournoi(rs.getInt("id"),rs.getString("nom"), rs.getString("nomEquipe"), rs.getInt("nombreParticipants"), rs.getInt("duree"), rs.getString("typeJeu"), rs.getInt("fraisParticipation"),rs.getString("location")) );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Tournoi_list;
    }

    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Tournoi> listM = getEvents();
        // title_col.setCellValueFactory(cellData -> cellData.getValue());
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        duree_col.setCellValueFactory(new PropertyValueFactory<>("duree"));
        location_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("location"));
        fraisParticipant_col.setCellValueFactory(new PropertyValueFactory<Tournoi, Integer>("fraisParticipation"));
        nbParticipants_col.setCellValueFactory(new PropertyValueFactory<Tournoi, Integer>("nombreParticipants"));
        nomEquipe_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nomEquipe"));
        nom_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("nom"));
        typeJeu_col.setCellValueFactory(new PropertyValueFactory<Tournoi, String>("typeJeu"));

        events_table.setItems(listM);

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            showEvents();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        events_table.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() > 0) {
                        Tournoi selectedEvent = onEdit();
                        setEventStorage(selectedEvent);
                        getUpdateView(event);
                    }
                }


        );

        FilteredList<Tournoi> filteredData;
        try {
            filteredData = new FilteredList<>(getEvents());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        keywordTextFld.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(event -> {
                if (newValue.isEmpty() || newValue == null) {
                    return true;
                }
                String searchKeyword = newValue.toLowerCase();
                if (event.getNom().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;
                } else if (event.getNomEquipe().toLowerCase().indexOf(searchKeyword) > -1) {
                    return true;

                } else if (String.valueOf(event.getFraisParticipation()).indexOf(searchKeyword) > -1) {
                    return true;

                } else
                    return false;
            });
            SortedList<Tournoi> sortedData = new SortedList<>(filteredData);
            sortedData.comparatorProperty().bind(events_table.comparatorProperty());
            events_table.setItems(sortedData);
        });
    }
    public Tournoi onEdit() {
        // check the table's selected item and get selected item
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Tournoi selectedEvent = events_table.getSelectionModel().getSelectedItem();

            return selectedEvent;

        }

        return new Tournoi();
    }
    Tournoi event = null;
    public void setEventStorage(Tournoi e){
        event= e;

    }
    public Tournoi getEventStorage(){
        return event;
    }

    @FXML
    private void getUpdateView(MouseEvent e) {
        try {

            System.out.println(e.getSource().getClass());
            //Parent parent_two = FXMLLoader.load(getClass().getResource("/tableView/UpdateEventView.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEventView.fxml"));
            Parent root = loader.load();
            Update_tController controller = loader.getController();
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
    private void getAddView(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/Ajouter_t.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
