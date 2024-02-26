package Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.Connexion;
import model.Equipement;


public class ServiceEq implements IserviceEq {
    private Connexion con = Connexion.getInstance();
    private Statement ste = con.createStatement();

    public ServiceEq() throws SQLException {


    }

    @Override
    public void ajouter(Equipement equipement) throws SQLException {
        String req = "INSERT INTO equipement ( nom, nombre ) VALUES(?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, equipement.getNom());
        pre.setInt(2, equipement.getNombre());
        pre.executeUpdate();
    }



    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM equipement WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);

        statement.executeUpdate();


    }




    @Override
    public void update(Equipement equipement , int id) throws SQLException {
        String req = "UPDATE equipement SET  nom = ?, nombre = ? WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);
        statement.setString(1, equipement.getNom());
        statement.setInt(2, equipement.getNombre());
        statement.executeUpdate();

    }

    @Override
    public Equipement findById(int id) throws SQLException {
        String req = "SELECT * FROM equipement WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            Equipement equipement = new Equipement();
            equipement.setId(res.getInt("id"));
            equipement.setNom(res.getString("nom"));
            equipement.setNombre(res.getInt("nombre"));
           return equipement;
        }
        return null;
    }

    @Override
    public List<Equipement> readAll() throws SQLException {
        List<Equipement> list = new ArrayList<>();
        String req = "SELECT * FROM equipement";
        PreparedStatement statement = con.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            Equipement equipement = new Equipement();
            equipement.setId(res.getInt("id"));
            equipement.setNom(res.getString("nom"));
            equipement.setNombre(res.getInt("nombre"));

            list.add(equipement);
        }
        return null;
    }
}
