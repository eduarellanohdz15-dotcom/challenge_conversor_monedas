import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.util.List;

public class HistorialDeConversiones {

    public void guardarHistorial(List<TasaDeCambio> historial) {

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        try (FileWriter writer = new FileWriter("historial_conversiones.json")) {

            writer.write(gson.toJson(historial));

        } catch (Exception e) {

            System.out.println("No fue posible guardar el historial: " + e.getMessage());
        }
    }

    public void mostrarHistorial(List<TasaDeCambio> historial) {

        for (TasaDeCambio tasa : historial) {

            System.out.printf(
                    "Conversión %s -> %s | Tasa: %.4f | Resultado: %.2f%n",
                    tasa.base_code(),
                    tasa.target_code(),
                    tasa.conversion_rate(),
                    tasa.conversion_result()
            );
        }
    }
}