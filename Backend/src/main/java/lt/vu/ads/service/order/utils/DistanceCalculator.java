package lt.vu.ads.service.order.utils;

import lt.vu.ads.models.Address;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DistanceCalculator {

//    @Value("${xRapidAPIKey}")
    private String xRapidAPIKey = System.getenv("xRapidAPIKey");

    public double  calculateDistance(Address sourceAddress, Address destinationAddress) {

        String sourceAddressString = getAddressString(sourceAddress);
        Pair coords1 = getDistance(sourceAddressString);

        double sourceLat = (double) coords1.getFirst();
        double sourceLng = (double) coords1.getSecond();

        String destinationAddressString = getAddressString(destinationAddress);

        Pair coords2 = getDistance(destinationAddressString);

        double destinationLat = (double) coords2.getFirst();
        double destinationLng = (double) coords2.getSecond();


        if ((sourceLat == destinationLat) && (sourceLng == destinationLng)) {
            return 0;
        }
        else {
            double theta = sourceLng - destinationLng;
            double dist = Math.sin(Math.toRadians(sourceLat)) * Math.sin(Math.toRadians(destinationLat)) +
                    Math.cos(Math.toRadians(sourceLat)) * Math.cos(Math.toRadians(destinationLat)) *
                            Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.609344;
            return (dist);
        }
    }

    private String getAddressString(Address address) {
        String street = address.getStreet().replaceAll("\\s", "");
        String addressString = street + address.getHouseNumber()+ address.getCity() + address.getCountry();
//        addressString.replaceAll("\\s","");
        return addressString;
    }


    private Pair<Double, Double> getDistance(String address) {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://google-maps-geocoding.p.rapidapi.com/geocode/json?address=" + address))
                .header("X-RapidAPI-Host", "google-maps-geocoding.p.rapidapi.com")
                .header("X-RapidAPI-Key", xRapidAPIKey)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;

        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String responseString = response.body();

        int latStart = responseString.indexOf("lat\" : ") + 7;
        int latEnd = responseString.substring(latStart).indexOf(",");
        double lat  = Double.parseDouble(responseString.substring(latStart, latStart + latEnd));

        int lngStart = responseString.indexOf("lng\" : ") + 7;
        int lngEnd = responseString.substring(lngStart).indexOf("\n");
        double lng  = Double.parseDouble(responseString.substring(lngStart, lngEnd + lngStart));

        return Pair.of(lat, lng);
    }
}
