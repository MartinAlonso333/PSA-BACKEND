package ar.edu.fiuba.is1.tpg.exception;

public class CampaniaInactivaException extends RuntimeException {
    public CampaniaInactivaException(String nombre) {
        super("La campaña '" + nombre + "' no está activa y no puede recibir leads");
    }
}
