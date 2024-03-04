package controllers;
import Entites.User;
import Service.ServiceUserCrud;
import Utils.DataSource;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import Entites.UserRole;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AjouterUserController {



    // Définition de la fonction afficherAlerte
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    @FXML
    void initialize() {

        // Ajouter un gestionnaire d'événements pour le champ CIN
        txtcin.setOnKeyReleased(event -> {
            String text = txtcin.getText();
            // Vérifier si le texte n'est pas composé uniquement de chiffres
            if (!text.matches("\\d*")) {
                // Si non, remplacer tous les caractères non numériques par une chaîne vide
                txtcin.setText(text.replaceAll("[^\\d]", ""));
                // Déplacer le curseur à la fin du champ
                txtcin.positionCaret(txtcin.getText().length());
            }

            // Limiter le nombre maximal de chiffres à 8
            if (text.length() > 8) {
                // Si le nombre de chiffres dépasse 8, raccourcir le texte à 8 caractères
                txtcin.setText(text.substring(0, 8));
                // Déplacer le curseur à la fin du champ
                txtcin.positionCaret(txtcin.getText().length());
            }
        });

        // Ajouter un gestionnaire d'événements pour le champ de numéro
        txtnumero.setOnKeyReleased(event -> {


            String text = txtnumero.getText();
            // Vérifier si le texte n'est pas composé uniquement de chiffres
            if (!text.matches("\\d*")) {
                // Si non, remplacer tous les caractères non numériques par une chaîne vide
                txtnumero.setText(text.replaceAll("[^\\d]", ""));
                // Déplacer le curseur à la fin du champ
                txtnumero.positionCaret(txtnumero.getText().length());
            }


            // Obtenir le texte actuel dans le champ de numéro
            //String text = txtnumero.getText();

            // Limiter le nombre maximal de chiffres à 8
            if (text.length() > 8) {
                // Si le nombre de chiffres dépasse 8, raccourcir le texte à 8 caractères
                txtnumero.setText(text.substring(0, 8));
                // Déplacer le curseur à la fin du champ
                txtnumero.positionCaret(txtnumero.getText().length());
            }
        });

// Ajouter un gestionnaire d'événements pour le champ de l'username
        txtusername.setOnKeyReleased(event -> {
            // Obtenir le texte actuel dans le champ de l'username
            String text = txtusername.getText();

            // Limiter le nombre maximal de caractères à 10
            if (text.length() > 10) {
                // Si le nombre de caractères dépasse 10, raccourcir le texte à 10 caractères
                txtusername.setText(text.substring(0, 10));
                // Déplacer le curseur à la fin du champ
                txtusername.positionCaret(txtusername.getText().length());
            }
        });


// Ajouter un gestionnaire d'événements pour le champ de l'username
        txtadresse.setOnKeyReleased(event -> {
            // Obtenir le texte actuel dans le champ de l'username
            String text = txtadresse.getText();

            // Limiter le nombre maximal de caractères à 10
            if (text.length() > 10) {
                // Si le nombre de caractères dépasse 10, raccourcir le texte à 10 caractères
                txtadresse.setText(text.substring(0, 10));
                // Déplacer le curseur à la fin du champ
                txtadresse.positionCaret(txtadresse.getText().length());
            }
        });

// Ajouter un gestionnaire d'événements pour le champ d'email
        txtemail.setOnKeyReleased(event -> {
            // Obtenir le texte actuel dans le champ d'email
            String text = txtemail.getText();

            // Limiter le nombre maximal de caractères à 20
            if (text.length() > 20) {
                // Si le nombre de caractères dépasse 20, raccourcir le texte à 20 caractères
                txtemail.setText(text.substring(0, 20));
                // Déplacer le curseur à la fin du champ
                txtemail.positionCaret(txtemail.getText().length());
            }


        });



        // Créer une liste de rôles disponibles
        List<String> roles = Arrays.asList("SPECTATEUR", "ADMIN", "JOUEUR","SPONSOR","ARBITRE");


        // Convertir la liste en ObservableList pour pouvoir l'utiliser avec la ChoiceBox
        ObservableList<String> rolesList = FXCollections.observableArrayList(roles);


        // Remplir la ChoiceBox avec la liste des rôles
        choicerole.setItems(rolesList);

    }

    @FXML
    private ChoiceBox<String> choicerole;
    @FXML
    private TextField txtusername;

    @FXML
    private TextField txtnumero;

    @FXML
    private TextField txtemail;

    @FXML
    private TextField txtadresse;

    @FXML
    private TextField txtpassword;

    @FXML
    private TextField txtrole;

    @FXML
    private TextField txtcin;

    @FXML
    private TextField txtpassword1;

    @FXML
    private TextField txtusername1;

    @FXML
    private Label oo;

    @FXML
    private Button forgotBtn;

    @FXML
    void forgotPsw(ActionEvent event) throws IOException{
        Parent view4=FXMLLoader.load(getClass().getResource("/GetPassword.fxml"));
        Scene scene4=new Scene(view4);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene4);
        window.show();
    }

    private final ServiceUserCrud ser = new ServiceUserCrud();






