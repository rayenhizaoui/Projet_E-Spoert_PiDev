package Service;
import Utils.Connexion;
import model.Tournoi;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class Service_t implements Iservice<Tournoi> {

    Connection mc;
    PreparedStatement ste;
    ResultSet resultSet = null;


    public Service_t() throws SQLException {
        mc = Connexion.getInstance().getCnx();
    }

    @Override
    public void ajouter(Tournoi tour) throws SQLException {
        String sql = "INSERT INTO `tournoi`(`nom`,`nomEquipe`,`location`,`nombreParticipants`,`duree`,`typeJeu`,`fraisParticipation`) VALUES(?,?,?,?,?,?,?)";
        //PreparedStatement pre = con.prepareStatement(req);
        ste=mc.prepareStatement(sql);
        System.out.println(ste);
        ste.setString(1, tour.getNom());
        ste.setInt(4, tour.getNombreParticipants());
        ste.setString(3, tour.getLocation());
        ste.setString(2, tour.getNomEquipe());
        ste.setInt(5, tour.getDuree());
        ste.setString(6, tour.getTypeJeu());
        ste.setInt(7, tour.getFraisParticipation());
     //   ste.setString(8, tour.getImage());
        System.out.println(ste);
        ste.executeUpdate();
        System.out.println("done");

    }
    @Override
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM tournoi WHERE id ="+id;
        ste = mc.prepareStatement(sql);
        ste.executeUpdate();

    }
    @Override
    public void update(Tournoi tour , int Id) throws SQLException {

        String sql = "UPDATE tournoi SET nom = ?, nomEquipe = ?, nombreParticipants = ?, duree = ?, typeJeu = ?, fraisParticipation = ?, location = ? WHERE id ="+Id;
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


    public List<Tournoi> DisplayEvent() {
        List<Tournoi> Tournoi_list = new ArrayList();
        mc = Connexion.getInstance().getCnx();
        String sql="SELECT * from tournoi";
        try {
            ste=mc.prepareStatement(sql);
            ResultSet rs = ste.executeQuery();
            System.out.println(rs.toString());
            while (rs.next()){
                Tournoi_list.add(new Tournoi(rs.getInt("id"),rs.getString("nom"), rs.getString("nomEquipe"), rs.getInt("nombreParticipants"), rs.getInt("duree"), rs.getString("typeJeu"), rs.getInt("fraisParticipation"),rs.getString("location")) );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Tournoi_list;

    }
}