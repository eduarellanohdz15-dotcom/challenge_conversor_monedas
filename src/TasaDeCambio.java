public record TasaDeCambio(

        String base_code,       // moneda origen
        String target_code,     // moneda destino
        double conversion_rate, // tasa utilizada
        double conversion_result // resultado final

) {}