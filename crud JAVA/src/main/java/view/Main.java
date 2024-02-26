package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/tablelocal.fxml"));
        Parent root =loader.load();
        Scene scene = new Scene(root);
       stage.setScene(scene);
        stage.show();
    //new animatefx.animation.ZoomIn(root).play();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
