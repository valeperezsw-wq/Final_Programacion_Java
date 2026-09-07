package com.example.biblioexpress.controller;

import com.example.biblioexpress.dto.DepurarDuplicadosRequest;
import com.example.biblioexpress.dto.DepurarDuplicadosResponse;
import com.example.biblioexpress.service.SocioService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UNIDAD 2 - CAPA CONTROLLER.
 *  POST /api/socios/depurar-duplicados
 */
@RestController
@RequestMapping("/api/socios")
public class SocioController {

    private final SocioService socioService;

    public SocioController(SocioService socioService) {
        this.socioService = socioService;
    }

    @PostMapping("/depurar-duplicados")
    public DepurarDuplicadosResponse depurarDuplicados(@Valid @RequestBody DepurarDuplicadosRequest request) {
        return socioService.depurarDuplicados(request.dnis());
    }
}
