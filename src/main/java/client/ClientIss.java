package client;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import modele.ReponseIss;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class ClientIss {
    private static final String URL = "http://api.open-notify.org/iss-now.json";

    private final HttpClient client = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(5))
            .build();
    private final Gson gson = new Gson();

    public ReponseIss recupererPosition() throws ErreurApi {
        HttpRequest requete = HttpRequest.newBuilder()
                .uri(URI.create(URL))
                .timeout(Duration.ofSeconds(5))
                .build();
        try {
            HttpResponse<String> reponse = client.send(requete, HttpResponse.BodyHandlers.ofString());
            if (reponse.statusCode() != 200) {
                throw new ErreurApi("Le service a répondu avec le code " + reponse.statusCode());
            }
            ReponseIss iss = gson.fromJson(reponse.body(), ReponseIss.class);
            if (iss == null || iss.getPosition() == null) {
                throw new ErreurApi("Réponse invalide du service");
            }
            return iss;
        } catch (IOException e) {
            throw new ErreurApi("Impossible de joindre le service (réseau coupé ou délai dépassé)", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ErreurApi("Appel interrompu", e);
        } catch (JsonSyntaxException e) {
            throw new ErreurApi("Réponse illisible du service", e);
        }
    }
}