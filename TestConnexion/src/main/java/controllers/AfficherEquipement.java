package controllers;
import Service.ServiceEq;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Entites.Equipement;
import org.controlsfx.control.Rating;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
//import Entites.

public class AfficherEquipement {

    @FXML
    private Label emptyLabel;

    @FXML
    private HBox equipementTitlePane;

    private final ServiceEq sv = new ServiceEq();

    public AfficherEquipement() throws SQLException {
    }


    @FXML
    public void initialize() throws SQLException {
        equipementTitlePane.getChildren().clear();

        Set<Equipement> equipements = sv.readAll().stream().collect(Collectors.toSet());

        if (equipements.isEmpty()) {
            emptyLabel.setVisible(true);
            equipementTitlePane.setVisible(false);
        } else {
            emptyLabel.setVisible(false);
            equipementTitlePane.setVisible(true);

            TilePane equipementsTilePane = new TilePane();
            equipementsTilePane.setPrefColumns(3); // Set the preferred number of columns to 3
            equipementsTilePane.setHgap(10); // Horizontal gap between elements
            equipementsTilePane.setVgap(10); // Vertical gap between elements
            equipementsTilePane.setAlignment(Pos.TOP_CENTER); // Align to the top center

            for (Equipement equipement : equipements) {
                Pane pane = createEquipementPane(equipement);
                equipementsTilePane.getChildren().add(pane);
            }

            equipementTitlePane.getChildren().add(equipementsTilePane);
        }
    }



    private Pane createEquipementPane(Equipement equipement) {
        System.out.println("Creating pane for equipment: " + equipement.getNom());

        // Use VBox for vertical layout
        VBox vbox = new VBox(5); // Use VBox for vertical layout with spacing of 10
        vbox.setAlignment(Pos.CENTER); // Center the content
        vbox.setPadding(new Insets(10)); // Add padding around the VBox
        vbox.setPrefSize(200, 300); // Set a preferred size for VBox
        vbox.getStyleClass().add("car-pane"); // Add the CSS class for styling

        // Create an ImageView for the car image
        ImageView imageView = new ImageView();
        imageView.setFitWidth(180);
        imageView.setFitHeight(120);
        imageView.setPreserveRatio(true);
        imageView.getStyleClass().add("car-image");
        try {
            File file = new File("src/main/resources/images/" + equipement.getImage());
            String imageUrl = file.toURI().toURL().toExternalForm();
            Image image = new Image(imageUrl);
            imageView.setImage(image);
        } catch (Exception e) {
            e.printStackTrace();
            // Consider having a default image if the specific image is not found.
        }

        vbox.getChildren().add(imageView); // Add ImageView to VBox
        System.out.println("Number of children in VBox for " + equipement.getNom() + ": " + vbox.getChildren().size());

        Label NomLabel = new Label("Nom: " + equipement.getNom());
        Label NombreLabel = new Label("Nombre: " + equipement.getNombre());

        Rating rating = new Rating(5);
        rating.setPartialRating(true);
        rating.getStyleClass().add("custom-rating");

        double equipementRating = equipement.getRating();


        if (equipementRating > 0) {
            rating.setRating(equipementRating);
        } else {
            rating.setRating(0);
        }
        System.out.println("Setting rating for " + equipement.getNom() + " to " + rating.getRating());

        rating.ratingProperty().addListener((observable, oldValue, newValue) -> {
            equipement.setRating(newValue.doubleValue());
            try {
                sv.update(equipement , equipement.getId());
                System.out.println("Rating updated for actualité " + equipement.getNom());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Adding children to VBox for " + equipement.getNom());

        vbox.getChildren().addAll(NomLabel, NombreLabel);

        vbox.getChildren().add(rating) ;

        // Enclose the VBox in a Pane for border/styling if needed
        StackPane stackPane = new StackPane(vbox);
        stackPane.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        return stackPane;

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
    void getAddreserView(MouseEvent event) {
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

}