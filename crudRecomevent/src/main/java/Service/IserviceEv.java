package Service;

import model.Evenement;

import java.sql.SQLException;
import java.util.List;

public interface IserviceEv {




        void ajouter(Evenement evenement) throws SQLException;

        void delete(Evenement evenement) throws SQLException;

        void delete(int id) throws SQLException;

        void update(Evenement evenement,int id) throws SQLException;

      Evenement findById(int id) throws SQLException;

        List<Evenement> readAll() throws  SQLException;
    }


