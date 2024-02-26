/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Service;


        import Entites.User;

        import java.sql.Connection;
        import java.sql.PreparedStatement;
        import java.sql.ResultSet;
        import java.sql.SQLException;
        import java.sql.Statement;
        import java.util.ArrayList;
        import java.util.List;


        import Entites.UserRole;
        import Utils.DataSource;

    public class ServiceUserCrud implements IService<User> {

    private static Connection con=DataSource.getInstance().getCon();
    private Statement ste;


    public ServiceUserCrud() {
        try {
            ste= con.createStatement();
        }catch (SQLException e)
        {
            System.out.println(e);
        }
    }




        public void ajouter(User u1) throws SQLException {
            String req = "INSERT INTO user (cin, username, numero, email, adresse, password, roles) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pre = con.prepareStatement(req);
            pre.setInt(1, u1.getCin());
            pre.setString(2, u1.getUsername());
            pre.setInt(3, u1.getNumero());
            pre.setString(4, u1.getEmail());
            pre.setString(5, u1.getAdresse());
            pre.setString(6, u1.getPassword());
            pre.setString(7, u1.getRole().name());
            pre.executeUpdate();
        }





        @Override
        public void supprimerUtilisateur(User user) {
            try {
                // Préparer la requête SQL pour supprimer l'utilisateur par son ID
                String requete = "DELETE FROM user WHERE id=?";
                PreparedStatement pst = con.prepareStatement(requete);
                pst.setInt(1, user.getId());
                // Exécuter la requête de suppression
                int rowsDeleted = pst.executeUpdate();

                if (rowsDeleted > 0) {
                    System.out.println("Utilisateur supprimé avec succès.");
                } else {
                    System.out.println("Échec de la suppression de l'utilisateur.");
                }
            } catch (SQLException ex) {
                System.out.println("Erreur lors de la suppression de l'utilisateur : " + ex.getMessage());
            }
        }







    @Override
    public void modifierUtilisateur(User user) {
        try {
            String requete2="update user set cin=?,username=?,numero=?,email=?,adresse=?,password=?,roles=? where id=?";
            PreparedStatement pst = con.prepareStatement(requete2);
            pst.setInt(1, user.getCin());
            pst.setString(2, user.getUsername());
            pst.setInt(3, user.getNumero());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getAdresse());
            pst.setString(6, user.getPassword());
            pst.setString(7, user.getRole().name());
            pst.setInt(8, user.getId());

            pst.executeUpdate();

            System.out.println("Utlisateur est modifié");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }}




    @Override
    public User findById(int id) throws SQLException {
        return null;
    }








/*
        public List<User> readAll() throws SQLException {
            List<User> list = new ArrayList<>();
            ResultSet res = ste.executeQuery("SELECT * FROM `user`");
            while (res.next()) {
                int id = res.getInt(1);
                int cin = res.getInt(2);
                String username = res.getString("username");
                int numero = res.getInt(4);
                String email = res.getString("email");
                String adresse = res.getString("adresse");
                String password = res.getString("password");
                String roles = res.getString("roles");


                // Convertir la chaîne de rôle en enum UserRole
                UserRole role = UserRole.valueOf(roles);

                User u1 = new User(id, cin, username, numero, email, adresse, password, role);
                list.add(u1);
            }
            return list;
        }
*/

        public List<User> readAll() throws SQLException {
            List<User> list = new ArrayList<>();
            ResultSet res = ste.executeQuery("SELECT * FROM `user`");
            while (res.next()) {
                int id = res.getInt(1);
                int cin = res.getInt(2);
                String username = res.getString("username");
                int numero = res.getInt(4);
                String email = res.getString("email");
                String adresse = res.getString("adresse");
                String password = res.getString("password");
                String roles = res.getString("roles");

                //User.UserRole role = User.UserRole.valueOf(res.getString("roles"));

                // Convertir la chaîne de rôle en enum UserRole
                UserRole role = UserRole.valueOf(roles);




                User u1 = new User(id, cin, username, numero, email, adresse, password, role);
                list.add(u1);
            }
            return list;
        }




/*
        public static boolean authentifierUtilisateur(String username, String password) throws SQLException {
            String req = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si une ligne est renvoyée par la requête, cela signifie que les informations d'identification sont correctes
            return resultSet.next();
        }
*/






        public static User authentifierUtilisateur(String username, String password) throws SQLException {
            String req = "SELECT * FROM user WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = con.prepareStatement(req);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si une ligne est renvoyée par la requête, cela signifie que les informations d'identification sont correctes
            // Créez un objet User avec les données de la base de données et retournez-le
            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                int cin = resultSet.getInt("cin");
                String userEmail = resultSet.getString("email");
                String userAdresse = resultSet.getString("adresse");
                String userPassword = resultSet.getString("password");
                String userRole = resultSet.getString("roles");

                // Convertir la chaîne de rôle en enum UserRole
                UserRole role = UserRole.valueOf(userRole);

                return new User(id, cin, username, 0, userEmail, userAdresse, userPassword, role);
            } else {
                return null; // Retourner null si l'utilisateur n'est pas trouvé
            }
        }




    }