package controller;

import Utils.Connexion;
import com.gluonhq.maps.MapLayer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import com.gluonhq.maps.MapView;
import com.gluonhq.maps.MapPoint;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import model.Tournoi;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MapsController implements Initializable {

    Connection mc;
    PreparedStatement ste;
    Tournoi Chosen ;
    public Tournoi getChosen() {
        return this.Chosen;
    }

    public void setChosen(Tournoi chosen) {
        this.Chosen = chosen;
    }
    String s =null;


    @FXML
private VBox address;
    @FXML
    private Label loc_Lab;

    String LocationStorage ;
    public void setLocationName(String s) {
        this.LocationStorage = s;
    }
    public String getLocationStorage(){
        return this.LocationStorage;

    }


    public double getLongitude() {
        return longitude;
    }



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
        System.out.println(latitude);
    }

    private double longitude;
    private double latitude;
    ;

    public MapPoint getTunisPoint() {
        return tunisPoint;
    }

    public void setTunisPoint(MapPoint tunisPoint) {
        this.tunisPoint = tunisPoint;
    }

    MapPoint tunisPoint = new MapPoint(25.0000, 20.0005);

    public void Testing(String s) throws IOException {
    LocationGeo loc = new LocationGeo();
        //System.out.println(getChosen());
        ArrayList longLat = loc.GetLocationGeo(s);
    System.out.println(s);
    System.out.println(longLat);
        setLongitude((Double) longLat.get(1));
        setLatitude((Double) longLat.get(0));
        setTunisPoint( new MapPoint((Double) longLat.get(1), (Double) longLat.get(0)));



    //String s = this.getLocationStorage();
    //System.out.println(""+ this.getLocationStorage()+ "" +longLat);

    }

    private void setLongitude(Double d) {
        this.longitude = d;
        System.out.println(d);

    }

    /*
        public void initialize() {
            System.out.println(getLocationStorage());
    MapView mapView = createMapView();
    address.getChildren().add(mapView);
    VBox.setVgrow(mapView, Priority.ALWAYS);
        }*/
@FXML
public void getEventBook(String s) throws IOException {
    // System.out.println(s);
    //System.out.println(s);
    String sql = "SELECT * FROM tournoi";


    try {
        Tournoi a = null;
        mc = Connexion.getInstance().getCnx();
        ste = mc.prepareStatement(sql);
        ResultSet rs = ste.executeQuery();
        while (rs.next()) {
            a = new Tournoi(rs.getString("nom"), rs.getString("location"), rs.getInt("fraisParticipation"));
            //System.out.println(a.getFraisParticipation());
            if (a.getNom().equals(s)) {
                //System.out.println(a.getLocation());
                //Date_lab.setText(a.getevent_date());
               // loc_Lab.setText(String.valueOf(a.getLocation()));
                Testing(a.getLocation());
                setLocationName(a.getLocation());
            }
        }


    } catch (SQLException ex) {
        System.out.println(ex.getMessage());
    }
}
    private MapView createMapView() {
        MapView mapView = new MapView();
        mapView.setPrefSize(500, 400);
        mapView.setZoom(4);
        mapView.addLayer(new customMaplayer());
        mapView.flyTo(0, getTunisPoint(), 0.1);
        return mapView;
    }

    @Override
    public void initialize (URL url, ResourceBundle resourceBundle){


        MapView mapView = createMapView();
        address.getChildren().add(mapView);
        VBox.setVgrow(mapView, Priority.ALWAYS);



    }


    public class customMaplayer extends MapLayer {
        private final Node marker;

        public customMaplayer() {
            marker = new Circle(10, Color.RED);
            getChildren().add(marker);
        }

        @Override
        protected void layoutLayer() {
            Point2D point = getMapPoint(getTunisPoint().getLatitude(), getTunisPoint().getLongitude());
            marker.setTranslateX(point.getX());
            marker.setTranslateY(point.getY());
        }
    }

}


