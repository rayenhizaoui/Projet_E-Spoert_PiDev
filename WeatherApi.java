package controller;

import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;
    public  class WeatherApi implements Initializable {

        @FXML
        private Button weather_btn;
        @FXML
        private TextField city_fld;

        @FXML
        private TextField degree_fld;

        @FXML
        private TextField feraniet_fld;

        @FXML
        public void call_me(String city) throws Exception
        {
            String url = "http://api.weatherapi.com/v1/current.json?key=33520bfb97e6419e91e213401222802&q=" + city;
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            con.setRequestProperty("User-Agent", "Mozilla/5.0");
            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print in String
            //System.out.println(response);
            //Read JSON response and print
            JSONObject myResponse = new JSONObject(response.toString());
            //System.out.println("result after Reading JSON Response");
            System.out.println( myResponse);
            JSONObject rest = new JSONObject(myResponse.getJSONObject("current").toString());

            degree_fld.setText(rest.get("temp_c").toString());
            feraniet_fld.setText(rest.get("temp_f").toString());
           //System.out.println(rest.get("temp_c"));
            /*System.out.println("countryName- "+myResponse.getString("countryName"));
            System.out.println("regionName- "+myResponse.getString("regionName"));
            System.out.println("cityName- "+myResponse.getString("cityName"));
            System.out.println("zipCode- "+myResponse.getString("zipCode"));
            System.out.println("latitude- "+myResponse.getString("latitude"));
            System.out.println("longitude- "+myResponse.getString("longitude"));*/


        }
        public void callEvent(MouseEvent event) throws Exception {
            if (!city_fld.getText().isEmpty()) {
                call_me(city_fld.getText());
            }
        }

        @Override


    public void initialize(URL location, ResourceBundle resources) {



    }}
