package com.example.biblioexpress.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

/**
 * UNIDAD 2 - DTO de entrada para POST /api/prestamos/prestar.
 * record = clase inmutable que solo transporta datos.
 * @Positive / @NotBlank validan el JSON: si falla, Spring responde 400.
 */
public record PrestarRequest(

        @Positive(message = "socioId debe ser un numero positivo")
        int socioId,

        @NotBlank(message = "codigoMaterial es obligatorio")
        String codigoMaterial
) {
}
