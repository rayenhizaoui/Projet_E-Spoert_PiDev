package Service;

import Entites.Personne;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import Entites.User;
import Utils.DataSource;
public class ServicePersonne implements IService<Personne>{

    private Connection con=DataSource.getInstance().getCon();

    private Statement ste;

    public ServicePersonne()
    {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }


    }
    @Override
    public void ajouter(Personne personne) throws SQLException {

        String req="INSERT INTO `personne` ( `nom`, `prenom`, `age`) VALUES ( '"+personne.getNom()+"', '"+personne.getPrenom()+"', '"+personne.getAge()+"');";
    ste.executeUpdate(req);
    }
    public void ajouterPST(Personne personne) throws SQLException {

        String req="INSERT INTO `personne` ( `nom`, `prenom`, `age`) VALUES ( ?,?,?);";
       PreparedStatement pre=con.prepareStatement(req);

       pre.setString(1,personne.getNom());
       pre.setString(2,personne.getPrenom());
       pre.setInt(3,personne.getAge());

        pre.executeUpdate();
    }



    @Override
    public Personne findById(int id) throws SQLException {
        return null;
    }

    @Override
    public List<Personne> readAll() throws SQLException {

        List<Personne> list=new ArrayList<>();
        ResultSet res=ste.executeQuery("select * from personne");
        while (res.next()) {

            int id = res.getInt(1);
            String nom = res.getString("nom");
            String prenom = res.getString(3);
            int age = res.getInt("age");
            Personne p1=new Personne(id,nom,prenom,age);
            list.add(p1);
        }

        return list;
    }

    @Override
    public void supprimerUtilisateur(User user) {

    }

    @Override
    public void modifierUtilisateur(User user) {

    }
}
