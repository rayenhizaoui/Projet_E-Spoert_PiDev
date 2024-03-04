package Service;
import Utils.DataSource;
import Entites.Local;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Service_l  implements Iservice_l<Local> {

    Connection mc;
    PreparedStatement ste;
    ResultSet resultSet = null;


    public Service_l() throws SQLException {
        mc = DataSource.getInstance().getCon();
    }

    @Override
    public void ajouterl(Local l) throws SQLException {
        String sql = "INSERT INTO `local`(`adresse`, `capacite`, `categorie`) VALUES (?, ?, ?)";
        ste = mc.prepareStatement(sql);
        ste.setString(1, l.getAdresse());
        ste.setInt(2, l.getCapacite());
        ste.setString(3, l.getCategorie());
        ste.executeUpdate();
        System.out.println("Local ajouté avec succès.");

    }

    @Override
    public void deletel(int id) throws SQLException {
        String sql = "DELETE FROM local WHERE id ="+id;
        ste = mc.prepareStatement(sql);
        ste.executeUpdate();
    }

    @Override
    public void updatel(Local local , int Id) throws SQLException {
        String sql = "UPDATE local SET adresse = ?, capacite = ?, categorie = ? WHERE id ="+Id;
        ste = mc.prepareStatement(sql);
        ste.setString(1, local.getAdresse());
        ste.setInt(2, local.getCapacite());
        ste.setString(3, local.getCategorie());
        ste.executeUpdate();

    }

    @Override
    public Local findByIdl(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Local> readAll() throws SQLException {
        return null;
    }

   /* @Override
    public void delete(Tournoi tournoi) throws SQLException {
        String sql = "DELETE FROM tournoi WHERE id = 1";
        ste = mc.prepareStatement(sql);
        ste.executeUpdate();
    }

    @Override
    public void update(Tournoi tour) throws SQLException {

        String sql = "UPDATE tournoi SET nom = ?, nomEquipe = ?, nombreParticipants = ?, duree = ?, typeJeu = ?, fraisParticipation = ?, location = ? WHERE id = 1";
        ste = mc.prepareStatement(sql);
        ste.setString(1, tour.getNom());
        ste.setString(2, tour.getNomEquipe());
        ste.setInt(3, tour.getNombreParticipants());
        ste.setInt(4, tour.getDuree());
        ste.setString(5, tour.getTypeJeu());
        ste.setInt(6, tour.getFraisParticipation());
        ste.setString(7, tour.getLocation());
        ste.executeUpdate();

    }

    @Override
    public Tournoi findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Tournoi> readAll() throws SQLException {
        return null;
    }
*/

}