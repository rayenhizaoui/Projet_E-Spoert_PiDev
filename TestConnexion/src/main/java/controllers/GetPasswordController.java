package controllers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entites.User;
import Utils.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Service.ServiceUserCrud;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;


import java.io.IOException;


//import com.sun.java.swing.plaf.windows.resources.windows;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class GetPasswordController {

  //  private Connection con=DataSource.getInstance().getCon();

    //private Statement ste;
    //PreparedStatement ps,ps1;
    //String num;
    //String pass;



    @FXML
    private TextField usernametxt;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField numerotxt;

    @FXML
    private TextField passtxt;

    @FXML
    private Button getpswBtn;

    @FXML
    private Button searchBtn;

    @FXML
    private Label errorLb;
    private final ServiceUserCrud ser = new ServiceUserCrud();
/*
    @FXML
    void searchPsw(ActionEvent event) throws IOException {

        try {


            passtxt.setText("");
            numerotxt.setText("");

            String u_name = usernametxt.getText().trim();
            if(u_name.isEmpty()){
                errorLb.setText("Please insert username");
            }
            else {
                String sql = "select email, numero , password from user where username=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, u_name);

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    txtemail.setText(rs.getString(1));
                    //questiontxt.setText(rs.getString(2));
                    num = rs.getString(2);
                    pass = rs.getString(3);
                    errorLb.setText("");

                    ps.close();
                    rs.close();
                }
                else {
                    errorLb.setText("Error: Username is incorrect");
                }


            }

        } catch (Exception ex) {
            System.out.println("something wrong" + ex);
        }

    }
*/








   /* @FXML
    void searchPsw(ActionEvent event) {
        try {
            String u_name = usernametxt.getText().trim();
            if (u_name.isEmpty()) {
                errorLb.setText("Please insert username");
                return;
            }

            // Requête pour récupérer l'email, le numéro et le mot de passe correspondant à l'utilisateur
            String sql = "SELECT email, numero, password FROM user WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, u_name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        txtemail.setText(rs.getString("email"));
                        numerotxt.setText(rs.getString("numero"));
                        pass = rs.getString("password");
                        errorLb.setText("");

                        // Si le numéro est vide, on demande à l'utilisateur de le remplir
                        if (numerotxt.getText().isEmpty()) {
                            errorLb.setText("Please insert your number");
                        }
                    } else {
                        errorLb.setText("Error: Username is incorrect");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
    }

    @FXML
    void retrivePsw(ActionEvent event) {
        String enteredNumber = numerotxt.getText().trim();
        if (enteredNumber.isEmpty()) {
            errorLb.setText("Please insert your number");
            return;
        }

        // Vérifie si le numéro entré correspond à celui récupéré dans la recherche précédente
        if (enteredNumber.equals(num)) {
            passtxt.setText(pass);
            errorLb.setText("");
        } else {
            errorLb.setText("Error: Number is incorrect");
        }
    }*/
////////////////////////////////////////////////////////////////////////////
   /* @FXML
    void retrivePsw(ActionEvent event) throws IOException {

        if(ans.equals(answertxt.getText().trim())){
            passtxt.setText(pass);
        }
        else {
            errorAnswer.setText("Your answer is wrong. Please try again");
        }

    }
*/
//////////////////////////////////////////////////////////////////////////////






/*    @FXML
    void backLogin(ActionEvent event) throws IOException {
        Parent view4=FXMLLoader.load(getClass().getResource("AjouterUser.fxml"));
        Scene scene4=new Scene(view4);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();

    }


    public void retrivePsw(javafx.event.ActionEvent actionEvent) {
    }

    public void backLogin(javafx.event.ActionEvent actionEvent) {
    }

    public void searchPsw(javafx.event.ActionEvent actionEvent) {
    }
}
*/











    private Connection con = DataSource.getInstance().getCon();
    private int num;
    private String pass;





    @FXML
    void searchPsw(ActionEvent event) {
        try {
            String u_name = usernametxt.getText().trim();
            if (u_name.isEmpty()) {
                errorLb.setText("Please insert username");
                return;
            }

            // Requête pour récupérer l'email, le numéro et le mot de passe correspondant à l'utilisateur
            String sql = "SELECT email, numero, password FROM user WHERE username=?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, u_name);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        txtemail.setText(rs.getString("email"));
                        pass = rs.getString("password");
                        errorLb.setText("");

                        // Si le numéro est vide, on demande à l'utilisateur de le remplir
                        String enteredNumber = numerotxt.getText().trim();
                        if (enteredNumber.isEmpty()) {
                            errorLb.setText("Please insert your number");
                            return;
                        }

                        // Vérifie si le numéro entré correspond à celui récupéré dans la base de données
                        if (enteredNumber.equals(rs.getString("numero"))) {
                            passtxt.setText(pass);
                            errorLb.setText("");
                        } else {
                            errorLb.setText("Error: Number is incorrect");
                        }
                    } else {
                        errorLb.setText("Error: Username is incorrect");
                    }
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
    }

private String numFromDB; // Variable globale pour stocker le numéro récupéré de la base de données
/*






/*
    @FXML
    void retrivePsw(ActionEvent event) {
        String enteredNumber = numerotxt.getText().trim();
        if (enteredNumber.isEmpty()) {
            errorLb.setText("Please insert your number");
            return;
        }

        // Vérifie si le numéro entré correspond à celui récupéré dans la recherche précédente
        if (enteredNumber.equals(num)) {
            passtxt.setText(pass);
            errorLb.setText("");
        } else {
            errorLb.setText("Error: Number is incorrect");
        }
    }
*/





    @FXML
    void backLogin(ActionEvent event) throws IOException {
        Parent view4 = FXMLLoader.load(getClass().getResource("/AjouterUser.fxml"));
        Scene scene4 = new Scene(view4);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();

    }


}

