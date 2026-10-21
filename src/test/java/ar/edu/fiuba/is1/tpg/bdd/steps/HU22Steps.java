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
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.dto.CanalOrigenRequest;
import ar.edu.fiuba.is1.tpg.dto.ContactoRequest;
import ar.edu.fiuba.is1.tpg.dto.LeadRequest;
import ar.edu.fiuba.is1.tpg.dto.LeadResponse;
import ar.edu.fiuba.is1.tpg.dto.LeadUpdateRequest;
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

public class HU22Steps {

    @Autowired
    private LeadService leadService;

    @Autowired
    private CampaniaRepository campaniaRepository;

    @Autowired
    private LeadComercialRepository leadRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    private LeadComercial leadActual;
    private LeadResponse leadRegistrado;
    private LeadResponse leadActualizadoResponse;
    private Map<String, Object> resultadoActualizacion;
    private List<Map<String, Object>> reportePorCanal;
    private Exception excepcionCapturada;
    private String nombreCampaniaEnUso;
    private String canalOrigenPendiente;
    private boolean leadRegistradoFlag;

    private Campania obtenerOCrearCampania(String nombre) {
        return campaniaRepository.findByNombre(nombre).orElseGet(() -> {
            Producto p = productoRepository.save(
                new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())
            );
            Campania c = new Campania(nombre, LocalDate.now(),
                LocalDate.now().plusMonths(1), BigDecimal.valueOf(100000),
                BigDecimal.valueOf(80000), EstadoCampania.ACTIVA);
            c.agregarProducto(p);
            return campaniaRepository.save(c);
        });
    }

    @Dado("que existe una campaña activa llamada {string}")
    public void queExisteUnaCampaniaActivaLlamada(String nombre) {
        obtenerOCrearCampania(nombre);
        nombreCampaniaEnUso = nombre;
    }

    @Y("que existe un lead interesado llamado {string}")
    public void queExisteUnLeadInteresadoLlamado(String nombre) {
    }

    private String campaniaParaRegistro;

    @Cuando("se registra un nuevo lead para este contacto bajo la campaña {string}")
    public void seRegistraUnNuevoLeadParaEsteContacto(String nombreCampania) {
        this.campaniaParaRegistro = nombreCampania;
    }

    @Y("se define el canal de origen como {string}")
    public void seDefineElCanalDeOrigenComo(String canal) {
        this.canalOrigenPendiente = canal;
        // Ahora sí ejecutamos el registro con el canal disponible
        Campania campania = campaniaRepository.findByNombre(campaniaParaRegistro).orElseThrow();
        LeadRequest req = buildLeadRequest(campania.getId(), "Juan Pérez", "Empresa Test", canal);
        Producto p = productoRepository.findByNombre("PSA Soporte")
            .orElseGet(() -> productoRepository.save(new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())));
        req.setProductosInteres(List.of(p.getId()));
        try {
            leadRegistrado = leadService.registrar(req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el lead debe quedar vinculado exitosamente a su campaña origen")
    public void elLeadDebeQuedarVinculadoASuCampaniaOrigen() {
        assertThat(excepcionCapturada).isNull();
        assertThat(leadRegistrado).isNotNull();
        assertThat(leadRegistrado.getCampaniaOrigenId()).isNotNull();
    }

    @Y("su procedencia debe quedar clasificada bajo el canal {string}")
    public void suProcedenciaDebeQuedarClasificadaBajoElCanal(String canal) {
        assertThat(leadRegistrado.getCanalOrigen()).isEqualTo(canal);
    }

    @Dado("que se intenta dar de alta un lead para la {string}")
    public void queSeIntentaDarDeAltaUnLeadParaLa(String nombreCampania) {
        obtenerOCrearCampania(nombreCampania);
        nombreCampaniaEnUso = nombreCampania;
    }

    @Cuando("se omite especificar el canal de origen durante el registro")
    public void seOmiteEspecificarElCanalDeOrigen() {
        Campania campania = campaniaRepository.findByNombre(nombreCampaniaEnUso).orElseThrow();
        LeadRequest req = buildLeadRequest(campania.getId(), "Test", "Test SRL", null);
        try {
            leadRegistrado = leadService.registrar(req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("la creación del lead debe ser rechazada por falta de información obligatoria")
    public void laCreacionDelLeadDebeSerRechazada() {
        assertThat(excepcionCapturada).isNotNull();
    }

    @Y("no debe generarse ningún registro comercial nuevo")
    public void noDebeGenerarseNingunRegistroComercialNuevo() {
        assertThat(leadRegistrado).isNull();
    }

    @Dado("que el lead de {string} fue registrado originalmente vía {string}")
    public void queElLeadFueRegistradoOriginalmenteVia(String empresa, String canalOriginal) {
        Campania campania = obtenerOCrearCampania("Campaña Otoño 2026");
        Canal canalEntity = resolverOCrearCanal(canalOriginal);
        Empresa emp = empresaRepository.findByNombre(empresa).orElseGet(() ->
            empresaRepository.save(new Empresa(empresa)));
        LeadComercial lead = new LeadComercial("Contacto", emp, canalEntity, campania);
        leadActual = leadRepository.save(lead);
        contactoRepository.save(new Contacto(leadActual, "contacto@test.com", null));
    }

    @Cuando("se modifica su origen a {string}")
    public void seModificaSuOrigenA(String nuevoCanalOrigen) {
        CanalOrigenRequest req = new CanalOrigenRequest();
        req.setCanalOrigen(nuevoCanalOrigen);
        try {
            resultadoActualizacion = leadService.actualizarCanalOrigen(leadActual.getId(), req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el lead debe reflejar su nueva procedencia correctamente")
    public void elLeadDebeReflejarSuNuevaProcedencia() {
        assertThat(excepcionCapturada).isNull();
        assertThat(resultadoActualizacion.get("canalOrigenActual")).isEqualTo("Evento presencial");
    }

    @Y("debe preservarse un registro histórico de este cambio para auditoría")
    public void debePreservarseUnRegistroHistorico() {
        LeadComercial recargado = leadRepository.findById(leadActual.getId()).orElseThrow();
        assertThat(recargado.getCanalAnterior().getNombre()).isEqualTo("Email");
    }

    @Dado("que se han generado los siguientes leads:")
    public void queSeHanGeneradoLosSiguientesLeads(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String nombre = row.get("nombre");
            String nombreCampania = row.get("campaña");
            String canal = row.get("canal");
            Campania campania = obtenerOCrearCampania(nombreCampania);
            Canal canalEntity = resolverOCrearCanal(canal);
            Empresa emp = empresaRepository.findByNombre(nombre + " SRL").orElseGet(() ->
                empresaRepository.save(new Empresa(nombre + " SRL")));
            LeadComercial lead = new LeadComercial(nombre, emp, canalEntity, campania);
            LeadComercial guardado = leadRepository.save(lead);
            contactoRepository.save(new Contacto(guardado, nombre.toLowerCase().replace(" ", ".") + "@test.com", null));
        }
    }

    @Cuando("se solicita el reporte de rendimiento por canal")
    public void seSolicitaElReportePorCanal() {
        reportePorCanal = leadService.reportePorCanal(null);
    }

    @Entonces("el resumen de resultados debe mostrar:")
    public void elResumenDeResultadosDebeMostrar(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String canal = row.get("canal");
            long cantidad = Long.parseLong(row.get("cantidad"));
            long encontrado = reportePorCanal.stream()
                .filter(r -> r.get("canal").equals(canal))
                .mapToLong(r -> (Long) r.get("cantidad"))
                .sum();
            assertThat(encontrado).isEqualTo(cantidad);
        }
    }

    private Canal resolverOCrearCanal(String nombreCanal) {
        return canalRepository.findByNombre(nombreCanal)
            .orElseGet(() -> canalRepository.save(new Canal(nombreCanal)));
    }

    @Y("se ha registrado un lead bajo la campaña {string}")
    public void seHaRegistradoUnLeadBajoLaCampania(String nombreCampania) {
        Campania campania = campaniaRepository.findByNombre(nombreCampania).orElseThrow();
        LeadRequest req = buildLeadRequest(campania.getId(), "Juan Pérez", null, "Email");
        Producto p = productoRepository.findByNombre("PSA Soporte")
            .orElseGet(() -> productoRepository.save(new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())));
        req.setProductosInteres(List.of(p.getId()));
        leadRegistrado = leadService.registrar(req);
        leadActual = leadRepository.findById(leadRegistrado.getId()).orElseThrow();
        leadRegistradoFlag = true;
    }

    @Cuando("se actualiza el lead con los siguientes datos:")
    public void seActualizaElLeadConLosSiguientesDatos(DataTable dataTable) {
        LeadUpdateRequest updateReq = new LeadUpdateRequest();
        for (Map<String, String> row : dataTable.asMaps()) {
            String campo = row.get("campo");
            String valor = row.get("valor");
            switch (campo) {
                case "nombre" -> updateReq.setNombre(valor);
            }
        }
        try {
            leadActualizadoResponse = leadService.actualizar(leadActual.getId(), updateReq);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el lead debe reflejar los nuevos datos correctamente")
    public void elLeadDebeReflejarLosNuevosDatos() {
        assertThat(excepcionCapturada).isNull();
        assertThat(leadActualizadoResponse).isNotNull();
        assertThat(leadActualizadoResponse.getNombre()).isEqualTo("Juan Pérez Actualizado");
    }

    @Y("los campos no enviados deben mantener su valor original")
    public void losCamposNoEnviadosDebenMantenerValor() {
        assertThat(leadActualizadoResponse.getCanalOrigen()).isEqualTo("Email");
    }

    private LeadRequest buildLeadRequest(Long campaniaId, String nombre, String empresa, String canal) {
        LeadRequest req = new LeadRequest();
        req.setCampaniaOrigenId(campaniaId);
        req.setNombre(nombre);
        if (empresa != null) {
            Empresa emp = empresaRepository.findByNombre(empresa).orElseGet(() ->
                empresaRepository.save(new Empresa(empresa)));
            req.setEmpresaId(emp.getId());
        }
        req.setCanalOrigen(canal);
        ContactoRequest cr = new ContactoRequest();
        cr.setEmail("test@test.com");
        req.setContacto(cr);
        Producto p = productoRepository.findByNombre("PSA Soporte")
            .orElseGet(() -> productoRepository.save(new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())));
        req.setProductosInteres(List.of(p.getId()));
        return req;
    }
}
