package com.example.biblioexpress.multas;

import org.springframework.stereotype.Component;

/**
 * Estrategia por defecto: $100 por cada dia de atraso.
 *
 * @Component: Spring crea una instancia y la guarda en su contenedor.
 * El PrestamoService pide "todas las MultaStrategy" y Spring le pasa esta,
 * MultaCampania y MultaFinDeSemana automaticamente.
 */
@Component
public class MultaNormal implements MultaStrategy {

    private static final double POR_DIA = 100.0;

    @Override
    public double calcular(int diasAtraso) {
        return diasAtraso * POR_DIA;
    }

    @Override
    public String nombre() {
        return "NORMAL";
    }
}
