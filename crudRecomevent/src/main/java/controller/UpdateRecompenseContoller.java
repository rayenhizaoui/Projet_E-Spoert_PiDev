package controller;
import Service.Service;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Recompense;
import java.time.LocalDate;
import java.sql.Date;
import java.sql.SQLException;


public class UpdateRecompenseContoller {






        @FXML
        private Button Delete_button;

        @FXML
        private DatePicker txtdateRecompense;

        @FXML
        private TextField txtequipeGangnte;

        @FXML
        private TextField txtmonatantRecompense;

        @FXML
        private Button update_button;

        @FXML
       void deleteR(ActionEvent event) throws SQLException {

            Service cntrl = new Service() ;
            cntrl.delete(getid());
            ((Stage)txtequipeGangnte.getScene().getWindow()).close();

            }


            @FXML

            void updateR(ActionEvent event) throws SQLException {
                String equipeGagnante = txtequipeGangnte.getText();
                int montantRecompense = Integer.parseInt(txtmonatantRecompense.getText());
                LocalDate selectedDate = txtdateRecompense.getValue();

                // Vérification que la date est sélectionnée
                if (selectedDate != null) {
                    Date dateRecompense = Date.valueOf(selectedDate);

                    Service ser = new Service();
                    Recompense recompense = new Recompense(equipeGagnante, montantRecompense, dateRecompense);
                    ser.update(recompense, getid()); // Passer l'id en paramètre
                }
            }

    private int id;
    private int getid() {
            return id;
    }

    private void setid(int id) {
        this.id=id;


    }
    public void setEvent(Recompense e) {
        if (e != null && e.getDateRecompense() != null) {
            setid(e.getId());
            System.out.println(getid());
            txtequipeGangnte.setText(e.getEquipeGagnante());
            txtmonatantRecompense.setText(String.valueOf(e.getMontantRecompense()));
            // Conversion de java.sql.Date en LocalDate
            Date dateEvent = (Date) e.getDateRecompense();
            LocalDate parsedDate = dateEvent.toLocalDate();
            txtdateRecompense.setValue(parsedDate);
        } else {
            // Gérer le cas où l'objet Arbitre ou la date d'embauche est nulle
        }
    }}

