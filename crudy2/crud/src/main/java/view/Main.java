package view;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {


    @Override
    public void start(Stage stage) throws Exception {
       FXMLLoader loader=new FXMLLoader(getClass().getResource("/reservationview.fxml"));
       Parent root=loader.load();
       Scene scene=new Scene(root,600,400);
       stage.setTitle("gerer sponsor");
       stage.setScene(scene);
       stage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
