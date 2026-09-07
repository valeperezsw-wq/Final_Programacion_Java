package com.example.biblioexpress.dto;

import java.util.List;

/**
 * UNIDAD 2 + 4 - DTO de salida de la depuracion de duplicados.
 *  - cantidadRecibidos: cuantos DNIs llegaron
 *  - cantidadUnicos: cuantos quedaron
 *  - duplicadosDescartados: cuantos se tiraron por repetidos
 *  - dnisUnicos: la lista final, en orden de aparicion
 *  - dnisInvalidos: los que se ignoraron por formato (validacion iterativa)
 */
public record DepurarDuplicadosResponse(
        int cantidadRecibidos,
        int cantidadUnicos,
        int duplicadosDescartados,
        List<String> dnisUnicos,
        List<String> dnisInvalidos
) {
}
