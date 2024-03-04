package Service;
import model.Evenement;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Utils.Connexion;
import model.Historique;
import model.Recompense;

public class ServiceEv implements IserviceEv {
    Connection con ;

    public ServiceEv() throws SQLException {
        con = Connexion.getInstance().getCon();
    }



    @Override
    public void ajouter(Evenement evenement) throws SQLException {
        // Assuming Recompense is already added in the database and has an ID
        String req = "INSERT INTO Evenement (nomEvent, lieu, dateEvent, duree, id_recompense , QrCode) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, evenement.getNomEvent());
        pre.setString(2, evenement.getLieu());
        pre.setDate(3, new java.sql.Date(evenement.getDateEvent().getTime()));
        pre.setInt(4, evenement.getDuree());
        pre.setInt(5, evenement.getRecompense().getId()); // Set the foreign key to Recompense
        pre.setString(6, evenement.getQrCode());
        logHistory("Ajouter", "Added new event with name: " + evenement.getNomEvent());

        pre.executeUpdate();
    }

    @Override
    public List<Evenement> readAll() throws SQLException {
        List<Evenement> list = new ArrayList<>();
        String req = "SELECT e.*, r.* FROM Evenement e LEFT JOIN Recompense r ON e.id_recompense = r.id";
        PreparedStatement statement = con.prepareStatement(req);
        ResultSet res = statement.executeQuery();
        while (res.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(res.getInt("e.id"));
            evenement.setNomEvent(res.getString("e.nomEvent"));
            evenement.setLieu(res.getString("e.lieu"));
            evenement.setDateEvent(res.getDate("e.dateEvent"));
            evenement.setDuree(res.getInt("e.duree"));
            evenement.setQrCode(res.getString("e.QrCode"));
            // Create and set Recompense object if present
            int recompenseId = res.getInt("r.id");
            if (!res.wasNull()) {
                Recompense recompense = new Recompense(
                        recompenseId,
                        res.getString("r.equipeGagnante"),
                        res.getInt("r.montantRecompense"),
                        res.getDate("r.dateRecompense")
                );
                evenement.setRecompense(recompense);
            }

            list.add(evenement);
        }
        return list;
    }

    @Override
    public Evenement findById(int id) throws SQLException {
        String req = "SELECT e.*, r.* FROM Evenement e LEFT JOIN Recompense r ON e.id_recompense = r.id WHERE e.id = ?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setInt(1, id);
        ResultSet res = statement.executeQuery();
        if (res.next()) {
            Evenement evenement = new Evenement();
            evenement.setId(res.getInt("e.id"));
            evenement.setNomEvent(res.getString("e.nomEvent"));
            evenement.setLieu(res.getString("e.lieu"));
            evenement.setDateEvent(res.getDate("e.dateEvent"));
            evenement.setDuree(res.getInt("e.duree"));
            evenement.setQrCode(res.getString("e.QrCode"));
            // Check if a Recompense record exists
            int recompenseId = res.getInt("r.id");
            System.out.println(recompenseId);
            if (!res.wasNull()) {
                Recompense recompense = new Recompense(
                        recompenseId,
                        res.getString("r.equipeGagnante"),
                        res.getInt("r.montantRecompense"),
                        res.getDate("r.dateRecompense")

                );
                evenement.setRecompense(recompense);
            }

            return evenement;
        }
        return null;
    }



    public void update(Evenement evenement, int id) throws SQLException {
        // Verify the recompense ID exists
        if (evenement.getRecompense() != null) {
            String checkReq = "SELECT COUNT(*) FROM recompense WHERE id = ?";
            PreparedStatement checkStmt = con.prepareStatement(checkReq);
            checkStmt.setInt(1, evenement.getRecompense().getId());
            ResultSet rsCheck = checkStmt.executeQuery();
            if (rsCheck.next() && rsCheck.getInt(1) == 0) {
                // The recompense ID does not exist, handle this scenario appropriately
                throw new SQLException("Recompense ID does not exist: " + evenement.getRecompense().getId());
            }
        }

        // Proceed with the update
        String req = "UPDATE Evenement SET nomEvent=?, lieu=?, dateEvent=?, duree=?, id_recompense=?  WHERE id=?";
        PreparedStatement statement = con.prepareStatement(req);
        statement.setString(1, evenement.getNomEvent());
        statement.setString(2, evenement.getLieu());
        statement.setDate(3, new java.sql.Date(evenement.getDateEvent().getTime()));
        statement.setInt(4, evenement.getDuree());

        if (evenement.getRecompense() != null) {
            statement.setInt(5, evenement.getRecompense().getId());
        } else {
            statement.setNull(5, Types.INTEGER); // Set null if there is no Recompense
        }

        statement.setInt(6, id);

        logHistory("Update", "Updated event with ID: " + id);


        statement.executeUpdate();
    }


    @Override
    public void delete(int id) throws SQLException {
        String req = "DELETE FROM Evenement WHERE id =" + id;
        PreparedStatement statement = con.prepareStatement(req);
        logHistory("Delete", "Deleted event with ID: " + id);

        statement.executeUpdate();


    }
    private void logHistory(String operationType, String description) throws SQLException {
        String req = "INSERT INTO historique (operation_type, description) VALUES (?, ?)";
        PreparedStatement pre = con.prepareStatement(req);

        pre.setString(1, operationType);
        pre.setString(2, description);
        pre.executeUpdate();
    }
    public List<Historique> fetchHistorique() throws SQLException {
        List<Historique> historiqueList = new ArrayList<>();
        String req = "SELECT * FROM historique";
        PreparedStatement pre = con.prepareStatement(req);
        ResultSet rs = pre.executeQuery();

        while (rs.next()) {
            Historique historique = new Historique(
                    rs.getInt("id"),
                    rs.getString("operation_type"),
                    rs.getString("description"),
                    rs.getTimestamp("operation_date") // Assuming the date is stored as TIMESTAMP in SQL
            );
            historiqueList.add(historique);
        }
System.out.println(historiqueList);
        return historiqueList;
    }
    @Override
    public void delete(Evenement evenement) throws SQLException {


    }
}










