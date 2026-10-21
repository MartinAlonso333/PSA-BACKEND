package ar.edu.fiuba.is1.tpg.bdd.steps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.Contacto;
import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import ar.edu.fiuba.is1.tpg.domain.EtapaPipeline;
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.domain.ResultadoCierre;
import ar.edu.fiuba.is1.tpg.dto.AvancePipelineRequest;
import ar.edu.fiuba.is1.tpg.dto.CierreLeadRequest;
import ar.edu.fiuba.is1.tpg.dto.ContactoRequest;
import ar.edu.fiuba.is1.tpg.dto.LeadRequest;
import ar.edu.fiuba.is1.tpg.dto.LeadResponse;
import ar.edu.fiuba.is1.tpg.repository.CampaniaRepository;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import ar.edu.fiuba.is1.tpg.repository.ContactoRepository;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;
import ar.edu.fiuba.is1.tpg.repository.LeadComercialRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import ar.edu.fiuba.is1.tpg.service.LeadService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class HU07Steps {

    @Autowired private LeadService leadService;
    @Autowired private CampaniaRepository campaniaRepository;
    @Autowired private LeadComercialRepository leadRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ContactoRepository contactoRepository;
    @Autowired private CanalRepository canalRepository;
    @Autowired private EmpresaRepository empresaRepository;

    private LeadComercial leadActual;
    private Map<String, Object> resultadoPipeline;
    private Map<String, Object> resultadoCierre;
    private Exception excepcionCapturada;
    private String nombreContactoInteresado;
    private List<LeadResponse> listaLeads;
    private LeadResponse leadResponseDetalle;
    private String ultimoNombreLeadRegistrado;

    private EtapaPipeline parsearEtapa(String raw) {
        String normalizada = raw.trim().toLowerCase();
        if (normalizada.equals("convertido en oportunidad")) {
            return EtapaPipeline.CONVERTIDO;
        }
        return EtapaPipeline.valueOf(raw.trim().toUpperCase());
    }

    private Canal resolverOCrearCanal(String nombreCanal) {
        return canalRepository.findByNombre(nombreCanal)
            .orElseGet(() -> canalRepository.save(new Canal(nombreCanal)));
    }

    private LeadComercial crearLeadEnEtapa(String empresa, EtapaPipeline etapaObjetivo, String nombreCampania) {
        Campania campania = campaniaRepository.findByNombre(nombreCampania).orElseGet(() -> {
            Producto p = productoRepository.save(
                new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime()));
            Campania c = new Campania(nombreCampania, LocalDate.now(),
                LocalDate.now().plusMonths(1), BigDecimal.valueOf(100000),
                BigDecimal.valueOf(80000), EstadoCampania.ACTIVA);
            c.agregarProducto(p);
            return campaniaRepository.save(c);
        });

        Empresa emp = empresaRepository.findByNombre(empresa).orElseGet(() ->
            empresaRepository.save(new Empresa(empresa)));
        LeadComercial lead = new LeadComercial(empresa, emp, resolverOCrearCanal("LinkedIn"), campania);
        EtapaPipeline[] etapas = {EtapaPipeline.NUEVO, EtapaPipeline.CONTACTADO, EtapaPipeline.CALIFICADO, EtapaPipeline.CONVERTIDO};
        
        for (int i = 0; i < etapas.length; i++) {
            if (etapas[i] == etapaObjetivo) break;
            if (i < etapas.length - 1) {
                lead.avanzarEtapa(etapas[i + 1]);
            }
        }
        
        if (etapaObjetivo == EtapaPipeline.PERDIDO) {
            lead.cerrarComoPerdido("Descarte inicial");
        }

        LeadComercial guardado = leadRepository.save(lead);
        contactoRepository.save(new Contacto(guardado, "contacto@empresa.com", null));
        return guardado;
    }

    @Dado("que el lead {string} se encuentra actualmente en la etapa {string}")
    public void queElLeadSeEncuentraActualmenteEnLaEtapa(String empresa, String etapaNombre) {
        excepcionCapturada = null;
        leadActual = crearLeadEnEtapa(empresa, parsearEtapa(etapaNombre), "Black Friday PSA 2025");
        assertThat(leadActual.getEtapa()).isEqualTo(parsearEtapa(etapaNombre));
    }

    @Cuando("se intenta avanzar el lead a la etapa {string}")
    public void seIntentaAvanzarElLeadALaEtapa(String etapaNombre) {
        AvancePipelineRequest req = new AvancePipelineRequest();
        req.setEtapaSiguiente(etapaNombre.trim());
        excepcionCapturada = null;
        try {
            resultadoPipeline = leadService.avanzarPipeline(leadActual.getId(), req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el lead debe avanzar a la etapa {string}")
    public void elLeadDebeAvanzarALaEtapa(String etapaNombre) {
        assertThat(excepcionCapturada).isNull();
        EtapaPipeline esperada = parsearEtapa(etapaNombre);
        assertThat(resultadoPipeline.get("etapaActual")).isEqualTo(esperada.name());
    }

    @Y("el lead debe quedar clasificado formalmente en la etapa {string}")
    public void elLeadDebeQuedarClasificadoFormalmenteEnLaEtapa(String etapaNombre) {
        LeadComercial recargado = leadRepository.findById(leadActual.getId()).orElseThrow();
        assertThat(recargado.getEtapa()).isEqualTo(parsearEtapa(etapaNombre));
    }

    @Dado("que el lead {string} se encuentra en la etapa {string}")
    public void queElLeadSeEncuentraEnLaEtapa(String empresa, String etapaNombre) {
        excepcionCapturada = null;
        leadActual = crearLeadEnEtapa(empresa, parsearEtapa(etapaNombre), "Black Friday PSA 2025");
    }

    @Cuando("se intenta saltar directamente a la etapa {string}")
    public void seIntentaSaltarDirectamenteALaEtapa(String etapaNombre) {
        AvancePipelineRequest req = new AvancePipelineRequest();
        req.setEtapaSiguiente(etapaNombre.trim());
        excepcionCapturada = null;
        try {
            resultadoPipeline = leadService.avanzarPipeline(leadActual.getId(), req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("la transición debe ser rechazada por violar la secuencia del pipeline")
    public void laTransicionDebeSerRechazada() {
        assertThat(excepcionCapturada).isNotNull();
    }

    @Y("el lead debe permanecer en su etapa original {string}")
    public void elLeadDebePermanecer(String etapaNombre) {
        LeadComercial recargado = leadRepository.findById(leadActual.getId()).orElseThrow();
        assertThat(recargado.getEtapa()).isEqualTo(parsearEtapa(etapaNombre));
    }



    @Y("un contacto interesado llamado {string}")
    public void unContactoInteresadoLlamado(String nombre) {
        this.nombreContactoInteresado = nombre;
    }

    @Cuando("se crea un lead asociado a dicha campaña para la empresa {string}")
    public void seCreaUnLeadAsociadoADichaCampania(String empresa) {
        Campania cam = campaniaRepository.findAll().stream()
                .filter(c -> c.estaActiva())
                .findFirst()
                .orElseThrow();
        LeadRequest req = new LeadRequest();
        req.setCampaniaOrigenId(cam.getId());
        String nombreLead = nombreContactoInteresado != null ? nombreContactoInteresado : "Juan Pérez";
        req.setNombre(nombreLead);
        req.setCanalOrigen("LinkedIn");
        ContactoRequest cr = new ContactoRequest();
        cr.setEmail("contacto@empresa.com");
        req.setContacto(cr);
        Producto p = productoRepository.findByNombre("PSA Soporte")
            .orElseGet(() -> productoRepository.save(new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())));
        req.setProductosInteres(List.of(p.getId()));
        excepcionCapturada = null;
        try {
            leadService.registrar(req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el lead debe ser dado de alta exitosamente")
    public void elLeadDebeSerDadoDeAlta() {
        assertThat(excepcionCapturada).isNull();
    }

    @Y("su estado inicial debe ser definido como {string}")
    public void suEstadoInicialDebeSerDefinidoComo(String estado) {
        LeadComercial ultimo = leadRepository.findAll().stream().reduce((a, b) -> b).orElseThrow();
        assertThat(ultimo.getEtapa()).isEqualTo(parsearEtapa(estado));
    }

    @Dado("que el lead {string} ha alcanzado satisfactoriamente la etapa {string}")
    public void queElLeadHaAlcanzadoSatisfactoriamenteLaEtapa(String empresa, String etapaNombre) {
        excepcionCapturada = null;
        leadActual = crearLeadEnEtapa(empresa, parsearEtapa(etapaNombre), "Black Friday PSA 2025");
    }

    @Cuando("se confirma la conversión del lead a Oportunidad")
    public void seConfirmaLaConversionDelLeadAOportunidad() {
        CierreLeadRequest req = new CierreLeadRequest();
        req.setResultado("GANADO");
        excepcionCapturada = null;
        try {
            resultadoCierre = leadService.cerrar(leadActual.getId(), req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el estado del lead debe cambiar a {string}")
    public void elEstadoDelLeadDebeCambiarA(String etapaNombre) {
        assertThat(excepcionCapturada).isNull();
        LeadComercial recargado = leadRepository.findById(leadActual.getId()).orElseThrow();
        assertThat(recargado.getEtapa()).isEqualTo(parsearEtapa(etapaNombre));
    }

    @Y("la conversión debe quedar vinculada a la campaña {string}")
    public void laConversionDebeQuedarVinculadaALaCampania(String nombreCampania) {
        LeadComercial recargado = leadRepository.findById(leadActual.getId()).orElseThrow();
        assertThat(recargado.getCampania().getNombre()).isEqualTo(nombreCampania);
    }

    @Cuando("se marca el lead como {string} indicando el motivo {string}")
    public void seMarcaElLeadComoConMotivo(String resultado, String motivo) {
        CierreLeadRequest req = new CierreLeadRequest();
        req.setResultado("PERDIDO");
        req.setMotivo(motivo);
        excepcionCapturada = null;
        try {
            resultadoCierre = leadService.cerrar(leadActual.getId(), req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Y("el lead debe conservar su estado final registrado")
    public void elLeadDebeConservarSuEstadoFinal() {
        LeadComercial recargado = leadRepository.findById(leadActual.getId()).orElseThrow();
        assertThat(recargado.getResultadoCierre()).isEqualTo(ResultadoCierre.PERDIDO);
        assertThat(recargado.getMotivoPerdida()).isNotBlank();
    }

    @Dado("que el lead {string} está listo para finalizar su ciclo en la etapa {string}")
    public void queElLeadEstaListoParaFinalizarEnLaEtapa(String empresa, String etapaNombre) {
        excepcionCapturada = null;
        leadActual = crearLeadEnEtapa(empresa, parsearEtapa(etapaNombre), "Black Friday PSA 2025");
    }

    @Cuando("se intenta completar el proceso sin registrar un resultado \\(Convertido en Oportunidad o Perdido)")
    public void seIntentaCompletarSinResultadoLargo() {
        CierreLeadRequest req = new CierreLeadRequest();
        req.setResultado(null);
        excepcionCapturada = null;
        try {
            resultadoCierre = leadService.cerrar(leadActual.getId(), req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el avance debe bloquearse hasta identificar el resultado final de la calificación")
    public void elAvanceDebeBloquearseHastaIdentificarElResultado() {
        assertThat(excepcionCapturada).isNotNull();
    }

    @Dado("que se han registrado los siguientes leads:")
    public void queSeHanRegistradoLosSiguientesLeads(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String nombre = row.get("nombre");
            String nombreCampania = row.get("campaña");
            String canal = row.get("canal");
            Campania campania = campaniaRepository.findByNombre(nombreCampania).orElseGet(() -> {
                Producto p = productoRepository.save(
                    new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime()));
                Campania c = new Campania(nombreCampania, LocalDate.now(),
                    LocalDate.now().plusMonths(1), BigDecimal.valueOf(100000),
                    BigDecimal.valueOf(80000), EstadoCampania.ACTIVA);
                c.agregarProducto(p);
                return campaniaRepository.save(c);
            });
            Canal canalEntity = canalRepository.findByNombre(canal)
                .orElseGet(() -> canalRepository.save(new Canal(canal)));
            Empresa emp = empresaRepository.findByNombre(nombre + " SRL").orElseGet(() ->
                empresaRepository.save(new Empresa(nombre + " SRL")));
            LeadComercial lead = new LeadComercial(nombre, emp, canalEntity, campania);
            leadRepository.save(lead);
            contactoRepository.save(new Contacto(lead, "contacto@test.com", null));
        }
    }

    @Cuando("se solicita el listado de leads filtrado por campaña {string}")
    public void seSolicitaListadoDeLeadsFiltradoPorCampania(String nombreCampania) {
        Campania campania = campaniaRepository.findByNombre(nombreCampania).orElseThrow();
        listaLeads = leadService.listar(campania.getId(), null, null);
    }

    @Entonces("deben obtenerse {int} leads en el resultado")
    public void debenObtenerseNLeads(int cantidad) {
        assertThat(listaLeads).hasSize(cantidad);
    }

    @Y("los leads deben pertenecer a la campaña {string}")
    public void losLeadsDebenPertenecerALaCampania(String nombreCampania) {
        for (LeadResponse lead : listaLeads) {
            assertThat(lead.getCampaniaNombre()).isEqualTo(nombreCampania);
        }
    }

    @Dado("que se ha registrado un lead llamado {string} para la campaña {string}")
    public void queSeHaRegistradoUnLeadLlamadoParaLaCampania(String nombreLead, String nombreCampania) {
        Campania campania = campaniaRepository.findByNombre(nombreCampania).orElseGet(() -> {
            Producto p = productoRepository.save(
                new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime()));
            Campania c = new Campania(nombreCampania, LocalDate.now(),
                LocalDate.now().plusMonths(1), BigDecimal.valueOf(100000),
                BigDecimal.valueOf(80000), EstadoCampania.ACTIVA);
            c.agregarProducto(p);
            return campaniaRepository.save(c);
        });
        Canal canal = canalRepository.findByNombre("Email")
            .orElseGet(() -> canalRepository.save(new Canal("Email")));
        Empresa emp = empresaRepository.findByNombre("TestCorp").orElseGet(() ->
            empresaRepository.save(new Empresa("TestCorp")));
        LeadComercial lead = new LeadComercial(nombreLead, emp, canal, campania);
        leadActual = leadRepository.save(lead);
        contactoRepository.save(new Contacto(leadActual, "lead@test.com", null));
        ultimoNombreLeadRegistrado = nombreLead;
    }

    @Cuando("se solicita el detalle del lead")
    public void seSolicitaElDetalleDelLead() {
        leadResponseDetalle = leadService.obtener(leadActual.getId());
    }

    @Entonces("debe devolverse la información completa del lead")
    public void debeDevolverseInformacionCompletaDelLead() {
        assertThat(leadResponseDetalle).isNotNull();
        assertThat(leadResponseDetalle.getId()).isEqualTo(leadActual.getId());
        assertThat(leadResponseDetalle.getNombre()).isEqualTo(ultimoNombreLeadRegistrado);
    }

    @Y("el lead debe estar en etapa {string}")
    public void elLeadDebeEstarEnEtapa(String etapa) {
        assertThat(leadResponseDetalle.getEtapa()).isEqualToIgnoringCase(etapa);
    }

    @Y("el lead debe estar asociado a la campaña {string}")
    public void elLeadDebeEstarAsociadoALaCampania(String nombreCampania) {
        assertThat(leadResponseDetalle.getCampaniaNombre()).isEqualTo(nombreCampania);
    }
}
