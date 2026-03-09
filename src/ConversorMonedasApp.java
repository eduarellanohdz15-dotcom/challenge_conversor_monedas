import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ConversorMonedasApp {

    static List<TasaDeCambio> historialConversiones = new ArrayList<>();

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        ExchangeRateAPIClient apiClient = new ExchangeRateAPIClient();

        System.out.println("*** BIENVENIDO AL CONVERSOR DE MONEDAS ***");

        String menu = """
                Seleccione una opción:
                1. Dólares a Pesos argentinos
                2. Pesos argentinos a Dólares
                3. Dólares a Pesos mexicanos
                4. Pesos mexicanos a Dólares
                5. Reales brasileños a Pesos argentinos
                6. Pesos argentinos a Reales brasileños
                7. Salir
                """;

        int opcion = 0;
        double cantidad;

        while (opcion != 7) {

            System.out.println(menu);
            opcion = scanner.nextInt();

            switch (opcion) {

                case 1 -> convertir(apiClient, "USD", "ARS", "Dólares", "Pesos argentinos", scanner);

                case 2 -> convertir(apiClient, "ARS", "USD", "Pesos argentinos", "Dólares", scanner);

                case 3 -> convertir(apiClient, "USD", "MXN", "Dólares", "Pesos mexicanos", scanner);

                case 4 -> convertir(apiClient, "MXN", "USD", "Pesos mexicanos", "Dólares", scanner);

                case 5 -> convertir(apiClient, "BRL", "ARS", "Real brasileño", "Pesos argentinos", scanner);

                case 6 -> convertir(apiClient, "ARS", "BRL", "Pesos argentinos", "Real brasileño", scanner);

            }
        }

        HistorialDeConversiones historial = new HistorialDeConversiones();
        historial.guardarHistorial(historialConversiones);

        System.out.println("Historial de conversiones:");
        historial.mostrarHistorial(historialConversiones);

        System.out.println("Gracias por utilizar el conversor.");
    }

    private static void convertir(ExchangeRateAPIClient apiClient,
                                  String base,
                                  String destino,
                                  String nombreBase,
                                  String nombreDestino,
                                  Scanner scanner) {

        System.out.println("Ingrese la cantidad en " + nombreBase + ":");

        double cantidad = scanner.nextDouble();

        try {

            TasaDeCambio resultado = apiClient.resultadoDeConversion(base, destino, cantidad);

            System.out.println("Resultado: " + resultado.conversion_result() + " " + nombreDestino);

            historialConversiones.add(resultado);

        } catch (IOException | InterruptedException e) {

            System.out.println("Ocurrió un error al realizar la conversión.");
        }
    }
}