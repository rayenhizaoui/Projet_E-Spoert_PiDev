package TestCon;

import java.sql.*;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {

    private static String url = "jdbc:mysql://localhost:3306/esprit";
    private static String user = "root";
    private static String pwd = "";
    private static Connection con;
    private static Statement ste;
    public static void main(String[] args) {
        try {
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("connexion etablie");
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {


            ste = con.createStatement();

        } catch (SQLException e) {

            System.out.println(e);
        }


        //  ste.executeupdate(req);
        // System.out.println(e);
        String req = "INSERT INTO `personne` (`id`, `nom`, `prenom`, `age`) VALUES (NULL, 'maisssa', 'bs', '23');";
        try {
            ste.executeUpdate(req);
            System.out.println("element inséré");
        } catch (SQLException e) {
            System.out.println((e));
        }
        try {
            ResultSet res = ste.executeQuery("select * from personne");
            while (res.next()) {
                int id = res.getInt(1);
                String nom = res.getString("nom");
                String prenom = res.getString("prenom");
                int age = res.getInt("age");
                System.out.println("id:" + id + "nom" + nom + "prenom" + prenom + "age" + age);
            }
        } catch (SQLException e) {
            System.out.println(e);

        }


    }}



