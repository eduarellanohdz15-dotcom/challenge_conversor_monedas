import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Properties;

public class ExchangeRateAPIClient {

    private static final String API_KEY;

    static {

        Properties config = new Properties();

        try (FileInputStream fis = new FileInputStream("config.properties")) {

            config.load(fis);
            API_KEY = config.getProperty("api.key");

        } catch (IOException e) {

            throw new RuntimeException("No se pudo cargar la API key");
        }
    }

    public TasaDeCambio resultadoDeConversion(String baseCode,
                                              String targetCode,
                                              double amount)
            throws IOException, InterruptedException {

        String url = "https://v6.exchangerate-api.com/v6/"
                + API_KEY
                + "/pair/"
                + baseCode
                + "/"
                + targetCode
                + "/"
                + amount;

        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        HttpResponse<String> response =
                httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        Gson gson = new Gson();

        return gson.fromJson(response.body(), TasaDeCambio.class);
    }
}