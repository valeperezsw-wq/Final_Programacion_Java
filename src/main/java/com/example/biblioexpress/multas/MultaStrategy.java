package com.example.biblioexpress.multas;

/**
 * UNIDAD 3 - PATRON STRATEGY (y es una interfaz, Unidad 1).
 *
 * Contrato comun: "dame los dias de atraso y te digo cuanto es la multa".
 * Cada clase que la implementa es una estrategia concreta.
 * El PrestamoService guarda una estrategia "activa" y la usa sin saber cual es.
 * Agregar una multa nueva = crear una clase nueva, sin tocar el service (OCP).
 */
public interface MultaStrategy {

    /** Calcula el monto de la multa para esa cantidad de dias de atraso. */
    double calcular(int diasAtraso);

    /** Nombre corto: NORMAL, CAMPANIA, FIN_DE_SEMANA. */
    String nombre();
}
