package app;

import client.ClientIss;
import client.ErreurApi;
import modele.ReponseIss;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        ClientIss clientIss = new ClientIss();
        try {
            ReponseIss iss = clientIss.recupererPosition();

            String heure = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")
                    .withZone(ZoneId.of("Europe/Paris"))
                    .format(Instant.ofEpochSecond(iss.getTimestamp()));

            System.out.println("Position de l'ISS le " + heure + " (heure de Paris)");
            System.out.println(String.format(Locale.FRANCE, "Latitude : %.4f", iss.getPosition().getLatitude()));
            System.out.println(String.format(Locale.FRANCE, "Longitude : %.4f", iss.getPosition().getLongitude()));
        } catch (ErreurApi e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}