/*
    @FXML
    void AjouterUser(ActionEvent event) {


        // Valider le champ de numéro
        String numeroText = txtnumero.getText();
        if (!numeroText.matches("\\d{8}")) {
            // Afficher une alerte indiquant que le numéro n'est pas valide
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText(null);
            alert.setContentText("Le numéro doit contenir exactement 8 chiffres.");
            alert.showAndWait();
            return; // Arrêter le traitement car la saisie n'est pas valide


        // Récupérer les valeurs des champs de saisie
        int CIN = Integer.parseInt(txtcin.getText());
        String username = txtusername.getText();
        int numero = Integer.parseInt(txtnumero.getText());
        String email = txtemail.getText();
        String adresse = txtadresse.getText();
        String password = txtpassword.getText();
        String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox

        // Vérifier chaque contrôle de saisie
        if (String.valueOf(CIN).length() != 10) {
            afficherAlerte("Le CIN doit contenir exactement 10 chiffres");
            return;
        }
        if (username.length() > 20) {
            afficherAlerte("Le nom d'utilisateur doit contenir au maximum 20 caractères");
            return;
        }
        if (String.valueOf(numero).length() != 8) {
            afficherAlerte("Le numéro doit contenir exactement 8 chiffres");
            return;
        }
        if (!email.contains("@") || !email.contains(".")) {
            afficherAlerte("L'email doit contenir '@' et '.'");
            return;
        }
        if (adresse.length() < 20) {
            afficherAlerte("L'adresse doit contenir au moins 20 caractères");
            return;
        }
        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            afficherAlerte("Le mot de passe doit contenir au moins 8 caractères et au moins un chiffre");
            return;
        }

        // Toutes les vérifications sont passées, créer et ajouter l'utilisateur
        User u4 = new User(CIN, username, numero, email, adresse, password, roles);

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("User ajouté avec succès");
        alert1.showAndWait();

        try {
            ser.ajouter(u4);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Méthode utilitaire pour afficher une alerte d'erreur
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur de saisie");
        alert.setContentText(message);
        alert.showAndWait();
    }

*/


/*
    @FXML
    void AjouterUser(ActionEvent event) {
        // Valider le champ de numéro
        String numeroText = txtnumero.getText();
        if (!numeroText.matches("\\d{8}")) {
            // Afficher une alerte indiquant que le numéro n'est pas valide
            afficherAlerte("Le numéro doit contenir exactement 8 chiffres.");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }

        try {
            // Récupérer les valeurs des champs de saisie
            int CIN = Integer.parseInt(txtcin.getText());
            String username = txtusername.getText();
            int numero = Integer.parseInt(txtnumero.getText());
            String email = txtemail.getText();
            String adresse = txtadresse.getText();
            String password = txtpassword.getText();
            String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox

            // Vérifier chaque contrôle de saisie
            if (String.valueOf(CIN).length() != 10) {
                afficherAlerte("Le CIN doit contenir exactement 10 chiffres");
                return;
            }
            if (username.length() > 20) {
                afficherAlerte("Le nom d'utilisateur doit contenir au maximum 20 caractères");
                return;
            }
            if (!email.contains("@") || !email.contains(".")) {
                afficherAlerte("L'email doit contenir '@' et '.'");
                return;
            }
            if (adresse.length() < 20) {
                afficherAlerte("L'adresse doit contenir au moins 20 caractères");
                return;
            }
            if (password.length() < 8 || !password.matches(".*\\d.*")) {
                afficherAlerte("Le mot de passe doit contenir au moins 8 caractères et au moins un chiffre");
                return;
            }

            // Toutes les vérifications sont passées, créer et ajouter l'utilisateur
            User u4 = new User(CIN, username, numero, email, adresse, password, roles);

            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("User ajouté avec succès");
            alert1.showAndWait();

            ser.ajouter(u4);
        } catch (NumberFormatException e) {
            afficherAlerte("Le format de nombre n'est pas valide");
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de l'ajout de l'utilisateur : " + e.getMessage());
        }
    }

    // Méthode utilitaire pour afficher une alerte
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
*/


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


