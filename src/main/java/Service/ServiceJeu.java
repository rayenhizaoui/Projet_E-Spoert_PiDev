package Service;
import Entites.jeu;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ServiceJeu implements IService<jeu>{
    private static Connection con = DataSource.getInstance().getCon();
    private Statement ste;

    public ServiceJeu() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
}
    @Override
    public void ajouter(jeu jeu) throws SQLException {
        String req = "INSERT INTO `jeu` (`id`,`nom`, `type`, `score`, `resultat`) VALUES ('"+jeu.getId()+"', '"+jeu.getNom()+"', '"+jeu.getType()+"','"+jeu.getScore()+"','"+jeu.getResultat()+"')";
        ste.executeUpdate(req);
    }

    @Override
    public void delete(jeu jeu) throws SQLException {
        String req = "DELETE FROM `jeu` WHERE `id` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, jeu.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void update(jeu jeu) throws SQLException {
        String req = "UPDATE `jeu` SET `nom`=?, `type`=?, `score`=?, `resultat`=? WHERE `id`=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setString(1, jeu.getNom());
            preparedStatement.setString(2, jeu.getType());
            preparedStatement.setInt(3, jeu.getScore());
            preparedStatement.setString(4, jeu.getResultat());
            preparedStatement.setInt(5, jeu.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public jeu findById(int id) throws SQLException {
        String req = "SELECT * FROM `jeu` WHERE `id` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int jeuId = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String type = resultSet.getString("type");
                    int score = resultSet.getInt("score");
                    String resultat = resultSet.getString("resultat");

                    return new jeu(jeuId, nom, type, score, resultat);
                }
            }
        }
        return null;
    }

    public List<jeu> readAll() throws SQLException {
        List<jeu> jeux = new ArrayList<>();
        String req = "SELECT * FROM `jeu`";
        try (PreparedStatement preparedStatement = con.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nom = resultSet.getString("nom");
                String type = resultSet.getString("type");
                int score = resultSet.getInt("score");
                String resultat = resultSet.getString("resultat");

                jeux.add(new jeu(id, nom, type, score, resultat));
            }
        }
        return jeux;
    }
    public List<jeu> search(String term) throws SQLException {
        List<jeu> resultList = new ArrayList<>();
        String req = "SELECT * FROM `jeu` WHERE `id` LIKE ? OR `nom` LIKE ? OR `type` LIKE ? OR `score` LIKE ? OR `resultat` LIKE ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            for (int i = 1; i <= 5; i++) {
                preparedStatement.setString(i, "%" + term + "%");
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int jeuId = resultSet.getInt("id");
                    String nom = resultSet.getString("nom");
                    String type = resultSet.getString("type");
                    int score = resultSet.getInt("score");
                    String resultat = resultSet.getString("resultat");

                    resultList.add(new jeu(jeuId, nom, type, score, resultat));
                }
            }
        }
        return resultList;
    }
}
