package ar.edu.fiuba.is1.tpg.bdd.steps;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;
import ar.edu.fiuba.is1.tpg.dto.CampaniaRequest;
import ar.edu.fiuba.is1.tpg.dto.CampaniaResponse;
import ar.edu.fiuba.is1.tpg.dto.CampaniaUpdateRequest;
import ar.edu.fiuba.is1.tpg.repository.CampaniaRepository;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;
import ar.edu.fiuba.is1.tpg.repository.LeadComercialRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import ar.edu.fiuba.is1.tpg.service.CampaniaService;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.springframework.beans.factory.annotation.Autowired;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HU06Steps {

    @Autowired
    private CampaniaService campaniaService;

    @Autowired
    private CampaniaRepository campaniaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private CanalRepository canalRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private LeadComercialRepository leadRepository;

    private CampaniaRequest campaniaRequest;
    private CampaniaResponse campaniaResponse;
    private CampaniaUpdateRequest campaniaUpdateRequest;
    private Exception excepcionCapturada;
    private String nombreCampania;
    private Campania campaniaActual;

    @Dado("que {string} está preparando el lanzamiento de {string}")
    public void queEstaPreparandoElLanzamiento(String responsable, String nombre) {
        this.nombreCampania = nombre;
        this.campaniaRequest = new CampaniaRequest();
        this.campaniaRequest.setNombre(nombre);
        this.campaniaRequest.setProductos(new ArrayList<>());
    }

    @Cuando("se registra la campaña con los siguientes datos:")
    public void seRegistraLaCampaniaConLosSiguientesDatos(io.cucumber.datatable.DataTable dataTable) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Map<String, String> row : dataTable.asMaps()) {
            String campo = row.get("campo");
            String valor = row.get("valor");
            switch (campo) {
                case "fecha inicio" -> campaniaRequest.setFechaInicio(LocalDate.parse(valor, fmt));
                case "fecha fin" -> campaniaRequest.setFechaFin(LocalDate.parse(valor, fmt));
                case "presupuesto" -> campaniaRequest.setPresupuesto(new BigDecimal(valor));
                case "costo" -> campaniaRequest.setCosto(new BigDecimal(valor));
                case "estado" -> campaniaRequest.setEstado(valor.toUpperCase());
                case "producto" -> {
                    Producto p = productoRepository.findByNombre(valor)
                        .orElseGet(() -> productoRepository.save(new Producto(valor, valor.toLowerCase().replace(" ", "-"))));
                    campaniaRequest.getProductos().add(p.getId());
                }
                case "canales" -> {
                    Canal c = canalRepository.findByNombre(valor)
                        .orElseThrow(() -> new RuntimeException("Canal no encontrado: " + valor));
                    campaniaRequest.setCanales(List.of(c.getId()));
                }
            }
        }
        try {
            campaniaResponse = campaniaService.registrar(campaniaRequest);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("la campaña debe quedar guardada bajo el nombre {string}")
    public void laCampaniaDebeQuedarGuardada(String nombre) {
        assertThat(excepcionCapturada).isNull();
        assertThat(campaniaResponse).isNotNull();
        assertThat(campaniaResponse.getNombre()).isEqualTo(nombre);
    }

    @Y("la campaña debe quedar asociada al producto {string}")
    public void laCampaniaDebeQuedarAsociadaAlProducto(String nombreProducto) {
        Producto p = productoRepository.findByNombre(nombreProducto).orElseThrow();
        assertThat(campaniaResponse.getProductos()).contains(p.getId());
    }

    @Y("la campaña debe quedar registrada en estado {string}")
    public void laCampaniaDebeQuedarRegistradaEnEstado(String estado) {
        assertThat(campaniaResponse.getEstado()).isEqualToIgnoringCase(estado);
    }

    @Y("debe permitirse el seguimiento de métricas de desempeño de la campaña")
    public void debePermitirseElSeguimientoDeMetricas() {
        assertThat(campaniaResponse.getId()).isNotNull();
        var metricas = campaniaService.obtenerMetricas(campaniaResponse.getId());
        assertThat(metricas).containsKey("leadsGenerados");
    }

    @Dado("que se intenta dar de alta la campaña {string}")
    public void queSeIntentaDarDeAltaLaCampania(String nombre) {
        this.nombreCampania = nombre;
        this.campaniaRequest = new CampaniaRequest();
        this.campaniaRequest.setNombre(nombre);
        this.campaniaRequest.setFechaInicio(LocalDate.now());
        this.campaniaRequest.setFechaFin(LocalDate.now().plusMonths(1));
        this.campaniaRequest.setCosto(BigDecimal.valueOf(1000));
        this.campaniaRequest.setEstado("ACTIVA");
        Producto p = productoRepository.findByNombre("PSA Soporte")
            .orElseGet(() -> productoRepository.save(new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())));
        this.campaniaRequest.setProductos(List.of(p.getId()));
    }

    @Cuando("no se define un presupuesto para la campaña")
    public void noSeDefineUnPresupuesto() {
        campaniaRequest.setPresupuesto(null);
        try {
            campaniaResponse = campaniaService.registrar(campaniaRequest);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el registro debe ser rechazado por incumplimiento de integridad")
    public void elRegistroDebeSerRechazado() {
        assertThat(excepcionCapturada).isNotNull();
    }

    @Y("no debe crearse la campaña en el repositorio central")
    public void noDebeCrearseLaCampania() {
        assertThat(campaniaRepository.findByNombre(nombreCampania)).isEmpty();
    }

    @Cuando("se actualiza la campaña {string} con los siguientes datos:")
    public void seActualizaLaCampania(String nombre, io.cucumber.datatable.DataTable dataTable) {
        Campania campania = campaniaRepository.findByNombre(nombre).orElseThrow();
        campaniaUpdateRequest = new CampaniaUpdateRequest();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        for (Map<String, String> row : dataTable.asMaps()) {
            String campo = row.get("campo");
            String valor = row.get("valor");
            switch (campo) {
                case "nombre" -> campaniaUpdateRequest.setNombre(valor);
                case "fecha inicio" -> campaniaUpdateRequest.setFechaInicio(LocalDate.parse(valor, fmt));
                case "fecha fin" -> campaniaUpdateRequest.setFechaFin(LocalDate.parse(valor, fmt));
                case "presupuesto" -> campaniaUpdateRequest.setPresupuesto(new BigDecimal(valor));
                case "costo" -> campaniaUpdateRequest.setCosto(new BigDecimal(valor));
            }
        }
        try {
            campaniaResponse = campaniaService.actualizar(campania.getId(), campaniaUpdateRequest);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("la campaña debe reflejar los cambios correctamente")
    public void laCampaniaDebeReflejarLosCambios() {
        assertThat(excepcionCapturada).isNull();
        assertThat(campaniaResponse).isNotNull();
        if (campaniaUpdateRequest.getNombre() != null) {
            assertThat(campaniaResponse.getNombre()).isEqualTo(campaniaUpdateRequest.getNombre());
        }
        if (campaniaUpdateRequest.getPresupuesto() != null) {
            assertThat(campaniaResponse.getPresupuesto()).isEqualByComparingTo(campaniaUpdateRequest.getPresupuesto());
        }
    }

    @Y("los campos no modificados deben mantener su valor original")
    public void losCamposNoModificadosDebenMantenerValor() {
        Campania original = campaniaRepository.findByNombre(nombreCampania).orElse(null);
        if (original != null) {
            if (campaniaUpdateRequest.getNombre() == null) {
                assertThat(campaniaResponse.getNombre()).isEqualTo(original.getNombre());
            }
            if (campaniaUpdateRequest.getPresupuesto() == null) {
                assertThat(campaniaResponse.getPresupuesto()).isEqualByComparingTo(original.getPresupuesto());
            }
        }
    }

    @Y("la campaña no tiene leads asociados")
    public void laCampaniaNoTieneLeadsAsociados() {
        // No-op: the campaign just created has no leads
    }

    @Y("la campaña tiene leads asociados")
    public void laCampaniaTieneLeadsAsociados() {
        Campania campania = campaniaRepository.findByNombre("Black Friday PSA 2025").orElseThrow();
        Canal canal = canalRepository.findByNombre("Email")
            .orElseGet(() -> canalRepository.save(new Canal("Email")));
        Empresa empresa = empresaRepository.findByNombre("TechCorp")
            .orElseGet(() -> empresaRepository.save(new Empresa("TechCorp", "TechCorp S.A.", "30-12345678-9", "Av. Siempre Viva 123")));
        LeadComercial lead = new LeadComercial("Lead Test", empresa, canal, campania);
        leadRepository.save(lead);
    }

    @Cuando("se elimina la campaña {string}")
    public void seEliminaLaCampania(String nombre) {
        Campania campania = campaniaRepository.findByNombre(nombre).orElseThrow();
        try {
            campaniaService.eliminar(campania.getId());
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Cuando("se intenta eliminar la campaña {string}")
    public void seIntentaEliminarLaCampania(String nombre) {
        seEliminaLaCampania(nombre);
    }

    @Entonces("la campaña debe ser eliminada exitosamente")
    public void laCampaniaDebeSerEliminada() {
        assertThat(excepcionCapturada).isNull();
    }

    @Y("no debe existir en el repositorio")
    public void noDebeExistirEnElRepositorio() {
        assertThat(campaniaRepository.findByNombre("Campaña a Eliminar")).isEmpty();
    }

    @Entonces("la eliminación debe ser rechazada por violación de integridad referencial")
    public void laEliminacionDebeSerRechazada() {
        assertThat(excepcionCapturada).isNotNull();
    }

    @Y("la campaña debe persistir en el repositorio")
    public void laCampaniaDebePersistir() {
        assertThat(campaniaRepository.findByNombre("Black Friday PSA 2025")).isPresent();
    }
}
