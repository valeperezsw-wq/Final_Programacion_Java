# BiblioExpress

API REST de una biblioteca barrial para prestar y devolver materiales.
Examen final práctico de Programación III.

- **Tecnologías:** Java 21, Spring Boot 3.3, Maven
- **Persistencia:** en memoria (HashMap), sin JPA ni base de datos

## Cómo ejecutar

```bash
mvn spring-boot:run
```

API en `http://localhost:8080`. Al arrancar carga datos de prueba (5 materiales, 3 socios).

## Arquitectura por capas

```
controller/  -> recibe HTTP, valida el JSON (@Valid), delega. Sin lógica.
service/     -> lógica de negocio: reglas, cálculo de multas, coordinación.
repository/  -> almacenamiento en memoria (HashMap).
model/       -> dominio (Material, Socio, Prestamo). Con herencia.
dto/         -> objetos del JSON. El modelo no se expone directo.
multas/      -> patrón Strategy para el cálculo de multas.
exception/   -> excepciones propias + manejador global de errores.
config/      -> carga de datos iniciales.
```

## Conceptos por unidad

| Unidad | Concepto | Dónde |
|---|---|---|
| 1 | Herencia / interfaces | `model/Material` → `Libro`, `Revista` · `model/Socio` → `SocioRegular`, `SocioPremium` · interfaz `multas/MultaStrategy` |
| 2 | Capas + validación iterativa | paquetes `controller/service/repository/model/dto` · `service/SocioService` recorre y valida cada DNI |
| 3 | Patrón Strategy | `multas/` + estrategia activa en `PrestamoService` |
| 4 | HashMap / HashSet | repos con `HashMap` · deduplicación con `HashSet` en `SocioService` |

## Endpoints

| Método | Ruta | Descripción |
|---|---|---|
| POST | `/api/prestamos/prestar` | `{ socioId, codigoMaterial }` — marca el material como prestado |
| POST | `/api/prestamos/devolver` | `{ socioId, codigoMaterial, diasAtraso }` — calcula multa (estrategia activa + beneficio del socio) |
| GET | `/api/materiales/disponibles` | lista los materiales disponibles |
| POST | `/api/socios/depurar-duplicados` | `{ dnis: [...] }` — devuelve los DNIs únicos en una pasada (HashSet) |
| POST | `/api/prestamos/estrategia?tipo=` | extra: cambia la estrategia activa (NORMAL / CAMPANIA / FIN_DE_SEMANA) |

## Estrategias de multa

| Estrategia | Cálculo |
|---|---|
| NORMAL | $100 × día |
| CAMPANIA | $60 × día |
| FIN_DE_SEMANA | $100 × día + $200 fijo |

## Datos de prueba

Materiales: `L-001` El Aleph, `L-002` Rayuela, `L-003` Ficciones, `R-001` El Gráfico, `R-002` Nature
Socios: `1` Vale Perez (Regular), `2` Joaquin Perez (Premium), `3` Ana Gomez (Regular)

## Pruebas

`BiblioExpress.postman_collection.json` (importable en Postman) y `EVIDENCIAS.md` con las capturas.
