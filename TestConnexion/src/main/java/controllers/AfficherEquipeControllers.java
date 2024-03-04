package controllers;

import Entites.User;
import Entites.equipe;
import Entites.jeu;
import Service.ServiceEquipe;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AfficherEquipeControllers {
    @FXML
    public TableView<equipe> tableView;


    @FXML
    private TableColumn<equipe, Integer> colid;

    @FXML
    private TableColumn<equipe, Integer> colidjeu;

    @FXML
    private TableColumn<equipe, String> colnom;

    @FXML
    private TableColumn<equipe, Integer> colnbrjoueur;

    @FXML
    private TableColumn<equipe, String> collistejoueur;
    @FXML
    private TextField txtRecherche;

    @FXML
    private TextField txtid;

    @FXML
    private TextField txtidjeu;

    @FXML
    private TextField txtnom;

    @FXML
    private TextField txtnbrjoueur;

    @FXML
    private TextField txtlistejoueur;


    private final ServiceEquipe serviceEquipe = new ServiceEquipe();

    private final ServiceEquipe serv = new ServiceEquipe();

    @FXML
    void initialize() {
        try {
            List<equipe> list = serviceEquipe.readAll();
            ObservableList<equipe> obers = FXCollections.observableList(list);
            System.out.println(list);

            tableView.setItems(obers);
            colid.setCellValueFactory(new PropertyValueFactory<>("id"));
            colidjeu.setCellValueFactory(new PropertyValueFactory<>("idJeu"));
            colnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            colnbrjoueur.setCellValueFactory(new PropertyValueFactory<>("nbrJoueur"));
            collistejoueur.setCellValueFactory(new PropertyValueFactory<>("listeJoueur"));
        } catch (SQLException e) {
            throw new RuntimeException(e); // Vous pouvez choisir de gérer l'exception d'une autre manière ici
        }
    }

    @FXML
    void retournerAInterfaceAjout(ActionEvent event) {
        try {
            FXMLLoader loader=new FXMLLoader(getClass().getResource("/AjouterEquipe.fxml"));
            Parent root=loader.load();
            AjouterEquipeController ajouterEquipeController = loader.getController();

            Stage stage = (Stage) tableView.getScene().getWindow(); // Récupérer la scène actuelle
            stage.setScene(new Scene(root)); // Changer la scène pour afficher l'interface d'ajout
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void recherche(ActionEvent event) {
        try {
            String searchTerm = txtRecherche.getText();

            // Si le champ de recherche est vide, afficher toutes les équipes
            if (searchTerm.isEmpty()) {
                List<equipe> allTeams = serviceEquipe.readAll();
                ObservableList<equipe> allTeamsObservable = FXCollections.observableList(allTeams);
                tableView.setItems(allTeamsObservable);
            } else {
                // Effectuer la recherche en utilisant la méthode search de ServiceEquipe
                List<equipe> resultList = serviceEquipe.search(searchTerm);

                // Mettre à jour la table avec les résultats de la recherche
                ObservableList<equipe> obers = FXCollections.observableList(resultList);
                tableView.setItems(obers);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ObservableList<equipe> getEquipeList() {
        return tableView.getItems();
    }

    public void statistique1(ActionEvent event) {
        List<Integer> nbrjoueur = new ArrayList<>();
        for (equipe equipe : tableView.getItems()) {
            nbrjoueur.add(equipe.getNbrJoueur());
        }

        // Afficher les statistiques en utilisant le contrôleur de vue StatistiqueController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/statistique1.fxml"));
        Parent root;
        try {
            root = loader.load();
            Statistique1Controller controller = loader.getController();
            controller.afficherStatistique1(nbrjoueur);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @FXML
    void ajouterEquipe(ActionEvent event) {

        int id = Integer.parseInt(txtid.getText());
        int id_jeu = Integer.parseInt(txtidjeu.getText());
        String nom = txtnom.getText();
        int nbr_joueur = Integer.parseInt(txtnbrjoueur.getText());
        String liste_joueur = txtlistejoueur.getText();

        equipe e1 = new equipe(id, id_jeu, nom, nbr_joueur, liste_joueur);


        try {
            serv.ajouter(e1);
            Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
            alert1.setTitle("Confirmation");
            alert1.setContentText("equipe ajouté avec succés");
            refreshTableView();
            alert1.showAndWait();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();

        }
    }


    private void afficherAlerte(Alert.AlertType type, String titre, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(titre);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void supprimerEquipe(ActionEvent actionEvent) {
        int id = Integer.parseInt(txtid.getText());

        // Appeler la méthode de suppression du service
        try {
            serv.delete(new equipe(id, 0, "", 0, ""));
            afficherAlerte(Alert.AlertType.INFORMATION, "Suppression réussie", "L'équipe a été supprimée avec succès.");
            refreshTableView();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur de suppression", "Une erreur s'est produite lors de la suppression de l'équipe : " + e.getMessage());
        }
    }

    @FXML
    void modifierEquipe(ActionEvent event) {
        int id = Integer.parseInt(txtid.getText());
        int cinJoueur = Integer.parseInt(txtidjeu.getText());
        String nom = txtnom.getText();
        int nbrJoueur = Integer.parseInt(txtnbrjoueur.getText());
        String listeJoueur = txtlistejoueur.getText();

        equipe equipe = new equipe(id, cinJoueur, nom, nbrJoueur, listeJoueur);
        try {
            serv.update(equipe);
            refreshTableView();
            afficherAlerte(Alert.AlertType.CONFIRMATION, "Confirmation", "Équipe modifiée avec succès");
            refreshTableView();
        } catch (SQLException e) {
            afficherAlerte(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de la modification de l'équipe : " + e.getMessage());
        }
    }



    private void refreshTableView() {
        try {
            tableView.getItems().clear();
            List<equipe> equipeList = serv.readAll();
            tableView.getItems().addAll(equipeList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void admin3(ActionEvent event) throws IOException{
        Parent view3= FXMLLoader.load(getClass().getResource("/Admin.fxml"));
        Scene scene3=new Scene(view3);
        Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene3);
        window.show();
    }


}

