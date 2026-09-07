package com.example.biblioexpress.model;

/**
 * UNIDAD 1 - HERENCIA.
 *
 * Material es ABSTRACTA: representa "algo que la biblioteca presta", pero nunca
 * se crea un Material solo. Siempre es un Libro o una Revista.
 *
 * Atributos comunes a todos los materiales: codigo, titulo, disponible.
 * Libro y Revista HEREDAN de esta clase (extends) y agregan lo suyo.
 */
public abstract class Material {

    private String codigo;
    private String titulo;
    private boolean disponible;

    protected Material(String codigo, String titulo, boolean disponible) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.disponible = disponible;
    }

    /** Cada subclase dice de que tipo es (para mostrarlo en el JSON). */
    public abstract String getTipo();

    public String getCodigo() {
        return codigo;
    }

    public String getTitulo() {
        return titulo;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
