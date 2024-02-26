package Service;
import model.Tournoi;
import java.sql.SQLException;
import java.util.List;
public interface Iservice<T> {




        void ajouter(Tournoi tournoi) throws SQLException;

        void delete(int id) throws SQLException;

        void update(Tournoi tournoi , int Id) throws SQLException;

        Tournoi findById(int id) throws SQLException;

        List<Tournoi> readAll() throws SQLException;
    }

