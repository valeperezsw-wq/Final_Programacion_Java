package com.example.biblioexpress.dto;

import com.example.biblioexpress.model.Libro;
import com.example.biblioexpress.model.Material;
import com.example.biblioexpress.model.Revista;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * UNIDAD 2 - DTO de salida para representar un Material en el JSON.
 *
 * No devolvemos el objeto Material directo (condicion del examen: "no exponer
 * entidades directamente"). Damos un formato plano y parejo.
 * @JsonInclude(NON_NULL): los campos null no aparecen (una Revista no muestra "autor").
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MaterialResponse(
        String codigo,
        String titulo,
        String tipo,
        boolean disponible,
        String autor,
        Integer numeroEdicion
) {

    /** Fabrica: convierte un Material del modelo en este DTO. */
    public static MaterialResponse desde(Material m) {
        String autor = (m instanceof Libro libro) ? libro.getAutor() : null;
        Integer edicion = (m instanceof Revista revista) ? revista.getNumeroEdicion() : null;
        return new MaterialResponse(
                m.getCodigo(),
                m.getTitulo(),
                m.getTipo(),
                m.isDisponible(),
                autor,
                edicion
        );
    }
}
