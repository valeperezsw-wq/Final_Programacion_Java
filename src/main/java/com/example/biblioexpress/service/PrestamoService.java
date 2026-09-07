package com.example.biblioexpress.service;

import com.example.biblioexpress.dto.DevolverRequest;
import com.example.biblioexpress.dto.DevolverResponse;
import com.example.biblioexpress.dto.PrestarRequest;
import com.example.biblioexpress.dto.PrestamoResponse;
import com.example.biblioexpress.exception.RecursoNoEncontradoException;
import com.example.biblioexpress.exception.ReglaNegocioException;
import com.example.biblioexpress.model.Material;
import com.example.biblioexpress.model.Prestamo;
import com.example.biblioexpress.model.Socio;
import com.example.biblioexpress.multas.MultaStrategy;
import com.example.biblioexpress.repository.MaterialRepository;
import com.example.biblioexpress.repository.PrestamoRepository;
import com.example.biblioexpress.repository.SocioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * UNIDAD 2 (capas) + UNIDAD 3 (Strategy).
 *
 * El service concentra la logica de negocio: validar reglas, coordinar
 * repositorios, calcular la multa. El controller solo recibe el pedido y llama aca.
 */
@Service
public class PrestamoService {

    private final MaterialRepository materialRepository;
    private final SocioRepository socioRepository;
    private final PrestamoRepository prestamoRepository;

    /** Todas las estrategias de multa disponibles, indexadas por su nombre. */
    private final Map<String, MultaStrategy> estrategiasPorNombre = new LinkedHashMap<>();

    /** La estrategia que se aplica ahora. Se puede cambiar en caliente. */
    private MultaStrategy estrategiaActiva;

    /**
     * Spring inyecta los repositorios y la LISTA de todas las clases que
     * implementan MultaStrategy (MultaNormal, MultaCampania, MultaFinDeSemana).
     */
    public PrestamoService(MaterialRepository materialRepository,
                           SocioRepository socioRepository,
                           PrestamoRepository prestamoRepository,
                           List<MultaStrategy> estrategias) {
        this.materialRepository = materialRepository;
        this.socioRepository = socioRepository;
        this.prestamoRepository = prestamoRepository;

        for (MultaStrategy estrategia : estrategias) {
            estrategiasPorNombre.put(estrategia.nombre(), estrategia);
        }
        this.estrategiaActiva = estrategiasPorNombre.get("NORMAL"); // por defecto
    }

    // ---------------------------------------------------------------
    //  PRESTAR
    // ---------------------------------------------------------------
    public PrestamoResponse prestar(PrestarRequest request) {
        Socio socio = buscarSocio(request.socioId());
        Material material = buscarMaterial(request.codigoMaterial());

        if (!material.isDisponible()) {
            throw new ReglaNegocioException(
                    "El material '" + material.getCodigo() + "' no esta disponible (ya esta prestado).");
        }

        material.setDisponible(false);
        prestamoRepository.guardar(new Prestamo(material.getCodigo(), socio.getId(), LocalDate.now()));

        return new PrestamoResponse(
                material.getCodigo(),
                material.getTitulo(),
                socio.getId(),
                socio.getNombre(),
                LocalDate.now(),
                "Prestamo registrado correctamente."
        );
    }

    // ---------------------------------------------------------------
    //  DEVOLVER  (Strategy + polimorfismo del socio)
    // ---------------------------------------------------------------
    public DevolverResponse devolver(DevolverRequest request) {
        Socio socio = buscarSocio(request.socioId());
        Material material = buscarMaterial(request.codigoMaterial());

        Prestamo prestamo = prestamoRepository.buscarPorCodigoMaterial(material.getCodigo())
                .orElseThrow(() -> new ReglaNegocioException(
                        "No hay un prestamo activo para el material '" + material.getCodigo() + "'."));

        if (prestamo.getSocioId() != socio.getId()) {
            throw new ReglaNegocioException(
                    "El material lo tiene prestado otro socio (id " + prestamo.getSocioId() + ").");
        }

        // 1) La estrategia activa calcula la multa base segun los dias de atraso.
        double multaBase = estrategiaActiva.calcular(request.diasAtraso());

        // 2) El socio aplica su beneficio (polimorfismo: Premium 50%, Regular nada).
        double multaFinal = socio.aplicarBeneficio(multaBase);

        // 3) El material vuelve a estar disponible y el prestamo se cierra.
        material.setDisponible(true);
        prestamoRepository.eliminar(material.getCodigo());

        String mensaje = request.diasAtraso() == 0
                ? "Devolucion a tiempo, sin multa."
                : "Devolucion con " + request.diasAtraso() + " dias de atraso.";

        return new DevolverResponse(
                material.getCodigo(),
                request.diasAtraso(),
                estrategiaActiva.nombre(),
                multaBase,
                socio.getTipo(),
                multaFinal,
                mensaje
        );
    }

    // ---------------------------------------------------------------
    //  Cambiar la estrategia activa (extra, util para la defensa)
    // ---------------------------------------------------------------
    public String cambiarEstrategia(String nombre) {
        MultaStrategy nueva = estrategiasPorNombre.get(nombre == null ? "" : nombre.toUpperCase());
        if (nueva == null) {
            throw new ReglaNegocioException(
                    "Estrategia desconocida. Opciones: " + estrategiasPorNombre.keySet());
        }
        this.estrategiaActiva = nueva;
        return nueva.nombre();
    }

    public String getEstrategiaActiva() {
        return estrategiaActiva.nombre();
    }

    // ---------------------------------------------------------------
    //  Helpers privados (guard clauses reutilizables)
    // ---------------------------------------------------------------
    private Socio buscarSocio(int id) {
        return socioRepository.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el socio con id " + id));
    }

    private Material buscarMaterial(String codigo) {
        return materialRepository.buscarPorCodigo(codigo)
                .orElseThrow(() -> new RecursoNoEncontradoException("No existe el material con codigo " + codigo));
    }
}
