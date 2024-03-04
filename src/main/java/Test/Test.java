package Test;

import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.sql.*;

public class Test {
    private static String url="jdbc:mysql://localhost:3306/esprit";
    private static String user="root";
    private static String pwd="";
    private static Connection con;
    private static Statement ste;
    public static void main(String[] args) throws SQLException {
        try {
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("connection établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            ste = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }
        String req = "INSERT INTO `joueur` (`cin`, `nom`, `prenom`, `genre`,`tel`,`email`) VALUES (NULL, 'mnasri', 'anas', 'masculin','99385385','anes.mnasri@esprit.tn')";
        try {
            ste.executeUpdate(req);
            System.out.println("élément inséré");
        } catch (SQLException e) {
            System.out.println(e);
        }

        String nom;
        try {
            ResultSet res = ste.executeQuery("SELECT * FROM joueur");

            while (res.next()) {
                int cin = res.getInt(1);
                nom = res.getString(2);
                String prenom = res.getString(3);
                String genre = res.getString(4);
                int tel = res.getInt(5);
                String email = res.getString(6);
                System.out.println("cin=" + cin + "nom=" + nom + "prenom=" + prenom + "genre=" + genre +"tel=" + tel + "email=" +email);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
    }

