package ar.edu.fiuba.is1.tpg.domain;

import java.util.List;

public enum EtapaPipeline {
    NUEVO,
    CONTACTADO,
    CALIFICADO,
    CONVERTIDO,
    PERDIDO;

    public boolean esSiguienteValida(EtapaPipeline destino) {
        return switch (this) {
            case NUEVO -> List.of(CONTACTADO, PERDIDO).contains(destino);
            case CONTACTADO -> List.of(CALIFICADO, PERDIDO).contains(destino);
            case CALIFICADO -> List.of(CONVERTIDO).contains(destino);
            case CONVERTIDO, PERDIDO -> false; // Estados finales, no avanzan a nada
        };
    }
}