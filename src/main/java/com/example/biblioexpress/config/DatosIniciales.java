package com.example.biblioexpress.config;

import com.example.biblioexpress.model.Libro;
import com.example.biblioexpress.model.Revista;
import com.example.biblioexpress.model.SocioPremium;
import com.example.biblioexpress.model.SocioRegular;
import com.example.biblioexpress.repository.MaterialRepository;
import com.example.biblioexpress.repository.SocioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Carga datos de prueba en memoria apenas arranca la app.
 * CommandLineRunner: Spring ejecuta run() una vez, al terminar de levantar.
 */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final MaterialRepository materialRepository;
    private final SocioRepository socioRepository;

    public DatosIniciales(MaterialRepository materialRepository, SocioRepository socioRepository) {
        this.materialRepository = materialRepository;
        this.socioRepository = socioRepository;
    }

    @Override
    public void run(String... args) {
        materialRepository.guardar(new Libro("L-001", "El Aleph", true, "Jorge Luis Borges"));
        materialRepository.guardar(new Libro("L-002", "Rayuela", true, "Julio Cortazar"));
        materialRepository.guardar(new Libro("L-003", "Ficciones", true, "Jorge Luis Borges"));
        materialRepository.guardar(new Revista("R-001", "El Grafico", true, 4210));
        materialRepository.guardar(new Revista("R-002", "Nature", true, 512));

        socioRepository.guardar(new SocioRegular(1, "Vale Perez"));
        socioRepository.guardar(new SocioPremium(2, "Joaquin Perez"));
        socioRepository.guardar(new SocioRegular(3, "Ana Gomez"));

        System.out.println(">>> Datos iniciales cargados: 5 materiales, 3 socios.");
    }
}
