package com.example.biblioexpress.multas;

import org.springframework.stereotype.Component;

/**
 * Estrategia de fin de semana: $100 por dia MAS un recargo fijo de $200 al total
 * (interpretacion de "MultaFinDeSemana: suma $200 al total").
 *
 * Si el profe aclara que $200 es el total plano, se cambia una sola linea:
 * return 200.0;
 */
@Component
public class MultaFinDeSemana implements MultaStrategy {

    private static final double POR_DIA = 100.0;
    private static final double RECARGO_FIJO = 200.0;

    @Override
    public double calcular(int diasAtraso) {
        return diasAtraso * POR_DIA + RECARGO_FIJO;
    }

    @Override
    public String nombre() {
        return "FIN_DE_SEMANA";
    }
}
