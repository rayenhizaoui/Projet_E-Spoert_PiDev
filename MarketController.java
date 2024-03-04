package controller;


import Service.Service_t;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Tournoi;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MarketController implements Initializable {

    Connection mc;
    PreparedStatement ste;
    ResultSet resultSet = null;
    @FXML
    private Button btnBack;
    @FXML
    private Label EventNameLabel;

    @FXML
    private Label PriceLabel;

    @FXML
    private ComboBox<Integer> Ticket_fld;

    @FXML
    private Button book_btn;

    @FXML
    private VBox chosenEvent;

    @FXML
    private GridPane grid;

    @FXML
    private ComboBox<String> payment_fld;

    @FXML
    private ScrollPane scroll;
    private List<Tournoi> events = new ArrayList<>();
    private Image image;
    private MyListener myListener;
    public Tournoi EventChoiceStorage = null;

    public Tournoi getEventChoiceStorage() {
        return this.EventChoiceStorage;
    }

    public void setEventChoiceStorage(Tournoi eventChoiceStorage) {
        this.EventChoiceStorage = eventChoiceStorage;
    }

    private List<Tournoi> getData() throws SQLException {
      Service_t cntrl = new Service_t();
        return cntrl.DisplayEvent();
    }

    public void setChosenFruit(Tournoi event) {
        setEventChoiceStorage(event);

        //  BookEventController bookEventController = new BookEventController();
        // bookEventController.setChosen(event);
        EventNameLabel.setText(event.getNom());
        PriceLabel.setText(String.valueOf(event.getFraisParticipation()));

        // image = new Image(getClass().getResourceAsStream(event.getEventTitle()));
        chosenEvent.setStyle("-fx-background-color: #5D8BF4 ;\n"
                + "    -fx-background-radius: 30;");
    }

    @FXML
    public void getBookView(MouseEvent event) {
        if (payment_fld.getValue() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("ERROR");
            alert.setContentText("PLEASE SELECT YOUR PAYMENT METHOD");
            alert.showAndWait();
        } else {
            try {

                // System.out.println( getEventChoiceStorage());
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/EventBooking.fxml"));
                Parent root = loader.load();

                BookEventController controller = loader.getController();

                // controller.setChosen(v);
                controller.getEventBook(getEventChoiceStorage().getNom());

                //controller.setS(EventNameLabel.getText());
                //Event event_o = controller.getChosen();
                // System.out.println(event_o);
                // controller.cin_tx.setText("aaaaa");
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            events.addAll(getData());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (events.size() > 0) {
            setChosenFruit(events.get(events.size() - 1));

        }

        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < events.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();

                itemController.setData(events.get(i), myListener);
                anchorPane.setOnMouseClicked((MouseEvent event) -> {
                    setChosenFruit(itemController.getData());

                });

                if (column == 3     ) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_COMPUTED_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_COMPUTED_SIZE);

                GridPane.setMargin(anchorPane, new Insets(4));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        List list = new ArrayList();
        list.add("Card");
        ObservableList list1 = FXCollections.observableArrayList();
        list1.setAll(list);
        payment_fld.setItems(list1);

        btnBack.setOnAction(event -> {
            try {
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/EventHome.fxml")));//khedmet anas
                Scene scene = new Scene(parent);
                Stage stage;
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
             //   System.out.println(name2);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });

    }

}
