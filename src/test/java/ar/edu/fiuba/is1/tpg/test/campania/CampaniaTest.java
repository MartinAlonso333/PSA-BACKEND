package ar.edu.fiuba.is1.tpg.test.campania;

import ar.edu.fiuba.is1.tpg.domain.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class CampaniaTest {

    private Campania crearCampania(EstadoCampania estado) {
        return new Campania(
            "Black Friday PSA 2025",
            LocalDate.of(2025, 11, 1),
            LocalDate.of(2025, 11, 30),
            BigDecimal.valueOf(150000),
            BigDecimal.valueOf(120000),
            estado
        );
    }

    // --- Constructor: atributos básicos (HU-06) ---

    @Test
    void CampaniaGuardaAtributosDelConstructorCorrectamente() {
        Campania c = new Campania(
            "Black Friday PSA 2025",
            LocalDate.of(2025, 11, 1),
            LocalDate.of(2025, 11, 30),
            BigDecimal.valueOf(150000),
            BigDecimal.valueOf(120000),
            EstadoCampania.PLANIFICADA
        );

        assertThat(c.getNombre()).isEqualTo("Black Friday PSA 2025");
        assertThat(c.getFechaInicio()).isEqualTo(LocalDate.of(2025, 11, 1));
        assertThat(c.getFechaFin()).isEqualTo(LocalDate.of(2025, 11, 30));
        assertThat(c.getPresupuesto()).isEqualByComparingTo(BigDecimal.valueOf(150000));
        assertThat(c.getCosto()).isEqualByComparingTo(BigDecimal.valueOf(120000));
        assertThat(c.getEstado()).isEqualTo(EstadoCampania.PLANIFICADA);
    }

    // --- estaActiva ---

    @Test
    void CampaniaPlanificadaEstaInactiva() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);

        assertThat(c.estaActiva()).isFalse();
    }

    @Test
    void CampaniaActivaEstaActiva() {
        Campania c = crearCampania(EstadoCampania.ACTIVA);

        assertThat(c.estaActiva()).isTrue();
    }

    @Test
    void CampaniaFinalizadaEstaInactiva() {
        Campania c = crearCampania(EstadoCampania.FINALIZADA);

        assertThat(c.estaActiva()).isFalse();
    }

    // --- avanzarEstado: transiciones válidas ---

    @Test
    void avanzaDePlanificadaAActiva() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);

        c.avanzarEstado(EstadoCampania.ACTIVA);

        assertThat(c.getEstado()).isEqualTo(EstadoCampania.ACTIVA);
    }

    @Test
    void avanzaDeActivaAFinalizada() {
        Campania c = crearCampania(EstadoCampania.ACTIVA);

        c.avanzarEstado(EstadoCampania.FINALIZADA);

        assertThat(c.getEstado()).isEqualTo(EstadoCampania.FINALIZADA);
    }

    // --- avanzarEstado: transiciones inválidas ---

    @Test
    void rechazaSaltoDePlanificadaAFinalizada() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);

        assertThatThrownBy(() -> c.avanzarEstado(EstadoCampania.FINALIZADA))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaRetrocesoDeFinalizada() {
        Campania c = crearCampania(EstadoCampania.FINALIZADA);

        assertThatThrownBy(() -> c.avanzarEstado(EstadoCampania.ACTIVA))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaRetrocesoDeActivaAPlanificada() {
        Campania c = crearCampania(EstadoCampania.ACTIVA);

        assertThatThrownBy(() -> c.avanzarEstado(EstadoCampania.PLANIFICADA))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaPermaneceEnMismoEstadoPlanificada() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);

        assertThatThrownBy(() -> c.avanzarEstado(EstadoCampania.PLANIFICADA))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaPermaneceEnMismoEstadoActiva() {
        Campania c = crearCampania(EstadoCampania.ACTIVA);

        assertThatThrownBy(() -> c.avanzarEstado(EstadoCampania.ACTIVA))
            .isInstanceOf(IllegalStateException.class);
    }

    // --- agregarProducto ---

    @Test
    void agregarProductoLoIncluye() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);
        Producto p = new Producto("PSA Soporte", "psa-soporte");

        c.agregarProducto(p);

        assertThat(c.getProductos()).contains(p);
    }

    @Test
    void agregarMismoProductoDosVecesNoLoDuplica() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);
        Producto p = new Producto("PSA Soporte", "psa-soporte");

        c.agregarProducto(p);
        c.agregarProducto(p);

        assertThat(c.getProductos()).hasSize(1);
    }

    @Test
    void agregarDosProductosDistintosLosIncluyeAmbos() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);
        Producto p1 = new Producto("PSA Soporte", "psa-soporte");
        Producto p2 = new Producto("PSA Monitoreo", "psa-monitoreo");

        c.agregarProducto(p1);
        c.agregarProducto(p2);

        assertThat(c.getProductos()).containsExactlyInAnyOrder(p1, p2);
    }

    // --- Roi como atributo calculado (derivado al consultar métricas) ---

    @Test
    void roiEsNuloPorDefecto() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);
        assertThat(c.getRoi()).isNull();
    }

    @Test
    void setRoiAsignaElValor() {
        Campania c = crearCampania(EstadoCampania.PLANIFICADA);
        c.setRoi(BigDecimal.valueOf(25.5));
        assertThat(c.getRoi()).isEqualByComparingTo(BigDecimal.valueOf(25.5));
    }
}