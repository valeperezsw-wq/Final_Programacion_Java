package com.example.biblioexpress.model;

/**
 * UNIDAD 1 - HERENCIA + POLIMORFISMO.
 *
 * Socio es abstracta. Todo socio tiene id y nombre.
 *
 * aplicarBeneficio(monto) es la clave del polimorfismo:
 *  - SocioRegular NO descuenta -> devuelve el mismo monto.
 *  - SocioPremium descuenta 50% -> devuelve monto / 2.
 * El service llama socio.aplicarBeneficio(multa) SIN preguntar de que tipo es.
 */
public abstract class Socio {

    private int id;
    private String nombre;

    protected Socio(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    /** Devuelve cuanto paga realmente este socio despues de su beneficio. */
    public abstract double aplicarBeneficio(double monto);

    /** Texto para el JSON (REGULAR / PREMIUM). */
    public abstract String getTipo();

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
}
