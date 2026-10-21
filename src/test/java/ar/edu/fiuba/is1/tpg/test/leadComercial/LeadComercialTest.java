package ar.edu.fiuba.is1.tpg.test.leadComercial;

import ar.edu.fiuba.is1.tpg.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;

class LeadComercialTest {

    private Campania campania;
    private Canal canalLinkedIn;
    private Canal canalEmail;
    private Canal canalEvento;
    private Empresa empresaTech;

    @BeforeEach
    void setUp() {
        campania = new Campania(
            "Black Friday PSA 2025",
            LocalDate.of(2025, 11, 1),
            LocalDate.of(2025, 11, 30),
            BigDecimal.valueOf(150000),
            BigDecimal.valueOf(120000),
            EstadoCampania.ACTIVA
        );
        canalLinkedIn = new Canal("LinkedIn", "Red social");
        canalEmail = new Canal("Email", "Email marketing");
        canalEvento = new Canal("Evento presencial", "Evento");
        empresaTech = new Empresa("Tech SRL");
    }

    // --- Estado inicial ---

    @Test
    void nuevoLeadIniciaSiempreEnEtapaNuevo() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThat(lead.getEtapa()).isEqualTo(EtapaPipeline.NUEVO);
    }

    @Test
    void nuevoLeadNoEstaCerrado() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThat(lead.estaCerrado()).isFalse();
    }

    @Test
    void nuevoLeadTieneCanalOrigenCorrecto() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThat(lead.getCanal().getNombre()).isEqualTo("LinkedIn");
        assertThat(lead.getCanalAnterior()).isNull();
    }

    // RN-07: todo lead debe estar asociado a una campaña y un canal de origen
    @Test
    void nuevoLeadQuedaAsociadoALaCampaniaDeOrigen() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThat(lead.getCampania()).isEqualTo(campania);
    }

    @Test
    void nuevoLeadGuardaNombreYEmpresaCorrectamente() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThat(lead.getNombre()).isEqualTo("Juan Pérez");
        assertThat(lead.getEmpresa()).isSameAs(empresaTech);
        assertThat(lead.getEmpresa().getNombre()).isEqualTo("Tech SRL");
    }

    // --- avanzarEtapa: transiciones válidas ---

    @Test
    void avanzaDeNuevoAContactadoCorrectamente() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        lead.avanzarEtapa(EtapaPipeline.CONTACTADO);

        assertThat(lead.getEtapa()).isEqualTo(EtapaPipeline.CONTACTADO);
    }

    @Test
    void avanzaDeContactadoACalificado() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        lead.avanzarEtapa(EtapaPipeline.CONTACTADO);

        lead.avanzarEtapa(EtapaPipeline.CALIFICADO);

        assertThat(lead.getEtapa()).isEqualTo(EtapaPipeline.CALIFICADO);
    }

    // --- avanzarEtapa: transiciones inválidas ---

    @Test
    void rechazaSaltoDeNuevoACalificado() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThatThrownBy(() -> lead.avanzarEtapa(EtapaPipeline.CALIFICADO))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaSaltoDeNuevoAConvertido() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThatThrownBy(() -> lead.avanzarEtapa(EtapaPipeline.CONVERTIDO))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaSaltoDeContactadoAConvertido() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        lead.avanzarEtapa(EtapaPipeline.CONTACTADO);

        assertThatThrownBy(() -> lead.avanzarEtapa(EtapaPipeline.CONVERTIDO))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void rechazaRetrocesoDeContactadoANuevo() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        lead.avanzarEtapa(EtapaPipeline.CONTACTADO);

        assertThatThrownBy(() -> lead.avanzarEtapa(EtapaPipeline.NUEVO))
            .isInstanceOf(IllegalStateException.class);
    }

     @Test
     void avanzaDeCalificadoAConvertido() {
         LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
         lead.avanzarEtapa(EtapaPipeline.CONTACTADO);
         lead.avanzarEtapa(EtapaPipeline.CALIFICADO);

         lead.avanzarEtapa(EtapaPipeline.CONVERTIDO);

         assertThat(lead.getEtapa()).isEqualTo(EtapaPipeline.CONVERTIDO);
     }

    // --- cerrarComoGanado ---

    @Test
    void cierreComoGanadoMarcaResultadoYAvanzaAConvertido() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        lead.avanzarEtapa(EtapaPipeline.CONTACTADO);
        lead.avanzarEtapa(EtapaPipeline.CALIFICADO);

        lead.cerrarComoGanado();

        assertThat(lead.getResultadoCierre()).isEqualTo(ResultadoCierre.GANADO);
        assertThat(lead.getEtapa()).isEqualTo(EtapaPipeline.CONVERTIDO);
    }

    @Test
    void leadGanadoQuedaMarcadoComoCerrado() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        lead.cerrarComoGanado();

        assertThat(lead.estaCerrado()).isTrue();
    }

    // --- cerrarComoPerdido ---

    @Test
    void cierreComoPerdidoGuardaMotivo() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        lead.avanzarEtapa(EtapaPipeline.CONTACTADO);

        lead.cerrarComoPerdido("Fuera del segmento objetivo");

        assertThat(lead.getResultadoCierre()).isEqualTo(ResultadoCierre.PERDIDO);
        assertThat(lead.getMotivoPerdida()).isEqualTo("Fuera del segmento objetivo");
    }

    @Test
    void leadPerdidoQuedaMarcadoComoCerrado() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        lead.cerrarComoPerdido("Sin presupuesto");

        assertThat(lead.estaCerrado()).isTrue();
    }

    @Test
    void cierreComoPerdidoConNullLanzaExcepcion() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThatThrownBy(() -> lead.cerrarComoPerdido(null))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cierreComoPerdidoConBlancoLanzaExcepcion() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThatThrownBy(() -> lead.cerrarComoPerdido("   "))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void cierreComoPerdidoConVacioLanzaExcepcion() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);

        assertThatThrownBy(() -> lead.cerrarComoPerdido(""))
            .isInstanceOf(IllegalArgumentException.class);
    }

    // --- actualizarCanalOrigen ---

    @Test
    void actualizarCanalOrigenPreservaElAnterior() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalEmail, campania);

        lead.actualizarCanalOrigen(canalEvento);

        assertThat(lead.getCanal().getNombre()).isEqualTo("Evento presencial");
        assertThat(lead.getCanalAnterior().getNombre()).isEqualTo("Email");
    }

    @Test
    void actualizarCanalOrigenDosVecesSoloPreservaElUltimoAnterior() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalEmail, campania);

        lead.actualizarCanalOrigen(canalEvento);
        lead.actualizarCanalOrigen(canalLinkedIn);

        assertThat(lead.getCanal().getNombre()).isEqualTo("LinkedIn");
        assertThat(lead.getCanalAnterior().getNombre()).isEqualTo("Evento presencial");
    }

    // --- agregarProductoInteres ---

    @Test
    void agregarProductoInteresLoIncluye() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        Producto p = new Producto("PSA Soporte", "psa-soporte");

        lead.agregarProductoInteres(p);

        assertThat(lead.getProductosInteres()).contains(p);
    }

    @Test
    void agregarMismoProductoInteresNoLoDuplica() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        Producto p = new Producto("PSA Soporte", "psa-soporte");

        lead.agregarProductoInteres(p);
        lead.agregarProductoInteres(p);

        assertThat(lead.getProductosInteres()).hasSize(1);
    }

    @Test
    void agregarDosProductosInteresDistintosLosIncluyeAmbos() {
        LeadComercial lead = new LeadComercial("Juan Pérez", empresaTech, canalLinkedIn, campania);
        Producto p1 = new Producto("PSA Soporte", "psa-soporte");
        Producto p2 = new Producto("PSA Monitoreo", "psa-monitoreo");

        lead.agregarProductoInteres(p1);
        lead.agregarProductoInteres(p2);

        assertThat(lead.getProductosInteres()).containsExactlyInAnyOrder(p1, p2);
    }
}