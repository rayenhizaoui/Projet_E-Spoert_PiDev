    package Service;
    import java.sql.*;
    import java.util.ArrayList;
    import java.util.List;
    import Utils.Connexion;
    import model.Equipement;
    import model.Reservation;
    import java.sql.Date;
    public class Service  implements Iservice {

        private Connexion con = Connexion.getInstance();
        private Statement ste = con.createStatement();

        public Service() throws SQLException {
        }

        @Override
        public void ajouter(Reservation reservation) throws SQLException {
            String req = "INSERT INTO reservation (nom, datedebutres, datefinres, type, deposit, id_equipement) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setString(1, reservation.getNom());
            pre.setDate(2, new java.sql.Date(reservation.getDatedebutres().getTime()));
            pre.setDate(3, new java.sql.Date(reservation.getDatefinres().getTime()));
            pre.setString(4, reservation.getType());
            pre.setFloat(5, reservation.getDeposit());
            pre.setInt(6, reservation.getEquipement().getId()); // Assuming Equipement has an ID field

            pre.executeUpdate();
        }


        @Override
        public void delete(int id) throws SQLException {
            String req = "DELETE FROM reservation WHERE id ="+id;
            PreparedStatement statement = con.prepareStatement(req);//requete précompilée

            statement.executeUpdate();// exécuter des requêtes SQL qui modifient les données dans la base de données


        }


        @Override
        public void update(Reservation reservation, int id) throws SQLException {
            String req = "UPDATE reservation SET nom = ?, datedebutres = ?, datefinres = ?, type = ?, deposit = ?, id_equipement = ? WHERE id = ?";
            PreparedStatement statement = con.prepareStatement(req);

            statement.setString(1, reservation.getNom());
            statement.setDate(2, new java.sql.Date(reservation.getDatedebutres().getTime()));
            statement.setDate(3, new java.sql.Date(reservation.getDatefinres().getTime()));
            statement.setString(4, reservation.getType());
            statement.setFloat(5, reservation.getDeposit());
            statement.setInt(6, reservation.getEquipement().getId()); // Set equipement_id
            statement.setInt(7, id); // Set reservation ID for the WHERE clause

            statement.executeUpdate();
        }


        @Override
        public Reservation findById(int id) throws SQLException {
            String req = "SELECT r.*, e.id as id_equipement, e.nom as nom, ... FROM reservation r JOIN equipement e ON r.id_equipement = e.id WHERE r.id = ?";
            PreparedStatement statement = con.prepareStatement(req);
            statement.setInt(1, id);
            ResultSet res = statement.executeQuery();
            if (res.next()) {
                Reservation reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setNom(res.getString("nom"));
                reservation.setDatedebutres(res.getDate("datedebutres"));
                reservation.setDatefinres(res.getDate("datefinres"));
                reservation.setType(res.getString("type"));
                reservation.setDeposit(res.getFloat("deposit"));

                // Manually extract Equipement data and create an Equipement object
                Equipement equipement = new Equipement();
                equipement.setId(res.getInt("id"));
                equipement.setNom(res.getString("nom"));
                equipement.setNombre(res.getInt("nombre"));
                equipement.setImage(res.getString("image"));
                // Set other Equipement fields as needed

                reservation.setEquipement(equipement);

                return reservation;
            }
            return null;
        }
        @Override
        public List<Reservation> readAll() throws SQLException {
            List<Reservation> list = new ArrayList<>();

            String req = "SELECT r.*, e.id AS id, e.nom AS nom, e.nombre AS nombre, e.image AS image FROM reservation r JOIN equipement e ON r.id_equipement = e.id";
            PreparedStatement statement = con.prepareStatement(req);
            ResultSet res = statement.executeQuery();
            while (res.next()) {
                // Create and populate the Reservation object
                Reservation reservation = new Reservation();
                reservation.setId(res.getInt("id"));
                reservation.setNom(res.getString("nom"));
                reservation.setDatedebutres(res.getDate("datedebutres"));
                reservation.setDatefinres(res.getDate("datefinres"));
                reservation.setType(res.getString("type"));
                reservation.setDeposit(res.getFloat("deposit"));

                // Manually create and populate the Equipement object from the ResultSet
                Equipement equipement = new Equipement();
                equipement.setId(res.getInt("id"));
                equipement.setNom(res.getString("nom"));
                equipement.setNombre(res.getInt("nombre"));
                equipement.setImage(res.getString("image")); // Assuming there's an `image` field

                // Set the Equipement object to the Reservation
                reservation.setEquipement(equipement);

                // Add the populated Reservation object to the list
                list.add(reservation);
            }
            return list;
        }




    }