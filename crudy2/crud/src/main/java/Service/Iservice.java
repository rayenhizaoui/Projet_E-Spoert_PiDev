package Service;

import model.Reservation;

import java.sql.SQLException;
import java.util.List;
public interface Iservice {




        void ajouter(Reservation reservation) throws SQLException;

        void delete(int id) throws SQLException;

        void update(Reservation reservation , int id) throws SQLException;

        Reservation findById(int id) throws SQLException;

        List<Reservation> readAll() throws SQLException;
    }

