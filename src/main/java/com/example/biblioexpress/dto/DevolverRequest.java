package com.example.biblioexpress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * UNIDAD 2 - DTO de entrada para POST /api/prestamos/devolver.
 * diasAtraso = 0 -> devolvio a tiempo (multa 0).
 */
public record DevolverRequest(

        @Positive(message = "socioId debe ser positivo")
        int socioId,

        @NotBlank(message = "codigoMaterial es obligatorio")
        String codigoMaterial,

        @PositiveOrZero(message = "diasAtraso no puede ser negativo")
        int diasAtraso
) {
}
