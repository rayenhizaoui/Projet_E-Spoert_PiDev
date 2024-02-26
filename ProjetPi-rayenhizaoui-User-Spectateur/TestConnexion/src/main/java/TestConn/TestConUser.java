package TestConn;

import java.sql.*;

public class TestConUser {


    private  static String url = "jdbc:mysql://localhost:3306/esprit";
    private static String user="root";
    private  static String pwd="";
    private static Connection con;


    private static Statement ste;

    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("connexion établie");
        }catch (SQLException e)
        {
            System.out.println(e);
        }
        try {
            ste=con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }

        //String req="INSERT INTO `personne` ( `nom`, `prenom`, `age`) VALUES ( 'ben fadhel', 'ons', '20');";

        String req="INSERT INTO `user` (`id`, `cin`, `username`, `numero`, `email`, `adresse`, `password`, `roles`) VALUES ('2', '2', 'ray', '123', 'rayen.hizaoui@esprit.tn', 'bardo', '123', 'admin');";

        try {
            ste.executeUpdate(req);
            System.out.println("élément insérer");
        }catch (SQLException e)
        {
            System.out.println(e);
        }

        try {
            ResultSet res= ste.executeQuery("select * from user");

            while (res.next()){

                int id=res.getInt(1);
                int cin=res.getInt(2);
                String username=res.getString("nom");
                int numero=res.getInt(3);
                String email=res.getString("email");
                String adresse=res.getString("adresse");
                String password=res.getString("password");
                String roles=res.getString("roles");

                System.out.println("id :"+id+" CIN :"+cin+" UserName :"+username+" Numero :"+numero+"Email :"+email+" Adresse :"+adresse+" Password :"+password+" roles :"+roles);
            }
        }catch (SQLException e)
        {
            System.out.println(e);
        }



    }
}
