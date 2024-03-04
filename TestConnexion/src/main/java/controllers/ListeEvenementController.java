package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Entites.Evenement;
import Entites.Historique;
import Entites.Recompense;
import Service.ServiceEv;

import Utils.DataSource;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;

public class ListeEvenementController implements Initializable {
    @FXML
    public Button openCalendarBtn;
    @FXML
    private Button Add_btn;
    @FXML
    private TableColumn<Evenement, String> dateEvent_col;
    @FXML
    private TableColumn<Evenement, Integer> duree_col;
    @FXML
    private TableView<Evenement> events_table;
    @FXML
    private TableColumn<Evenement, Integer> id_col;
    @FXML
    private TextField keywordTextFld;
    @FXML
    private TableColumn<Evenement, String> lieu_col;
    @FXML
    private TableColumn<Evenement, String> nomEvent_col;
    @FXML
    private TableColumn<Evenement, String> qrCodeColumn; // Use String type for QR code column
    @FXML
    private Button print_btn;
    @FXML
    private Button showEvents;

    private final ServiceEv ser = new ServiceEv();
    private Connection mc;
    private PreparedStatement ste;

    public ListeEvenementController() throws SQLException {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            showEvents();
            setupQrCodeColumn(); // Call this method to set up the QR code column
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        events_table.setOnMouseClicked(event -> {
            if (event.getClickCount() > 0) {
                Evenement selectedEvent = onEdit();
                setEventStorage(selectedEvent);
                getUpdateView(event);
            }
        });


        Add_btn.setOnAction(event -> {
            getAddView(event);

        });

    }

    private void setupQrCodeColumn() {
        qrCodeColumn.setCellFactory(column -> new TableCell<Evenement, String>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {

                    byte[] decodedBytes = Base64.getDecoder().decode(item);
                    Image image = new Image(new ByteArrayInputStream(decodedBytes));

                    imageView.setImage(image);
                    imageView.setFitHeight(50); // Adjust size as needed
                    imageView.setFitWidth(50);
                    setGraphic(imageView);
                }
            }
        });
    }

    public ObservableList<Evenement> getEvents() throws SQLException {
        ObservableList<Evenement> evenementList = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT e.*, r.* FROM Evenement e LEFT JOIN recompense r ON e.id_recompense = r.id";

        try {
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Recompense recompense = new Recompense(rs.getInt("id"), rs.getString("equipeGagnante"), rs.getInt("montantRecompense"), rs.getDate("dateRecompense"));
                evenementList.add(new Evenement(rs.getInt("id"), rs.getString("nomEvent"), rs.getString("lieu"), rs.getDate("dateEvent"), rs.getInt("duree"), rs.getString("QrCode")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return evenementList;
    }

    @FXML
    public void showEvents() throws SQLException {
        ObservableList<Evenement> listM = getEvents();
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomEvent_col.setCellValueFactory(new PropertyValueFactory<>("nomEvent"));
        lieu_col.setCellValueFactory(new PropertyValueFactory<>("lieu"));
        dateEvent_col.setCellValueFactory(new PropertyValueFactory<>("dateEvent"));
        duree_col.setCellValueFactory(new PropertyValueFactory<>("duree"));
        qrCodeColumn.setCellValueFactory(new PropertyValueFactory<>("qrCode")); // Make sure QR code column is set up correctly
        events_table.setItems(listM);
    }


    @FXML
    private void getUpdateView(MouseEvent event) {
        Evenement selectedEvent = onEdit();
        System.out.println("sdqdqsdqsdqsdqsd" + selectedEvent);
        if (selectedEvent != null) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvenement.fxml"));
                Parent root = loader.load();
                UpdateEvenementController controller = loader.getController();
                controller.setEvent(selectedEvent);
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }


    private Evenement getEventStorage() {
        return event;

    }

    Evenement event = null;

    private void setEventStorage(Evenement e) {
        Evenement event = e;
    }


    private Evenement onEdit() {
        if (events_table.getSelectionModel().getSelectedItem() != null) {
            Evenement selectedEvent = events_table.getSelectionModel().getSelectedItem();
            try {
                ServiceEv serviceEv = new ServiceEv();
                // Fetch the complete Evenement details by ID, including Recompense
                Evenement fullEvenementDetails = serviceEv.findById(selectedEvent.getId());
                return fullEvenementDetails;
            } catch (SQLException e) {
                e.printStackTrace();
                // Handle the error appropriately (e.g., show an error dialog)
            }
        }
        return new Evenement(); // Return an empty Evenement if none is selected or in case of error
    }


    @FXML
    private void getAddView(ActionEvent event) {

        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/ajouterEvenement.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void openCalendar(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CalendarView.fxml"));
            Parent root = loader.load();

            CalendarViewController controller = loader.getController();
            List<Evenement> events = getEvents(); // Fetch events, perhaps from a database or service
            controller.setEvents(events);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    public void getHistorique(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/HistoriqueView.fxml"));
            Parent root = loader.load();

            HistoriqueViewController controller = loader.getController();
            List<Historique> historiqueList = fetchHistorique(); // Implement this method to get historique data from the database
            controller.setHistoriqueData(historiqueList);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // Make the dialog modal
            stage.setTitle("Historique");
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Historique> fetchHistorique() throws SQLException {
        return ser.fetchHistorique();
    }

    public void searchbyname(ActionEvent actionEvent) {
        String keyword = keywordTextFld.getText(); // Récupérer le texte de recherche
        try {
            ObservableList<Evenement> filteredlist = getFilteredEvenement(keyword);
            events_table.setItems(filteredlist); // Mettre à jour le contenu de la table avec la liste filtrée
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private ObservableList<Evenement> getFilteredEvenement(String keyword) throws SQLException {

        ObservableList<Evenement> filteredList = FXCollections.observableArrayList();
        mc = DataSource.getInstance().getCon();
        String sql = "SELECT * FROM Evenement WHERE nomEvent LIKE ?";
        try {
            ste = mc.prepareStatement(sql);
            ste.setString(1, "%" + keyword.toLowerCase() + "%"); // Assurez-vous que la requête est insensible à la casse
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                Evenement event = new Evenement(rs.getInt("id"), rs.getString("nomEvent"), rs.getString("lieu"), rs.getDate("dateEvent"), rs.getInt("duree"), rs.getString("QrCode"));
                filteredList.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredList;
    }

    public void getpdf(ActionEvent actionEvent) {
        String filename = "evenement_list.pdf";

        try {
            PdfWriter writer = new PdfWriter(filename);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.add(new Paragraph("Liste des evenmenets"));

            ObservableList<Evenement> allEvenements = events_table.getItems();

            // Créer un tableau avec 3 colonnes
            float[] columnWidths = {1, 2, 3,4,5}; // largeur relative des colonnes
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("ID")));
            table.addCell(new Cell().add(new Paragraph("Nom de l'evenement")));
            table.addCell(new Cell().add(new Paragraph("lieu")));
            table.addCell(new Cell().add(new Paragraph("dateEvent")));
            table.addCell(new Cell().add(new Paragraph("duree")));

            // Ajouter les données des équipements dans le tableau
            for (Evenement equipment : allEvenements) {
                table.addCell(new Cell().add(new Paragraph(String.valueOf(equipment.getId()))));
                table.addCell(new Cell().add(new Paragraph(equipment.getNomEvent())));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(equipment.getLieu()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(equipment.getDateEvent()))));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(equipment.getDuree()))));
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


    @FXML
    void retour(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


    }



