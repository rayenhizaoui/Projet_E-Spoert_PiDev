package TestConn;
import java.sql.*;
public class Test {

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

      String req="INSERT INTO `personne` ( `nom`, `prenom`, `age`) VALUES ( 'ben fadhel', 'ons', '20');";

      try {
          ste.executeUpdate(req);
          System.out.println("élément insérer");
      }catch (SQLException e)
      {
          System.out.println(e);
      }

      try {
          ResultSet res= ste.executeQuery("select * from personne");

          while (res.next()){

              int id=res.getInt(1);
              String nom=res.getString("nom");
              String prenom=res.getString(3);
              int age=res.getInt("age");

              System.out.println("id :"+id+" nom :"+nom+" prenom :"+prenom+" age :"+age);
          }
      }catch (SQLException e)
      {
          System.out.println(e);
      }



    }
}
