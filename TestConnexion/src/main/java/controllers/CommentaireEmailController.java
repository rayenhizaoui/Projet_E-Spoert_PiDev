package controllers;
//import com.google.protobuf.Message;
//import com.mysql.cj.Session;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
/*
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.PasswordAuthentication;
import java.util.Properties;
*/

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.util.Properties;
//import javax.mail.Session;
//import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;


import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;


import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;




import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CommentaireEmailController {


    @FXML
    private TextField emailtxt;

    @FXML
    private TextField commenttxt;

    @FXML
    private Label errorLb;

 /*   @FXML
    void envoyerMail(ActionEvent event) {
// Récupérer l'adresse e-mail destinataire
        String toEmail = emailtxt.getText().trim();

        // Vérifier si l'adresse e-mail est vide
        if (toEmail.isEmpty()) {
            errorLb.setText("Please provide recipient email address");
            return;
        }

        // Récupérer le contenu du commentaire
        String comment = commenttxt.getText().trim();

        // Vérifier si le commentaire est vide
        if (comment.isEmpty()) {
            errorLb.setText("Please provide a comment to send");
            return;
        }

        // Paramètres pour la connexion SMTP
        String host = "smtp.example.com"; // Remplacer par l'hôte SMTP approprié
        String port = "587"; // Remplacer par le port SMTP approprié
        String username = "rayen@gmail.com"; // Remplacer par votre nom d'utilisateur SMTP
        String password = "azertyqsqsdf"; // Remplacer par votre mot de passe SMTP

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session de messagerie avec authentification
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Adresse e-mail du destinataire
            message.setSubject("Comment from Your Application"); // Sujet du message
            message.setText(comment); // Contenu du message

            // Envoyer le message
            Transport.send(message);

            // Message de succès
            errorLb.setText("Comment sent successfully to " + toEmail);
        } catch (MessagingException e) {
            // Erreur lors de l'envoi du message
            errorLb.setText("Error: Unable to send comment. Please try again later.");
            e.printStackTrace();
        }
    }
*/



/*
    @FXML
    void envoyerMail(ActionEvent event) {
        // Récupérer l'adresse e-mail destinataire
        String toEmail = emailtxt.getText().trim();

        // Vérifier si l'adresse e-mail est vide
        if (toEmail.isEmpty()) {
            errorLb.setText("Please provide recipient email address");
            return;
        }

        // Récupérer le contenu du commentaire
        String comment = commenttxt.getText().trim();

        // Vérifier si le commentaire est vide
        if (comment.isEmpty()) {
            errorLb.setText("Please provide a comment to send");
            return;
        }

        // Paramètres pour la connexion SMTP
        String host = "smtp.example.com"; // Remplacer par l'hôte SMTP approprié
        String port = "587"; // Remplacer par le port SMTP approprié
        String username = "rayen@gmail.com"; // Remplacer par votre nom d'utilisateur SMTP
        String password = "azertyqsqsdf"; // Remplacer par votre mot de passe SMTP

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session de messagerie avec authentification
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Adresse e-mail du destinataire
            message.setSubject("Comment from Your Application"); // Sujet du message
            message.setText(comment); // Contenu du message

            // Envoyer le message
            Transport.send(message);

            // Message de succès
            errorLb.setText("Comment sent successfully to " + toEmail);
        } catch (MessagingException e) {
            // Erreur lors de l'envoi du message
            errorLb.setText("Error: Unable to send comment. Please try again later.");
            e.printStackTrace();
        }
    }
*/

/*
    @FXML
    void envoyerMail(ActionEvent event) {
        // Récupérer l'adresse e-mail destinataire
        String toEmail = emailtxt.getText().trim();

        // Vérifier si l'adresse e-mail est vide
        if (toEmail.isEmpty()) {
            errorLb.setText("Please provide recipient email address");
            return;
        }

        // Récupérer le contenu du commentaire
        String comment = commenttxt.getText().trim();

        // Vérifier si le commentaire est vide
        if (comment.isEmpty()) {
            errorLb.setText("Please provide a comment to send");
            return;
        }

        // Paramètres pour la connexion SMTP
        String host = "smtp.example.com"; // Remplacer par l'hôte SMTP approprié
        String port = "587"; // Remplacer par le port SMTP approprié
        String username = "rayen@gmail.com"; // Remplacer par votre nom d'utilisateur SMTP
        String password = "azertyqsqsdf"; // Remplacer par votre mot de passe SMTP

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session de messagerie avec authentification
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Adresse e-mail du destinataire
            message.setSubject("Comment from Your Application"); // Sujet du message
            message.setText(comment); // Contenu du message

            // Envoyer le message
            Transport.send(message);

            // Message de succès
            errorLb.setText("Comment sent successfully to " + toEmail);
        } catch (MessagingException e) {
            // Erreur lors de l'envoi du message
            errorLb.setText("Error: Unable to send comment. Please try again later.");
            e.printStackTrace();
        }
    }
*/








    @FXML
    void envoyerMail(ActionEvent event) {
        // Récupérer l'adresse e-mail destinataire
        String toEmail = emailtxt.getText().trim();

        // Vérifier si l'adresse e-mail est vide
        if (toEmail.isEmpty()) {
            errorLb.setText("Please provide recipient email address");
            return;
        }

        // Récupérer le contenu du commentaire
        String comment = commenttxt.getText().trim();

        // Vérifier si le commentaire est vide
        if (comment.isEmpty()) {
            errorLb.setText("Please provide a comment to send");
            return;
        }

        // Paramètres pour la connexion SMTP
        String host = "smtp.gmail.com"; // Remplacer par l'hôte SMTP approprié
        String port = "587"; // Remplacer par le port SMTP approprié
        String username = "rayenhizaoui05@gmail.com"; // Remplacer par votre nom d'utilisateur SMTP
        String password = "bfoo rtll hcau mihw"; // Remplacer par votre mot de passe SMTP

        // Propriétés SMTP
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Créer une session de messagerie avec authentification
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new javax.mail.PasswordAuthentication(username, password);
            }
        });

        try {
            // Créer un message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username)); // Adresse e-mail de l'expéditeur
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Adresse e-mail du destinataire
            message.setSubject("Comment from Your Application"); // Sujet du message
            message.setText(comment); // Contenu du message

            // Envoyer le message
            Transport.send(message);

            // Message de succès
            errorLb.setText("Comment sent successfully to " + toEmail);
        } catch (MessagingException e) {
            // Erreur lors de l'envoi du message
            errorLb.setText("Error: Unable to send comment. Please try again later.");
            e.printStackTrace();
        }
    }


    @FXML
    void retour1(ActionEvent event) throws IOException {
        Parent view3= FXMLLoader.load(getClass().getResource("/AjouterBillet.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }




    }


