
package controller;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import javafx.collections.ObservableList;
import javafx.scene.input.MouseEvent;


import Service.ServiceEq;
import Utils.Connexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Equipement;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.fx.FXGraphics2D;

import java.io.FileNotFoundException;

public class ListequipementController implements Initializable {


        @FXML
        private TableView<Equipement> events_table;

        @FXML
        private TextField keywordTextFld;
        @FXML
        private TableColumn<Equipement, String> image_col;
        @FXML
        private TableColumn<Equipement, String> id_col;

        @FXML
        private TableColumn<Equipement, String> nom_col;


        @FXML
        private TableColumn<Equipement, Integer> nombre_col;

    @FXML
    public TableColumn<Equipement, String> qrCodeColumn;
        @FXML
        private Button print_btn;
    @FXML
    private Canvas chartCanvas;
        @FXML
        private Button showEvents;

        Connection mc;
        PreparedStatement ste;
        private ServiceEq Serviceeq ;

        @FXML
        void pdf(MouseEvent event) {
        /*   String filename = "equipment_list.pdf";

            try {
                PdfWriter writer = new PdfWriter(filename);
                PdfDocument pdf = new PdfDocument(writer);
                Document document = new Document(pdf);

                document.add(new Paragraph("Liste des équipements"));

                ObservableList<Equipement> allEquipments = events_table.getItems();
                for (Equipement equipment : allEquipments) {
                    document.add(new Paragraph("ID: " + equipment.getId()));
                    document.add(new Paragraph("Nom: " + equipment.getNom()));
                    document.add(new Paragraph("Nombre: " + equipment.getNombre()));

                    document.add(new Paragraph("----------------------------------"));
                }

                // Fermer le document
                document.close();

                System.out.println("PDF créé avec succès.");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }*/

                String filename = "equipment_list.pdf";

                try {
                    PdfWriter writer = new PdfWriter(filename);
                    PdfDocument pdf = new PdfDocument(writer);
                    Document document = new Document(pdf);

                    document.add(new Paragraph("Liste des équipements"));

                    ObservableList<Equipement> allEquipments = events_table.getItems();

                    // Créer un tableau avec 3 colonnes
                    float[] columnWidths = {1, 3, 2}; // largeur relative des colonnes
                    Table table = new Table(columnWidths);
                    table.addCell(new Cell().add(new Paragraph("ID")));
                    table.addCell(new Cell().add(new Paragraph("Nom")));
                    table.addCell(new Cell().add(new Paragraph("Nombre")));

                    // Ajouter les données des équipements dans le tableau
                    for (Equipement equipment : allEquipments) {
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(equipment.getId()))));
                        table.addCell(new Cell().add(new Paragraph(equipment.getNom())));
                        table.addCell(new Cell().add(new Paragraph(String.valueOf(equipment.getNombre()))));
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





