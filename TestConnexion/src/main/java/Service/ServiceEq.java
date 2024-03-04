package Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
//import Utils.Connexion;
import Utils.DataSource;
import Entites.Equipement;


public class ServiceEq  {
    private Connection con= DataSource.getInstance().getCon();
    private Statement ste;

    public ServiceEq() throws SQLException {


    }


    public void ajouter(Equipement equipement) throws SQLException {
        String req = "INSERT INTO equipement ( nom, nombre , image ,rating , QrCode) VALUES(?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, equipement.getNom());
        pre.setInt(2, equipement.getNombre());
        pre.setString(3, equipement.getImage());
        pre.setString(4, "0");
        pre.setString(5, equipement.getQrCode());

        pre.executeUpdate();
    }


    public void delete(int id) throws SQLException {
        String req = "DELETE FROM equipement WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);
        statement.executeUpdate();
    }





    public void update(Equipement equipement , int id) throws SQLException {
        String req = "UPDATE equipement SET  nom = ?, nombre = ? , image = ? , rating=?  , QrCode=? WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);
        statement.setString(1, equipement.getNom());
        statement.setInt(2, equipement.getNombre());
        statement.setString(3, equipement.getImage());
        statement.setDouble(4, equipement.getRating());
        statement.setString(5, equipement.getQrCode());

        statement.executeUpdate();

    }


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
            equipement.setImage(res.getString("image"));
            equipement.setRating(res.getDouble("rating"));
            equipement.setQrCode(res.getString("QrCode"));

            return equipement;
        }
        return null;
    }


    public List<Equipement>     readAll() throws SQLException {
        List<Equipement> list = new ArrayList<>();
        String req = "SELECT * FROM equipement";
        PreparedStatement statement = con.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            Equipement equipement = new Equipement();
            equipement.setId(res.getInt("id"));
            equipement.setNom(res.getString("nom"));
            equipement.setNombre(res.getInt("nombre"));
            equipement.setImage(res.getString("image"));
            equipement.setRating(res.getDouble("rating"));
            equipement.setQrCode(res.getString("QrCode"));

            list.add(equipement);
        }
        return list; // Return the list here, not null
    }


}
