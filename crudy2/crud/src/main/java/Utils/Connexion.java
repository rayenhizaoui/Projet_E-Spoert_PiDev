package Utils;

import java.sql.*;


public class Connexion {


        private static Connexion instance;
        private Connection cnx;

        private final String URL = "jdbc:mysql://localhost:3306/esport";
        private final String LOGIN = "root";
        private final String PASSWORD = "";
  // constructeur de connexion
        private Connexion() {
            try {
                cnx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
                System.out.println("Conncting !");
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
            }
        }
// avec singelton seul instance de connexion
        public static Connexion getInstance() {
            if (instance == null) {
                instance = new Connexion();
            }
            return instance;
        }
    public Statement createStatement() throws SQLException {
        return cnx.createStatement();
    }

    public Connection getCon() {
        return cnx;
    }

    public PreparedStatement prepareStatement(String req) throws SQLException {
        return cnx.prepareStatement(req);
    }
    }
