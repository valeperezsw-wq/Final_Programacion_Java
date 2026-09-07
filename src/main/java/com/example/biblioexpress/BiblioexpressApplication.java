package com.example.biblioexpress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Punto de entrada de la aplicacion.
 *
 * @SpringBootApplication:
 *  - marca esta clase como configuracion de Spring,
 *  - activa la autoconfiguracion (arma solo el servidor web, el JSON, etc.),
 *  - escanea este paquete y los de abajo buscando @RestController, @Service, @Repository...
 *
 * main() levanta un servidor Tomcat embebido en el puerto 8080.
 */
@SpringBootApplication
public class BiblioexpressApplication {

    public static void main(String[] args) {
        SpringApplication.run(BiblioexpressApplication.class, args);
    }
}
