package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Tournoi;

import java.net.URL;
import java.util.ResourceBundle;


public class ItemController implements Initializable {
    @FXML
    private Label CategoryLab;

    @FXML
    private HBox CategoryLabel;

    @FXML
    private Label DateLab;

    @FXML
    private Label LocLab;

    @FXML
    private ImageView img;

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameLabel1;

    @FXML
    private Label nameLabel2;

    @FXML
    private Label nameLabel3;
    @FXML
    private AnchorPane ItemPane;

    @FXML
    private Label priceLable;
    @FXML
    private VBox Vbox;
    @FXML
    private Label Duration_lab;
    @FXML
    private Label description_tx;

    private MyListener myListener;


    public ItemController() {

    }


      @FXML
       public void click(MouseEvent mouseEvent) {
       this.myListener.onClickListener(event);
       System.out.println(event);
    }
public Tournoi getData(){

        priceLable.getText();

        Tournoi event_one = new Tournoi(nameLabel.getText(), Integer.parseInt(priceLable.getText()));
       return event_one;

}





    private Tournoi event;

    public void setData(Tournoi event, MyListener myListener) {
        this.event = event;
        this.myListener = myListener;
        nameLabel.setText(event.getNom());
        priceLable.setText(String.valueOf(event.getFraisParticipation()));
        CategoryLab.setText(event.getTypeJeu());
        LocLab.setText(String.valueOf(event.getLocation()));
        //DateLab.setText(event.getevent_date());
        description_tx.setText(String.valueOf(event.getNombreParticipants()));

        Duration_lab.setText(String.valueOf(event.getNomEquipe()));


        //Image image = new Image(getClass().getResourceAsStream(fruit.getImgSrc()));
        //img.setImage(image);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ItemPane.setOnMouseClicked((MouseEvent event) ->{

        });

    }
}
