package ar.edu.fiuba.is1.tpg.test.producto;

import ar.edu.fiuba.is1.tpg.domain.Producto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class ProductoTest {

    @Test
    void productoGuardaNombreCorrectamente() {
        Producto p = new Producto("PSA Soporte", "psa-soporte");

        assertThat(p.getNombre()).isEqualTo("PSA Soporte");
    }

    @Test
    void productoGuardaCodigoCorrectamente() {
        Producto p = new Producto("PSA Soporte", "psa-soporte");

        assertThat(p.getCodigo()).isEqualTo("psa-soporte");
    }

    @Test
    void dosProductosConDistintoCodigoSonDistintos() {
        Producto p1 = new Producto("PSA Soporte", "psa-soporte");
        Producto p2 = new Producto("PSA Monitoreo", "psa-monitoreo");

        assertThat(p1.getCodigo()).isNotEqualTo(p2.getCodigo());
    }
}