/*
    @FXML
    void AjouterUser(ActionEvent event) {
        int CIN = Integer.parseInt(txtcin.getText());
        String username = txtusername.getText();
        int numero = Integer.parseInt(txtnumero.getText());
        String email = txtemail.getText();
        String adresse = txtadresse.getText();
        String password = txtpassword.getText();
        String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox

        User u4 = new User(CIN, username, numero, email, adresse, password, roles);

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("User ajouté avec succès");
        alert1.showAndWait();

        try {
            ser.ajouter(u4);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }


        // Valider le champ de CIN
        if (String.valueOf(CIN).length() != 8) {
            // Afficher une alerte indiquant que le CIN n'est pas valide
            afficherAlerte("Le CIN doit contenir exactement 8 chiffres");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }


        // Valider le champ de mot de passe
        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            // Afficher une alerte indiquant que le mot de passe n'est pas valide
            afficherAlerte("Le mot de passe doit contenir au moins 8 caractères et au moins un chiffre");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }


        // Valider le champ d'email
        //String email = txtemail.getText();
        if (!email.contains("@") || !email.contains(".")) {
            // Afficher une alerte indiquant que l'email n'est pas valide
            afficherAlerte("L'email doit contenir à la fois '@' et '.'");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }


    }
*/
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////



    @FXML
    void AjouterUser(ActionEvent event) {
        int CIN = Integer.parseInt(txtcin.getText());
        String username = txtusername.getText();
        int numero = Integer.parseInt(txtnumero.getText());
        String email = txtemail.getText();
        String adresse = txtadresse.getText();
        String password = txtpassword.getText();
        String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox

        // Valider le champ de CIN
        if (String.valueOf(CIN).length() != 8) {
            // Afficher une alerte indiquant que le CIN n'est pas valide
            afficherAlerte("Le CIN doit contenir exactement 8 chiffres");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }

        // Valider le champ de mot de passe
        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            // Afficher une alerte indiquant que le mot de passe n'est pas valide
            afficherAlerte("Le mot de passe doit contenir au moins 8 caractères et au moins un chiffre");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }

        // Valider le champ d'email
        if (!email.contains("@") || !email.contains(".")) {
            // Afficher une alerte indiquant que l'email n'est pas valide
            afficherAlerte("L'email doit contenir à la fois '@' et '.'");
            return; // Arrêter le traitement car la saisie n'est pas valide
        }

        // Toutes les validations sont passées, créer et ajouter l'utilisateur
        User u4 = new User(CIN, username, numero, email, adresse, password, roles);

        // Afficher une boîte de dialogue de confirmation
        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("User ajouté avec succès");
        alert1.showAndWait();

        try {
            // Ajouter l'utilisateur à la base de données
            ser.ajouter(u4);
        } catch (SQLException e) {
            // En cas d'erreur lors de l'ajout, afficher une alerte d'erreur
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }




/*
    @FXML
    void AjouterUser(ActionEvent event) {
        int cin = Integer.parseInt(txtcin.getText());
        String username = txtusername.getText();
        int numero = Integer.parseInt(txtnumero.getText());
        String email = txtemail.getText();
        String adresse = txtadresse.getText();
        String password = txtpassword.getText();
        String roles = choicerole.getValue(); // Récupérer la valeur sélectionnée dans la ChoiceBox

        User u4 = new User(cin, username, numero, email, adresse, password, roles);

        Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
        alert1.setTitle("Confirmation");
        alert1.setContentText("User ajouté avec succès");
        alert1.showAndWait();

        try {
            ser.ajouter(u4);
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }

    }

*/





    @FXML
    void afficherUser(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherUser.fxml"));
            Parent root = loader.load();
            txtcin.getScene().setRoot(root);
            AfficherUserController dc = loader.getController();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }







    @FXML
    public void ConnecterUser(ActionEvent event) {
        String username = txtusername1.getText();
        String password = txtpassword1.getText();

        try {
            User user = ServiceUserCrud.authentifierUtilisateur(username, password);

            if (user != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Succès");
                alert.setHeaderText(null);
                alert.setContentText("Authentification réussie !");
                alert.showAndWait();

                FXMLLoader loader;
                if (user.getRoles().contains(User.UserRole.SPECTATEUR.toString())) {
                    loader = new FXMLLoader(getClass().getResource("/AjouterBillet.fxml"));
                } else if (user.getRoles().contains(User.UserRole.ADMIN.toString())) {
                    loader = new FXMLLoader(getClass().getResource("/Admin.fxml"));
                } else if (user.getRoles().contains(User.UserRole.JOUEUR.toString())) {
                    loader = new FXMLLoader(getClass().getResource("/Home.fxml"));
                }else if (user.getRoles().contains(User.UserRole.SPONSOR.toString())) {
                        loader = new FXMLLoader(getClass().getResource("/AfficherEquipement.fxml"));
                }else if (user.getRoles().contains(User.UserRole.ARBITRE.toString())) {
                    loader = new FXMLLoader(getClass().getResource("/frontRC.fxml"));
                } else {
                    throw new IllegalStateException("Rôle utilisateur non reconnu");
                }

                Parent root = loader.load();
                txtcin.getScene().setRoot(root);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Échec");
                alert.setHeaderText(null);
                alert.setContentText("Nom d'utilisateur ou mot de passe incorrect !");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            System.out.println("Erreur lors de l'authentification : " + e.getMessage());
        }
    }









}












