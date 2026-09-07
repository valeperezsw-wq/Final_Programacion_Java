package com.example.biblioexpress.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * UNIDAD 2 + 4 - DTO de entrada para POST /api/socios/depurar-duplicados.
 * Recibe una lista de DNIs con repetidos. El service la recorre UNA vez
 * usando un HashSet para quedarse con los unicos.
 */
public record DepurarDuplicadosRequest(

        @NotNull(message = "la lista de dnis es obligatoria")
        List<String> dnis
) {
}
