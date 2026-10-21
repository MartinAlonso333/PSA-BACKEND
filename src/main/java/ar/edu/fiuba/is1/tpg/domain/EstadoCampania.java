package ar.edu.fiuba.is1.tpg.domain;

public enum EstadoCampania {
    PLANIFICADA,
    ACTIVA,
    FINALIZADA;

    public boolean esSiguienteValida(EstadoCampania destino) {
        return this.ordinal() + 1 == destino.ordinal();
    }
}