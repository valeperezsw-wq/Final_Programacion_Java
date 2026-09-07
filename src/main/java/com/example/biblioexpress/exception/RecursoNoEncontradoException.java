package com.example.biblioexpress.exception;

/** Se busca algo que no existe (socio, material, prestamo). -> HTTP 404. */
public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
