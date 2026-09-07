package com.example.biblioexpress.exception;

/** La operacion no respeta una regla del negocio (prestar algo ya prestado). -> HTTP 409. */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
