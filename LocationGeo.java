package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Account;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LocationGeo {
    public ArrayList<String> GetLocationGeo(String city) throws IOException {
// Set your secret key here

        String url = "https://api.geoapify.com/v1/geocode/search?text="+city+"&apiKey=23dfc253de5540f7816e0f32c323596b";
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
        ArrayList l = new ArrayList<String>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(String.valueOf(myResponse));
            JsonNode featuresNode = rootNode.path("features");
            if (featuresNode.isArray()) {
                int count = 0;
                for (JsonNode featureNode : featuresNode) {
                    JsonNode geometryNode = featureNode.path("geometry");
                    String type = geometryNode.path("type").asText();
                    if ("Point".equals(type)) {
                        JsonNode coordinatesNode = geometryNode.path("coordinates");
                        double longitude = coordinatesNode.get(0).asDouble();
                        double latitude = coordinatesNode.get(1).asDouble();
                       // System.out.println("Longitude: " + longitude);
                        //System.out.println("Latitude: " + latitude);

                        l.add(longitude);
                        l.add(latitude);
                        count++;
                        if (count == 1) {
                            break; // Stop after printing the first two sets of coordinates
                        }
                    }
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return l;
    }
       // System.out.println( response);
        //JSONObject rest = new JSONObject(myResponse.getJSONObject("current").toString());


    public static void main(String[] args) throws IOException {
     //   LocationGeo lo = new LocationGeo();
       // System.out.println(lo.GetLocationGeo("kef"));


    }


}