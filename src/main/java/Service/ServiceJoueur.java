package Service;

import Entites.joueur;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceJoueur implements IService<joueur>{
    private Connection con= DataSource.getInstance().getCon();

    private Statement ste;
    public ServiceJoueur()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }


    }
    @Override
    public void ajouter(joueur joueur) throws SQLException {
        String req="INSERT INTO `joueur` ( `cin`,`nom`, `prenom`, `genre`,`tel`,`email`) VALUES ( '"+joueur.getCin()+"', '"+joueur.getNom()+"', '"+joueur.getPrenom()+"','"+joueur.getGenre()+"','"+joueur.getTel()+"','"+joueur.getEmail()+"');";
        ste.executeUpdate(req);
    }


    public void delete(joueur joueur) throws SQLException {
        String req = "DELETE FROM `joueur` WHERE `cin` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, joueur.getCin()); // Supposons que getCin() retourne le cin du joueur
            preparedStatement.executeUpdate();
        }
    }


    @Override
    public void update(joueur joueur) throws SQLException {
        String req = "UPDATE `joueur` SET `nom`=?, `prenom`=?, `genre`=?, `tel`=?, `email`=? WHERE `cin`=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, joueur.getNom());
            preparedStatement.setString(2, joueur.getPrenom());
            preparedStatement.setString(3, joueur.getGenre());
            preparedStatement.setInt(4, joueur.getTel());
            preparedStatement.setString(5, joueur.getEmail());
            preparedStatement.setInt(6, joueur.getCin());
            preparedStatement.executeUpdate();
        }
    }

    public joueur findById(int cin) throws SQLException {
        String req = "SELECT * FROM `joueur` WHERE `cin` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, cin);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int joueurCin = resultSet.getInt("cin");
                    String nom = resultSet.getString("nom");
                    String prenom = resultSet.getString("prenom");
                    String genre = resultSet.getString("genre");
                    int tel = resultSet.getInt("tel");
                    String email = resultSet.getString("email");

                    return new joueur(joueurCin, nom, prenom, genre, tel, email);
                }
            }
        }
        return null;
    }


    @Override
    public List<joueur> readAll() throws SQLException {
        List<joueur> joueurs = new ArrayList<>();
        String req = "SELECT * FROM `joueur`";
        try (PreparedStatement preparedStatement = con.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int cin = resultSet.getInt("cin");
                String nom = resultSet.getString("nom");
                String prenom = resultSet.getString("prenom");
                String genre = resultSet.getString("genre");
                int tel = resultSet.getInt("tel");
                String email = resultSet.getString("email");

                joueurs.add(new joueur(cin, nom, prenom, genre, tel, email));
            }
        }
        return joueurs;
    }

}
