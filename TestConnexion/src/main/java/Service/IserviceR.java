package Service;
import Entites.Recompense;
import java.sql.SQLException;
import java.util.List;
public interface IserviceR {




        void ajouter( Recompense recompense) throws SQLException;

        void delete(Recompense recompense) throws SQLException;

    void delete(int id) throws SQLException;

    void update(Recompense recompense,int id) throws SQLException;

        Recompense findById(int id) throws SQLException;

     List<Recompense> readAll() throws  SQLException;
}

