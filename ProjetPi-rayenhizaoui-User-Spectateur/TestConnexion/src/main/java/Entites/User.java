package Entites;


import javax.management.relation.Role;

public class User {


    public User(int id, int cin, String username, int numero, String email, String adresse, String password, Entites.UserRole role) {
        this(cin, username, numero, email, adresse, password, String.valueOf(role));
        this.id = id;
    }

    public enum UserRole {
         SPECTATEUR,ADMIN
        // autres valeurs si nécessaire
    }

    //private UserRole roles;
    private int id;
    private int cin;
    private String username;
    private int numero;
    private String email;
    private String adresse;
    private String password;
    private UserRole roles; // Utilisation de l'enum pour représenter le rôle
    //private String roles;



    public User(int id, int cin, String username, int numero, String email, String adresse, String password, UserRole roles) {
        this(cin, username, numero, email, adresse, password, roles);
        this.id = id;
    }

    public User(int cin, String username, int numero, String email, String adresse, String password, UserRole roles) {
        this.cin = cin;
        this.username = username;
        this.numero = numero;
        this.email = email;
        this.adresse = adresse;
        this.password = password;
        this.roles = roles;
    }

    public User(int cin, String username, int numero, String email, String adresse, String password, String roles) {
        this(cin, username, numero, email, adresse, password, UserRole.valueOf(roles));
    }




    // Ajoutez les getters et les setters pour le rôle

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCin() {
        return cin;
    }

    public void setCin(int cin) {
        this.cin = cin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }




    public UserRole getRole() {
        return roles;
    }

    public void setRole(UserRole roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", cin=" + cin +
                ", username='" + username + '\'' +
                ", numero=" + numero +
                ", email='" + email + '\'' +
                ", adresse='" + adresse + '\'' +
                ", password='" + password + '\'' +
                ", role=" + roles +
                '}';
    }





    public String getRoles() {

        return roles.toString();
    }

      /*  StringBuilder rolesBuilder = new StringBuilder();
        for (UserRole userRole : UserRole.values()) {
            rolesBuilder.append(userRole.toString()).append(", ");
        }
        // Supprimer la dernière virgule et l'espace en trop
        if (rolesBuilder.length() > 0) {
            rolesBuilder.setLength(rolesBuilder.length() - 2);
        }
        return rolesBuilder.toString();
    }*/
/*
    public void setRoles(String newValue) {
    }
*/
    public void setRoles(String newValue) {
        // Convertir la chaîne en une constante d'énumération UserRole
        this.roles = UserRole.valueOf(newValue);
    }

}
