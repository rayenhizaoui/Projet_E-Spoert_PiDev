package Service;

import Entites.Local;
import java.sql.SQLException;
import java.util.List;

public interface Iservice_l<L> {




    void ajouterl(Local local) throws SQLException;

    void deletel(int  id) throws SQLException;

    void updatel(Local local, int Id) throws SQLException;

    Local findByIdl(int id) throws SQLException;

    List<Local> readAll() throws SQLException;
}

