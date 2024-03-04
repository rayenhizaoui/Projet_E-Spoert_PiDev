package controller;

import Utils.Connexion;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.Tournoi;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class BookEventController implements Initializable {
    Connection mc;
    PreparedStatement ste;
    ResultSet resultSet = null;
    @FXML
    private Label Date_lab;

    @FXML
    private TextField Cvv_txt;

    @FXML
    private TextField DateCard_txt;
    @FXML
    private TextField NumCard_txt;

    @FXML
    private Label Location_lab;

    @FXML
    private Label Title_lab;

    @FXML
    private TextField cin_tx;

    @FXML
    private TextField name_tx;



    @FXML
    private Label total_fld;

    @FXML
    private ImageView Pay_img;




    Tournoi Chosen ;
    public Tournoi getChosen() {
        return this.Chosen;
    }

    public void setChosen(Tournoi chosen) {
        this.Chosen = chosen;
    }
    String s =null;

    public String getS() {
        return this.s;
    }

    public void setS(String s) {
        this.s = s;
    }

    public BookEventController(){}

@FXML
    public void getEventBook(String s) {
       // System.out.println(s);
        String sql = "SELECT * FROM tournoi";


        try {
            Tournoi a = null;
            mc = Connexion.getInstance().getCnx();
            ste = mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            while (rs.next()) {
                a = new Tournoi(rs.getString("nom"), rs.getString("location"), rs.getInt("fraisParticipation"));
                System.out.println(a.getFraisParticipation());
                if (a.getNom().equals(s) ){
                    //Date_lab.setText(a.getevent_date());
                    Location_lab.setText(String.valueOf(a.getLocation()));
                    Title_lab.setText((a.getNom()));
                    total_fld.setText(""+a.getFraisParticipation());
                    setChosen(a);
                }
            }



        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }



    }
    @FXML
    public void TicketPdf() throws SQLException, IOException, DocumentException {

            Document doc = new Document();
            PdfWriter.getInstance(doc, new FileOutputStream("./RecuPaiement.pdf"));

            doc.open();
            // Image image = Image.getInstance("C:\\Users\\drwhoo\\Desktop\\Projet3eme\\JavaApplication\\src\\HolidaysHiatus\\images\\logo.png");

            //document.add(new Paragraph("test\n  test"));
            // doc.add(image);
            doc.add(new Paragraph("                                                                        Esport APP                    "));

            doc.add(new Paragraph("   "));

            doc.add(new Paragraph("                    Ticket to attend  " + Title_lab.getText()));
            doc.add(new Paragraph("   "));
            doc.add(new Paragraph("   "));

            doc.add(new Paragraph("Name : " + name_tx.getText()));
            doc.add(new Paragraph("   "));
            doc.add(new Paragraph("CIN  : " + cin_tx.getText()));
            doc.add(new Paragraph("Total  : " + total_fld.getText()));
            doc.add(new Paragraph("   "));
            doc.add(new Paragraph("   "));
            doc.add(new Paragraph("   "));
            doc.add(new Paragraph("Payed With  :  Card " ));





            int n = 0;
            while (n < 8) {
                doc.add(new Paragraph("   "));
                n++;
            }
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(110);

            PdfPCell cell;

            cell = new PdfPCell(new Phrase("Title", FontFactory.getFont("Comic Sans MS", 15)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(Color.PINK);
            cell.setBorderWidth(1);
            cell.setPadding(3);


            table.addCell(cell);

            cell = new PdfPCell(new Phrase("Location", FontFactory.getFont("Comic Sans MS", 15)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(Color.PINK);
            cell.setBorderWidth(1);
            cell.setPadding(3);


            table.addCell(cell);
            cell = new PdfPCell(new Phrase("Prix", FontFactory.getFont("Comic Sans MS", 15)));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(Color.PINK);
            cell.setBorderWidth(1);
            cell.setPadding(3);

            table.addCell(cell);
            int i = 0;
            while (i < 1) {

                cell = new PdfPCell(new Phrase(Title_lab.getText(), FontFactory.getFont("Comic Sans MS", 12)));
                cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setBackgroundColor(Color.PINK);
                cell.setBorderWidth(1);
                cell.setPadding(3);


                table.addCell(cell);
                cell = new PdfPCell(new Phrase(Location_lab.getText(), FontFactory.getFont("Comic Sans MS", 12)));
                cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setPadding(3);

                table.addCell(cell);
                cell = new PdfPCell(new Phrase(total_fld.getText(), FontFactory.getFont("Comic Sans MS", 12)));
                cell.setVerticalAlignment(Element.ALIGN_JUSTIFIED);
                cell.setPadding(3);

                table.addCell(cell);
                i++;
            }


            //Image image1 = Image.getInstance("C:\\Users\\drwhoo\\Desktop\\Projet3eme\\SymfonyApplication\\public\\uploads\\" + c.getLien_icon());
            //PdfPCell cell1 = new PdfPCell(image1, true);

            //  table.addCell(cell1);


            doc.add(table);
            n = 0;
            while (n < 8) {
                doc.add(new Paragraph("   "));
                n++;
            }
            doc.add(new Paragraph("  Thanks for your purchase  "));

            doc.close();
            Desktop.getDesktop().open(new File("./RecuPaiement.pdf"));
        }

    @FXML
    private void PaiementFrais() throws SQLException, DocumentException, IOException {
        try {
            if (name_tx.getText().matches("^[0-9]*$") || name_tx.getText().compareTo("") == 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Name invalid");
                alert.showAndWait();
            } else if (!cin_tx.getText().matches("^[0-9]*$") || cin_tx.getText().compareTo("") == 0 || cin_tx.getText().length() != 8) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("cin invalid");
                alert.showAndWait();
            }
           else if (!NumCard_txt.getText().matches("^[0-9]*$") || NumCard_txt.getText().compareTo("") == 0 || NumCard_txt.getText().length() != 16) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Num Card invalid");
                alert.showAndWait();
            } else if (DateCard_txt.getText().matches("^[0-9]*$") ||  DateCard_txt.getText().length() == 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Date invalide");
                alert.showAndWait();
            } else if (!Cvv_txt.getText().matches("^[0-9]*$") || Cvv_txt.getText().compareTo("") == 0 || Cvv_txt.getText().length() != 3) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("ERROR");
                alert.setContentText("Cvv invalid");
                alert.showAndWait();
            }
            else {
// Set your secret key here
                Stripe.apiKey = "sk_test_51KrhAWFenwx3M2XtmEadhP9MjORyBpjuq9jRSAR4SYLqsyXwVr6d8hiLBlEPye5sHL32HZvGAwQLPOdw3bbGe16Q009dPut1v5";
                Tournoi tournoi = getChosen();
// Create a PaymentIntent with other payment details
                PaymentIntentCreateParams params =
                        PaymentIntentCreateParams.builder()
                                .setAmount(2000L)
                                .setCurrency("usd")
                                .setAutomaticPaymentMethods(
                                        PaymentIntentCreateParams.AutomaticPaymentMethods.builder()
                                                .setEnabled(true)
                                                .build()
                                )
                                .build();
                PaymentIntent paymentIntent = PaymentIntent.create(params);

// If the payment was successful, display a success message
                System.out.println("Payment successful. PaymentIntent ID: " + paymentIntent.getId());
                TicketPdf();
            }}
        catch(StripeException e){
// If there was an error processing the payment, display the error message
                System.out.println("Payment failed. Error: " + e.getMessage());
            }

        }

    @FXML
    public void getMapView(MouseEvent event) throws IOException {

                // System.out.println( getEventChoiceStorage());
                FXMLLoader load = new FXMLLoader(getClass().getResource("/maps.fxml"));
                Parent root = load.load();

                MapsController controller = load.getController();

                // controller.setChosen(v);
       // System.out.println(Location_lab.getText());
        controller.getEventBook(Title_lab.getText());

                //controller.setS(EventNameLabel.getText());
                //Event event_o = controller.getChosen();
                // System.out.println(event_o);
                // controller.cin_tx.setText("aaaaa");
                Scene scene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();

        }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

      //System.out.println(getChosen());

        //Date_lab.setText(e.getevent_date());
       //Location_lab.setText(e.getVenueId());
        //Title_lab.setText(getChosen().getNom());
        //cin_tx.setText(e.getEventTitle());



    }
}
