package ar.edu.fiuba.is1.tpg.test.estadoCampania;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;

class EstadoCampaniaTest {

    // --- Transiciones válidas ---

    @Test
    void planificadaAActivaEsValida() {
        assertThat(EstadoCampania.PLANIFICADA.esSiguienteValida(EstadoCampania.ACTIVA)).isTrue();
    }

    @Test
    void activaAFinalizadaEsValida() {
        assertThat(EstadoCampania.ACTIVA.esSiguienteValida(EstadoCampania.FINALIZADA)).isTrue();
    }

    // --- Saltos hacia adelante inválidos ---

    @Test
    void planificadaAFinalizadaEsInvalida() {
        assertThat(EstadoCampania.PLANIFICADA.esSiguienteValida(EstadoCampania.FINALIZADA)).isFalse();
    }

    // --- Retrocesos inválidos ---

    @Test
    void activaAPlanificadaEsInvalida() {
        assertThat(EstadoCampania.ACTIVA.esSiguienteValida(EstadoCampania.PLANIFICADA)).isFalse();
    }

    @Test
    void finalizadaAActivaEsInvalida() {
        assertThat(EstadoCampania.FINALIZADA.esSiguienteValida(EstadoCampania.ACTIVA)).isFalse();
    }

    @Test
    void finalizadaAPlanificadaEsInvalida() {
        assertThat(EstadoCampania.FINALIZADA.esSiguienteValida(EstadoCampania.PLANIFICADA)).isFalse();
    }

    // --- Mismos estados inválidos ---

    @Test
    void mismoEstadoEsInvalido() {
        assertThat(EstadoCampania.PLANIFICADA.esSiguienteValida(EstadoCampania.PLANIFICADA)).isFalse();
        assertThat(EstadoCampania.ACTIVA.esSiguienteValida(EstadoCampania.ACTIVA)).isFalse();
        assertThat(EstadoCampania.FINALIZADA.esSiguienteValida(EstadoCampania.FINALIZADA)).isFalse();
    }
}