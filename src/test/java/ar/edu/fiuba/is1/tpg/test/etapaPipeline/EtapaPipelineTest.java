package ar.edu.fiuba.is1.tpg.test.etapaPipeline;

import ar.edu.fiuba.is1.tpg.domain.EtapaPipeline;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class EtapaPipelineTest {

    // --- Transiciones válidas ---

    @Test
    void nuevoAContactadoEsValido() {
        assertThat(EtapaPipeline.NUEVO.esSiguienteValida(EtapaPipeline.CONTACTADO)).isTrue();
    }

    @Test
    void contactadoACalificadoEsValido() {
        assertThat(EtapaPipeline.CONTACTADO.esSiguienteValida(EtapaPipeline.CALIFICADO)).isTrue();
    }

    @Test
    void calificadoAConvertidoEsValido() {
        assertThat(EtapaPipeline.CALIFICADO.esSiguienteValida(EtapaPipeline.CONVERTIDO)).isTrue();
    }

    // --- Saltos hacia adelante inválidos ---

    @Test
    void nuevoACalificadoEsInvalido() {
        assertThat(EtapaPipeline.NUEVO.esSiguienteValida(EtapaPipeline.CALIFICADO)).isFalse();
    }

    @Test
    void nuevoAConvertidoEsInvalido() {
        assertThat(EtapaPipeline.NUEVO.esSiguienteValida(EtapaPipeline.CONVERTIDO)).isFalse();
    }

     @Test
     void contactadoAConvertidoEsInvalido() {
         assertThat(EtapaPipeline.CONTACTADO.esSiguienteValida(EtapaPipeline.CONVERTIDO)).isFalse();
     }

     @Test
     void calificadoAPerdidoEsInvalido() {
         assertThat(EtapaPipeline.CALIFICADO.esSiguienteValida(EtapaPipeline.PERDIDO)).isFalse();
     }

    // --- Retrocesos inválidos ---

    @Test
    void contactadoANuevoEsInvalido() {
        assertThat(EtapaPipeline.CONTACTADO.esSiguienteValida(EtapaPipeline.NUEVO)).isFalse();
    }

    @Test
    void calificadoAContactadoEsInvalido() {
        assertThat(EtapaPipeline.CALIFICADO.esSiguienteValida(EtapaPipeline.CONTACTADO)).isFalse();
    }

    @Test
    void convertidoACalificadoEsInvalido() {
        assertThat(EtapaPipeline.CONVERTIDO.esSiguienteValida(EtapaPipeline.CALIFICADO)).isFalse();
    }

    @Test
    void convertidoANuevoEsInvalido() {
        assertThat(EtapaPipeline.CONVERTIDO.esSiguienteValida(EtapaPipeline.NUEVO)).isFalse();
    }

    // --- Misma etapa inválida ---

    @Test
    void mismaEtapaEsInvalida() {
        assertThat(EtapaPipeline.NUEVO.esSiguienteValida(EtapaPipeline.NUEVO)).isFalse();
        assertThat(EtapaPipeline.CONTACTADO.esSiguienteValida(EtapaPipeline.CONTACTADO)).isFalse();
        assertThat(EtapaPipeline.CALIFICADO.esSiguienteValida(EtapaPipeline.CALIFICADO)).isFalse();
        assertThat(EtapaPipeline.CONVERTIDO.esSiguienteValida(EtapaPipeline.CONVERTIDO)).isFalse();
    }
}