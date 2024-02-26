package Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Connexion {
    private static Connexion instance;
    private Connection cnx;

    private final String URL = "jdbc:mysql://localhost:3306/esport";
    private final String LOGIN = "root";
    private final String PASSWORD = "";

    private Connexion() {
        try {
            cnx = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Conncting !");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static Connexion getInstance() {
        if (instance == null) {
            instance = new Connexion();
        }
        return instance;
    }

    public Connection getCnx() {
        return cnx;
    }
}
