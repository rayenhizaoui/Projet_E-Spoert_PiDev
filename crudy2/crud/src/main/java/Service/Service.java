package Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.Connexion;
import model.Reservation;
import java.sql.Date;
public class Service  implements Iservice {

    private Connexion con = Connexion.getInstance();
    private Statement ste = con.createStatement();

    public Service() throws SQLException {
    }

    @Override
    public void ajouter(Reservation reservation) throws SQLException {
        String req = "INSERT INTO reservation ( nom, datedebutres, datefinres, type,deposit)VALUES(?,?,?,?,?)";
        PreparedStatement pre = con.prepareStatement(req);
        pre.setString(1, reservation.getNom());
        pre.setDate(2, new java.sql.Date(reservation.getDatedebutres().getTime()));
        pre.setDate(3, new java.sql.Date(reservation.getDatefinres().getTime()));
        pre.setString(4, reservation.getType());
        pre.setFloat(5, reservation.getDeposit());




        pre.executeUpdate();

    }

    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM reservation WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);//requete précompilée

        statement.executeUpdate();// exécuter des requêtes SQL qui modifient les données dans la base de données


    }


    @Override
    public void update(Reservation reservation , int id ) throws SQLException {
        String req = "UPDATE reservation SET nom = ?, datedebutres = ?, datefinres = ?, type = ?, deposit = ? WHERE id ="+id;
        PreparedStatement statement = con.prepareStatement(req);


        statement.setString(1, reservation.getNom());
        statement.setDate(2, new java.sql.Date(reservation.getDatedebutres().getTime()));
        statement.setDate(3, new java.sql.Date(reservation.getDatefinres().getTime()));
        statement.setString(4, reservation.getType());
        statement.setFloat(5, reservation.getDeposit());



        statement.executeUpdate();
    }

    @Override
    public Reservation findById(int id) throws SQLException {
        String req = "SELECT * FROM reservation WHERE id = ?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();//stocker dans resultat apres exécution
        if (res.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(res.getInt("id"));
            reservation.setNom(res.getString("nom"));
            reservation.setDatedebutres(res.getDate("date debut res"));
            reservation.setDatefinres(res.getDate("date fin res"));
            reservation.setType(res.getString("type"));
            reservation.setDeposit(Float.valueOf(res.getString("deposit")));
            return reservation;
        }
        return null;
    }

    @Override
    public List<Reservation> readAll() throws SQLException {
        List<Reservation> list = new ArrayList<>();
        String req = "SELECT * FROM reservation";
        PreparedStatement statement = con.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            Reservation reservation = new Reservation();
            reservation.setId(res.getInt("id"));
            reservation.setNom(res.getString("nom"));
            reservation.setDatedebutres(res.getDate("date debut res"));
            reservation.setDatefinres(res.getDate("date fin res"));
            reservation.setType(res.getString("type"));
            reservation.setDeposit(Float.valueOf(res.getString("deposit")));

            list.add(reservation);
        }
        return list;
    }

}