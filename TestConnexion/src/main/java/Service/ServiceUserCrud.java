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
        import java.util.HashMap;
        import java.util.List;
        import java.util.Map;


        import Entites.UserRole;
        import Utils.DataSource;

    public class ServiceUserCrud  {

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

/*
        public int countUsersByRole(String spectateur) {
            // Initialiser le compteur
            int count = 0;

            try {
                // Récupérer la liste des utilisateurs avec le rôle spécifié
                List<User> users = ser.getUsersByRole(role);

                // Mettre à jour le compteur avec la taille de la liste
                count = users.size();
            } catch (SQLException e) {
                // Gérer les exceptions SQL
                e.printStackTrace();
            }

            // Retourner le nombre d'utilisateurs pour le rôle spécifié
            return count;
        }
*/



        public int countUsersByRole(String role) {
            // Initialiser le compteur
            int count = 0;

            try {
                // Récupérer la liste des utilisateurs avec le rôle spécifié
                List<User> users = readAll(); // Utilisez votre méthode readAll() pour obtenir la liste des utilisateurs
                for (User user : users) {
                    if (user.getRole().name().equals(role)) { // Vérifiez si le rôle de l'utilisateur correspond au rôle spécifié
                        count++; // Incrémentez le compteur si le rôle correspond
                    }
                }
            } catch (SQLException e) {
                // Gérer les exceptions SQL
                e.printStackTrace();
            }

            // Retourner le nombre d'utilisateurs pour le rôle spécifié
            return count;
        }

        ///////////////////////////////////////////////////////////////////////////
 /*       // Méthode pour compter le nombre d'utilisateurs par rôle
        public Map<String, Long> countUsersByRole() throws SQLException {
            // Créer une connexion à la base de données
            Connection conn = DataSource.getInstance().getCon();
            // Initialiser une map pour stocker le résultat
            Map<String, Long> countMap = new HashMap<>();

            // Définir une requête SQL pour compter le nombre d'utilisateurs par rôle
            String sql = "SELECT roles, COUNT(*) AS count FROM users GROUP BY roles";
            try (
                    // Préparer la requête SQL
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    // Exécuter la requête et obtenir les résultats
                    ResultSet rs = stmt.executeQuery()
            ) {
                // Parcourir les résultats
                while (rs.next()) {
                    // Récupérer le nom du rôle et le nombre d'utilisateurs correspondant
                    String role = rs.getString("roles");
                    long count = rs.getLong("count");
                    // Ajouter le résultat à la map
                    countMap.put(role, count);
                }
            } finally {
                // Fermer la connexion à la base de données dans une clause finally pour assurer la fermeture
                conn.close();
            }

            // Retourner la map contenant le nombre d'utilisateurs par rôle
            return countMap;
        }
*/

    }