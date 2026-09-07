package com.example.biblioexpress.controller;

import com.example.biblioexpress.dto.DevolverRequest;
import com.example.biblioexpress.dto.DevolverResponse;
import com.example.biblioexpress.dto.PrestarRequest;
import com.example.biblioexpress.dto.PrestamoResponse;
import com.example.biblioexpress.service.PrestamoService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * UNIDAD 2 - CAPA CONTROLLER.
 *
 * Solo HTTP: mapear la URL, leer el body JSON (@RequestBody), validarlo (@Valid),
 * delegar al service. Sin logica de negocio.
 *
 *  POST /api/prestamos/prestar
 *  POST /api/prestamos/devolver
 *  POST /api/prestamos/estrategia   (extra)
 *  GET  /api/prestamos/estrategia   (extra)
 */
@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }

    @PostMapping("/prestar")
    public PrestamoResponse prestar(@Valid @RequestBody PrestarRequest request) {
        return prestamoService.prestar(request);
    }

    @PostMapping("/devolver")
    public DevolverResponse devolver(@Valid @RequestBody DevolverRequest request) {
        return prestamoService.devolver(request);
    }

    @PostMapping("/estrategia")
    public Map<String, String> cambiarEstrategia(@RequestParam String tipo) {
        return Map.of("estrategiaActiva", prestamoService.cambiarEstrategia(tipo));
    }

    @GetMapping("/estrategia")
    public Map<String, String> verEstrategia() {
        return Map.of("estrategiaActiva", prestamoService.getEstrategiaActiva());
    }
}
