package Service;

import Entites.Billet;
import Entites.Spectateur;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import Entites.Spectateur;
import Entites.User;
import Utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;




public class ServiceBillet {


    private Connection con= DataSource.getInstance().getCon();
    private Statement ste;


    public ServiceBillet() {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }


    public void ajouter(Billet b1) throws SQLException {
        String req="INSERT INTO `billet` (`id`, `cin`, `type`, `email`,`place`) VALUES ('"+b1.getId()+"', '"+b1.getCin()+"', '"+b1.getType()+"','"+b1.getEmail()+"','"+b1.getPlace()+"');";
        ste.executeUpdate(req);
    }
    public void ajouterPST(Billet billet) throws SQLException {

        String req="INSERT INTO `billet` ( `id`, `cin`, `type`, `email`,`place`) VALUES ( ?,?,?,?,);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setInt(1,billet.getId());
        pre.setInt(2,billet.getCin());
        pre.setString(3,billet.getType());
        pre.setString(4,billet.getEmail());
        pre.setString(5,billet.getPlace());

        pre.executeUpdate();
    }







    public void supprimerBillet(Billet billet) {
        try {
            String requete="delete from billet where id=?";
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1,billet.getId());
            pst.executeUpdate();

            System.out.println("Billet est supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}

    public void modifierBillet(Billet billet) {
        try {
            String requete2="update billet set cin=?,type=?,email=?,place=? where id=?";
            PreparedStatement pst = con.prepareStatement(requete2);
            pst.setInt(1, billet.getCin());
            pst.setString(2, billet.getType());
            pst.setString(3, billet.getEmail());
            pst.setString(4, billet.getPlace());
            pst.setInt(5, billet.getId());
            pst.executeUpdate();

            System.out.println("Billet est modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}







    public Billet findById(int id) throws SQLException {
        return null;
    }

    public List<Billet> readAll() throws SQLException {

        List<Billet> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("SELECT * FROM `billet`");
        while (res.next()) {

            int id=res.getInt(1);
            int cin=res.getInt("cin");
            String type=res.getString("type");
            String email=res.getString("email");
            String place=res.getString("place");
            Billet b1=new Billet(id,cin,type,email,place);
            list.add(b1);
        }

        return list;
    }


}


















