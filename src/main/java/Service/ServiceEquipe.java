package Service;

import Entites.equipe;
import Entites.jeu;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEquipe implements IService<equipe> {
    private Connection con = DataSource.getInstance().getCon();
    private Statement ste;

    public ServiceEquipe() {
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ajouter(equipe equipe) throws SQLException {
        String req = "INSERT INTO `equipe` (`id`,`id_jeu`, `nom`, `nbr_joueur`, `liste_joueur`) VALUES ('"+equipe.getId()+"', '"+equipe.getIdJeu()+"', '"+equipe.getNom()+"','"+equipe.getNbrJoueur()+"','"+equipe.getListeJoueur()+"');";
        ste.executeUpdate(req);
    }

    @Override
    public void update(equipe equipe) throws SQLException {
        String req = "UPDATE `equipe` SET `id_jeu`=?, `nom`=?, `nbr_joueur`=?, `liste_joueur`=? WHERE `id`=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, equipe.getIdJeu());
            preparedStatement.setString(2, equipe.getNom());
            preparedStatement.setInt(3, equipe.getNbrJoueur());
            preparedStatement.setString(4, equipe.getListeJoueur());
            preparedStatement.setInt(5, equipe.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public void delete(equipe equipe) throws SQLException {
        String req = "DELETE FROM `equipe` WHERE `id`=?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, equipe.getId());
            preparedStatement.executeUpdate();
        }
    }

    @Override
    public List<equipe> readAll() throws SQLException {
        List<equipe> equipes = new ArrayList<>();
        String req = "SELECT * FROM `equipe`";
        try (PreparedStatement preparedStatement = con.prepareStatement(req);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int id_jeu = resultSet.getInt("id_jeu");
                String nom = resultSet.getString("nom");
                int nbr_joueur = resultSet.getInt("nbr_joueur");
                String liste_joueur = resultSet.getString("liste_joueur");

                equipes.add(new equipe(id, id_jeu, nom, nbr_joueur, liste_joueur));
            }
        }
        return equipes;
    }

    public equipe findById(int id) throws SQLException {
        String req = "SELECT * FROM `equipe` WHERE `id` = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id_jeu = resultSet.getInt("id_jeu");
                    String nom = resultSet.getString("nom");
                    int nbr_joueur = resultSet.getInt("nbr_joueur");
                    String liste_joueur = resultSet.getString("liste_joueur");

                    return new equipe(id, id_jeu, nom, nbr_joueur, liste_joueur);
                }
            }
        }
        return null;
    }
    public List<equipe> search(String term) throws SQLException {
        List<equipe> resultList = new ArrayList<>();
        String req = "SELECT * FROM `equipe` WHERE `id` LIKE ? OR `id_jeu` LIKE ? OR `nom` LIKE ? OR `nbr_joueur` LIKE ? OR `liste_joueur` LIKE ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(req)) {
            for (int i = 1; i <= 5; i++) {
                preparedStatement.setString(i, "%" + term + "%");
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int id_jeu = resultSet.getInt("id_jeu");
                    String nom = resultSet.getString("nom");
                    int nbr_joueur = resultSet.getInt("nbr_joueur");
                    String liste_joueur = resultSet.getString("liste_joueur");

                    resultList.add(new equipe(id, id_jeu, nom, nbr_joueur, liste_joueur));
                }
            }
        }
        return resultList;
    }

}
