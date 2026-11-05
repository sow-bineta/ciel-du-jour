package app;

import com.google.gson.Gson;
import modele.ReponseIss;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
    public static void main(String[] args) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requete = HttpRequest.newBuilder()
                .uri(URI.create("http://api.open-notify.org/iss-now.json"))
                .build();
        HttpResponse<String> reponse = client.send(requete, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();
        ReponseIss iss = gson.fromJson(reponse.body(), ReponseIss.class);

        String heure = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                .withZone(ZoneId.of("Europe/Paris"))
                .format(Instant.ofEpochSecond(iss.getTimestamp()));

        System.out.println("Position de l'ISS le " + heure + " (heure de Paris)");
        System.out.println(String.format(Locale.FRANCE, "Latitude : %.4f", iss.getPosition().getLatitude()));
        System.out.println(String.format(Locale.FRANCE, "Longitude : %.4f", iss.getPosition().getLongitude()));
    }
}