        @Override
        public void initialize(URL location, ResourceBundle resources) {
            try {
                Serviceeq = new ServiceEq();

                showEvents();

                setupQrCodeColumn() ;
            events_table.setOnMouseClicked((MouseEvent event) -> {
                if (event.getClickCount() > 0) {
                    Equipement selectedEvent = onEdit();
                    setEventStorage(selectedEvent);
                    getUpdateView(event);
                }
            });

                drawChart() ;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    private void setupQrCodeColumn() {
        qrCodeColumn.setCellFactory(column -> new TableCell<Equipement, String>() {
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
        @FXML
        void refresh(MouseEvent event) {
            try {
                showEvents(); // Récupère les nouvelles données
                events_table.refresh(); // Rafraîchit l'affichage de la TableView
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ObservableList<Equipement> getEvents() throws SQLException {
            ObservableList<Equipement> Equipement_list = FXCollections.observableArrayList();
            mc = Connexion.getInstance().getCon();
            String sql = "SELECT * FROM equipement";
            try {
                ste = mc.prepareStatement(sql);
                ResultSet rs = ste.executeQuery();
                while (rs.next())
                    Equipement_list.add(new Equipement(rs.getInt("id"), rs.getString("nom"), rs.getInt("nombre")  , rs.getString("image") , rs.getDouble("rating"),rs.getString("QrCode")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Equipement_list;
        }

        @FXML
        public void showEvents() throws SQLException {
            ObservableList<Equipement> listM = getEvents();
            id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
            nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
            nombre_col.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            // Set up the image column to display images
            image_col.setCellValueFactory(new PropertyValueFactory<>("image")); // Ensure this matches the property name in Equipement class
            image_col.setCellFactory(column -> new TableCell<Equipement, String>() {
                private final ImageView imageView = new ImageView();
                {
                    imageView.setFitHeight(50); // Set the preferred height for the images
                    imageView.setPreserveRatio(true);
                }

                @Override
                protected void updateItem(String image, boolean empty) {
                    super.updateItem(image, empty);
                    if (empty || image == null || image.isEmpty()) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        try {
                            // Load the image
                            File file = new File("src/main/resources/images/" + image);
                            String imageUrl = file.toURI().toURL().toExternalForm();
                            javafx.scene.image.Image images = new Image(imageUrl);
                            imageView.setImage(images);
                            setGraphic(imageView);
                        } catch (Exception e) {
                            e.printStackTrace();
                            setText("Image not found");
                        }
                    }
                }

            });
            qrCodeColumn.setCellValueFactory(new PropertyValueFactory<>("qrCode"));

            events_table.setItems(listM);
        }




        public Equipement onEdit() {
            // check the table's selected item and get selected item
            if (events_table.getSelectionModel().getSelectedItem() != null) {
                Equipement selectedEvent = events_table.getSelectionModel().getSelectedItem();
                return selectedEvent;
            }
            return new Equipement();
        }

        Equipement event;

        public void setEventStorage(Equipement e) {
            event = e;
        }

        public Equipement getEventStorage() {
            return event;
        }

        @FXML
        void getAddView(MouseEvent event) {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/ajouterequipement.fxml"));
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
        void getUpdateView(MouseEvent event) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/updateequipement.fxml"));
                Parent root = loader.load();
                updateequipementController controller = loader.getController();
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
        void searchEquipement() {
            String keyword = keywordTextFld.getText();
            try {
                ObservableList<Equipement> filteredList = getFilteredEquipements(keyword);
                events_table.setItems(filteredList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public ObservableList<Equipement> getFilteredEquipements(String keyword) throws SQLException {
            ObservableList<Equipement> filteredList = FXCollections.observableArrayList();
            mc = Connexion.getInstance().getCon();
            String sql = "SELECT * FROM equipement WHERE nom LIKE ?";
            try {
                ste = mc.prepareStatement(sql);
                ste.setString(1, "%" + keyword + "%");
                ResultSet rs = ste.executeQuery();
                while (rs.next())
                    filteredList.add(new Equipement(rs.getInt("id"), rs.getString("nom"), rs.getInt("nombre") , rs.getString("image"),rs.getDouble("rating"),rs.getString("QrCode")));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return filteredList;
        }
    private void drawChart() throws SQLException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<Equipement> list = Serviceeq.readAll(); // Get the list
        Set<Equipement> arrivages = new HashSet<>(list); // Convert list to set to eliminate duplicates
        for (Equipement arrivage : arrivages) {
            dataset.addValue(arrivage.getNombre(), "Nombre", arrivage.getNom());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Statistique des  Equipements" +
                        "" +
                        "", // Chart title
                "Equipement", // Domain axis label
                "Nombre", // Range axis label
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        GraphicsContext gc = chartCanvas.getGraphicsContext2D();
        FXGraphics2D g2d = new FXGraphics2D(gc);
        g2d.clearRect(0, 0, (int) chartCanvas.getWidth(), (int) chartCanvas.getHeight());
        barChart.draw(g2d, new java.awt.Rectangle((int) chartCanvas.getWidth(), (int) chartCanvas.getHeight()));
    }


    }



