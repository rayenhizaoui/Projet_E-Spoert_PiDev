package Service;
import Entites.Recompense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.DataSource;

public class ServiceR {

    Connection con ;

    public ServiceR() throws SQLException {
        con = DataSource.getInstance().getCon();
    }


    public void ajouter(Recompense recompense) throws SQLException {
        String req = "INSERT INTO recompense (equipeGagnante, montantRecompense, dateRecompense) VALUES (?, ?, ?)";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, recompense.getEquipeGagnante());
        pre.setInt(2, recompense.getMontantRecompense());
        pre.setDate(3, new java.sql.Date(recompense.getDateRecompense().getTime())); // Utilisation de java.sql.Date pour les dates SQL
        pre.executeUpdate();
    }








    public void delete(Recompense recompense) throws SQLException {

    }


    public void delete(int id) throws SQLException {
        String req = "DELETE FROM Recompense WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);

        statement.executeUpdate();


    }




    public void update(Recompense recompense ,int id) throws SQLException {
        String req = "UPDATE recompense SET equipeGagnante=?, montantRecompense=?, dateRecompense=? WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);
        statement.setString(1, recompense.getEquipeGagnante());
        statement.setInt(2, recompense.getMontantRecompense());
        statement.setDate(3, (Date) recompense.getDateRecompense());

        statement.executeUpdate();
    }



    public Recompense findById(int id) throws SQLException {
        String req = "SELECT * FROM recompense WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {

            Recompense recompense = new Recompense();
            recompense.setEquipeGagnante(res.getString("equipeGagnante"));
            recompense.setMontantRecompense(res.getInt("montantRecompense"));
            recompense.setDateRecompense(res.getDate("dateRecompense"));
            return recompense;
        }
            return null;
        }


        public List<Recompense> readAll () throws SQLException {
            List<Recompense> list = new ArrayList<>();
            String req = "SELECT * FROM recompense";
            PreparedStatement statement = con.prepareStatement(req);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                Recompense recompense = new Recompense();
                recompense.setId(res.getInt("id"));
                recompense.setEquipeGagnante(res.getString("equipeGagnante"));
                recompense.setMontantRecompense(res.getInt("montantRecompense"));
                recompense.setDateRecompense(res.getDate("dateRecompense"));
                list.add(recompense);
            }
            return list;
        }


}