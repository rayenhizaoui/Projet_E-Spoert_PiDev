package controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ChatbotController {

    @FXML
    private TextField txtmessage;
    @FXML
    private TextArea chatArea;
    private Map<String, String> conversations = new HashMap<>();

    public ChatbotController() {
        conversations.put("salut", "Salut!");
        conversations.put("cv", "Oui, ça va bien, et vous?");
        conversations.put("quel est le prochain match?", "Le prochain match est prévu pour demain à 18h.");
        conversations.put("quel est votre nom?", "Je suis un chatbot.");
        conversations.put("quel est votre équipe", "Bien sûr, je suis un grand fan de Barcelone!");
        conversations.put("quel jeu jouez-vous?", "Nous jouons actuellement à [Nom du jeu].");
    }

    public String chat(String userInput) {
        String response = conversations.get(userInput.toLowerCase());

        // Si aucune réponse prédéfinie n'est trouvée, répondre par défaut
        if (response == null) {
            response = "Je suis un chatbot et je ne sais pas comment répondre à ça!";
        }

        return response;
    }
    @FXML
    public void initialize() {
        // Ajoute un gestionnaire d'événements pour la pression de la touche "Entrée"
        txtmessage.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                envoyerMessage();
            }
        });
    }

    @FXML
    void envoyerMessage(ActionEvent event) {
        envoyerMessage();
    }

   private void envoyerMessage (){
        String message = txtmessage.getText();
        String response = chat(message);

        // Afficher la réponse du chatbot dans une boîte de dialogue
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Chatbot");
        alert.setHeaderText(null);
        alert.setContentText("Chatbot: " + response);
        alert.showAndWait();
        chatArea.appendText("Vous: " + message + "\n");
        // Affichage de la réponse du chatbot
        chatArea.appendText("Chatbot: " + response + "\n");

        // Effacer le champ de saisie après l'envoi du message
        txtmessage.clear();
    }
}

