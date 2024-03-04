package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {



    @FXML
    void equipe(ActionEvent event) throws IOException{
        Parent view4=FXMLLoader.load(getClass().getResource("/AjouterEquipe.fxml"));
        Scene scene4=new Scene(view4);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Parent view4= FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
        Scene scene4=new Scene(view4);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    @FXML
    void tournoi(ActionEvent event) throws IOException {
        Parent view4= FXMLLoader.load(getClass().getResource("/UIEvent.fxml"));
        Scene scene4=new Scene(view4);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

}
