package Service;
import model.Equipement;

import java.sql.SQLException;
import java.util.List;

public interface IserviceEq {
    void ajouter(Equipement equipement) throws SQLException;



    void delete(int id) throws SQLException;

    void update(Equipement equipement , int id ) throws SQLException;


    Equipement findById(int id) throws SQLException;

    List<Equipement> readAll() throws SQLException;

}