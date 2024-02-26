package Service;

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


public class ServiceSpectateurCrud  {




    private Connection con= DataSource.getInstance().getCon();
    private Statement ste;


    public ServiceSpectateurCrud() {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }


    public void ajouter(Spectateur s1) throws SQLException {
    String req="INSERT INTO `spectateur` (`id`, `nom`, `prenom`, `age`) VALUES ('"+s1.getId()+"', '"+s1.getNom()+"', '"+s1.getPrenom()+"','"+s1.getAge()+"');";
        ste.executeUpdate(req);
    }
    public void ajouterPST(Spectateur spectateur) throws SQLException {

        String req="INSERT INTO `personne` ( `id`, `nom`, `prenom`, `age`) VALUES ( ?,?,?,?,);";
        PreparedStatement pre=con.prepareStatement(req);

        pre.setInt(1,spectateur.getId());
        pre.setString(2,spectateur.getNom());
        pre.setString(3,spectateur.getPrenom());
        pre.setInt(4,spectateur.getAge());


        pre.executeUpdate();
    }







    public void supprimerSpectateur(Spectateur spectateur) {
        try {
            String requete="delete from spectateur where id=?";
            PreparedStatement pst = con.prepareStatement(requete);
            pst.setInt(1,spectateur.getId());
            pst.executeUpdate();

            System.out.println("Utlisateur est supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}

    public void modifierSpectateur(Spectateur spectateur) {
        try {
            String requete2="update spectateur set nom=?,prenom=?,age=? where id=?";
            PreparedStatement pst = con.prepareStatement(requete2);
            pst.setString(1, spectateur.getNom());
            pst.setString(2, spectateur.getPrenom());
            pst.setInt(3, spectateur.getAge());
            pst.setInt(4, spectateur.getId());
            pst.executeUpdate();

            System.out.println("Spectateur est modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}







    public Spectateur findById(int id) throws SQLException {
        return null;
    }

    public List<Spectateur> readAll() throws SQLException {

        List<Spectateur> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("SELECT * FROM `spectateur`");
        while (res.next()) {

            int id=res.getInt(1);
            String nom=res.getString("nom");
            String prenom=res.getString("prenom");
            int age=res.getInt("age");
            Spectateur s1=new Spectateur(id,nom,prenom,age);
            list.add(s1);
        }

        return list;
    }


    }

    












