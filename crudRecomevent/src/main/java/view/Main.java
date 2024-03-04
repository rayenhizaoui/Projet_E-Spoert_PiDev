package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/frontRC.fxml"));
        Parent root = loader.load();

        // Créer une nouvelle scène avec la vue chargée
        Scene scene = new Scene(root);


        // Définir la scène pour la fenêtre de l'application
        stage.setScene(scene);

        // Afficher la fenêtre
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


      //  Parent root = FXMLLoader.load(getClass().getResource("/view/ajouterRecompense.fxml"));

        //Scene scene = new Scene(root);
        //stage.setScene(scene);
       // stage.show();
        //new animatefx.animation.ZoomIn(root).play();



