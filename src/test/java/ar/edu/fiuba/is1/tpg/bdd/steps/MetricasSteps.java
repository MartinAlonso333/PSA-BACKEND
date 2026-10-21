package ar.edu.fiuba.is1.tpg.bdd.steps;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.Contacto;
import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.repository.CampaniaRepository;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import ar.edu.fiuba.is1.tpg.repository.ContactoRepository;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;
import ar.edu.fiuba.is1.tpg.repository.LeadComercialRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import ar.edu.fiuba.is1.tpg.service.CampaniaService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class MetricasSteps {

    @Autowired private CampaniaService campaniaService;
    @Autowired private CampaniaRepository campaniaRepository;
    @Autowired private LeadComercialRepository leadRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private ContactoRepository contactoRepository;
    @Autowired private CanalRepository canalRepository;
    @Autowired private EmpresaRepository empresaRepository;

    private Campania campaniaActual;
    private Map<String, Object> metricas;
    private Exception excepcionCapturada;

    private Campania crearCampania(String nombre) {
        Producto p = productoRepository.save(
            new Producto("PSA Producto", "psa-prod-" + System.nanoTime()));
        Canal c = canalRepository.findByNombre("Email")
            .orElseGet(() -> canalRepository.save(new Canal("Email")));
        Campania campania = new Campania(nombre, LocalDate.now(),
            LocalDate.now().plusMonths(1), BigDecimal.valueOf(200000),
            BigDecimal.valueOf(100000), EstadoCampania.ACTIVA);
        campania.agregarProducto(p);
        campania.agregarCanal(c);
        return campaniaRepository.save(campania);
    }

    private void asociarLead(String nombre, String etapa) {
        Empresa emp = empresaRepository.findByNombre(nombre)
            .orElseGet(() -> empresaRepository.save(new Empresa(nombre)));
        Canal canal = canalRepository.findByNombre("Email")
            .orElseGet(() -> canalRepository.save(new Canal("Email")));
        LeadComercial lead = new LeadComercial(nombre, emp, canal, campaniaActual);

        switch (etapa) {
            case "Ganado" -> lead.cerrarComoGanado();
            case "Perdido" -> lead.cerrarComoPerdido("Sin interés");
            default -> {}
        }

        LeadComercial guardado = leadRepository.save(lead);
        contactoRepository.save(new Contacto(guardado, "contacto@test.com", null));
    }

    @Dado("que existe una campaña activa denominada {string} para métricas")
    public void queExisteUnaCampaniaActivaParaMetricas(String nombre) {
        campaniaActual = crearCampania(nombre);
    }

    @Y("la campaña tiene un costo de {int}")
    public void laCampaniaTieneUnCostoDe(int costo) {
        campaniaActual.setCosto(BigDecimal.valueOf(costo));
        campaniaRepository.save(campaniaActual);
    }

    @Y("se registran los siguientes leads para métricas:")
    public void seRegistranLosSiguientesLeadsParaMetricas(DataTable dataTable) {
        for (Map<String, String> row : dataTable.asMaps()) {
            String nombre = row.get("nombre");
            String etapa = row.get("etapa");
            asociarLead(nombre, etapa);
        }
    }

    @Cuando("se consultan las métricas de la campaña")
    public void seConsultaLasMetricasDeLaCampania() {
        excepcionCapturada = null;
        try {
            metricas = campaniaService.obtenerMetricas(campaniaActual.getId());
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Y("la respuesta debe incluir los campos {string}, {string}, {string}, {string} y {string}")
    public void laRespuestaDebeIncluirLosCampos(String c1, String c2, String c3, String c4, String c5) {
        assertThat(excepcionCapturada).isNull();
        assertThat(metricas).containsKeys(c1, c2, c3, c4, c5);
    }

    @Entonces("la cantidad de leads generados debe ser {int}")
    public void laCantidadDeLeadsGeneradosDebeSer(int cantidad) {
        assertThat(excepcionCapturada).isNull();
        assertThat(metricas.get("leadsGenerados")).isEqualTo((long) cantidad);
    }

    @Y("la cantidad de leads convertidos debe ser {int}")
    public void laCantidadDeLeadsConvertidosDebeSer(int cantidad) {
        assertThat(metricas.get("leadsConvertidos")).isEqualTo((long) cantidad);
    }

    @Y("la respuesta debe indicar {int} leads generados")
    public void laRespuestaDebeIndicarLeadsGenerados(int cantidad) {
        assertThat(metricas.get("leadsGenerados")).isEqualTo((long) cantidad);
    }

    @Y("la respuesta debe indicar {int} leads convertidos")
    public void laRespuestaDebeIndicarLeadsConvertidos(int cantidad) {
        assertThat(metricas.get("leadsConvertidos")).isEqualTo((long) cantidad);
    }

    @Y("la tasa de conversión debe ser {int}")
    public void laTasaDeConversionDebeSer(int porcentaje) {
        BigDecimal esperado = BigDecimal.valueOf(porcentaje).setScale(2, RoundingMode.HALF_UP);
        assertThat(metricas.get("conversion")).isEqualTo(esperado);
    }

    @Entonces("la respuesta debe contener el identificador de la campaña")
    public void laRespuestaDebeContenerElIdentificadorDeLaCampania() {
        assertThat(excepcionCapturada).isNull();
        assertThat(metricas.get("CampaniaId")).isEqualTo(campaniaActual.getId());
    }
}
