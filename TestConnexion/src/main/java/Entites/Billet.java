package Entites;

public class Billet {








    private int id;
    private int cin;
    private String type;
    private String email;
    private String place;

    public Billet(int id, int cin, String type, String email,String place) {
        this.id = id;
        this.cin=cin;
        this.type = type;
        this.email= email;
        this.place=place;
    }

    public Billet(int cin, String type, String email, String place) {
        this.cin=cin;
        this.type=type;
        this.email=email;
        this.place=place;
    }

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
        this.cin=cin;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type=type;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place=place;
    }

    @Override
    public String toString() {
        return "Billet{" +
                "id=" + id +
                ", cin='" + cin + '\'' +
                ", type='" + type + '\'' +
                ", email=" + email + '\'' +
                ", place=" + place +
                '}';
    }
}


