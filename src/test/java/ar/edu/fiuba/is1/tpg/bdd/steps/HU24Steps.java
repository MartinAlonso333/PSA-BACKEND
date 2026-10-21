package ar.edu.fiuba.is1.tpg.bdd.steps;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.dto.ContactoRequest;
import ar.edu.fiuba.is1.tpg.dto.LeadRequest;
import ar.edu.fiuba.is1.tpg.dto.LeadResponse;
import ar.edu.fiuba.is1.tpg.repository.CampaniaRepository;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import ar.edu.fiuba.is1.tpg.service.LeadService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;

public class HU24Steps {

    @Autowired private LeadService leadService;
    @Autowired private CampaniaRepository campaniaRepository;
    @Autowired private ProductoRepository productoRepository;
    @Autowired private EmpresaRepository empresaRepository;

    private LeadResponse leadRegistrado;
    private Exception excepcionCapturada;
    private String campaniaContexto;

    private Campania obtenerOCrearCampania(String nombre) {
        return campaniaRepository.findByNombre(nombre).orElseGet(() -> {
            Producto p = productoRepository.save(
                new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())
            );
            Campania c = new Campania(nombre, LocalDate.now(),
                LocalDate.now().plusMonths(1), BigDecimal.valueOf(150000),
                BigDecimal.valueOf(120000), EstadoCampania.ACTIVA);
            c.agregarProducto(p);
            return campaniaRepository.save(c);
        });
    }

    @Cuando("{string} registra el lead {string} con los siguientes datos:")
    public void registraElLeadConLosSiguientesDatos(String responsable, String empresa, DataTable dataTable) {
        Map<String, String> datos = dataTable.asMap();
        Campania campania = obtenerOCrearCampania(datos.get("campaña origen"));

        LeadRequest req = new LeadRequest();
        req.setCampaniaOrigenId(campania.getId());
        String nombreEmpresa = datos.get("empresa");
        Empresa emp = empresaRepository.findByNombre(nombreEmpresa).orElseGet(() ->
            empresaRepository.save(new Empresa(nombreEmpresa)));
        req.setEmpresaId(emp.getId());
        req.setCanalOrigen(datos.get("canal origen"));
        req.setNombre(nombreEmpresa);
        ContactoRequest cr = new ContactoRequest();
        cr.setEmail(datos.get("email"));
        cr.setTelefono(datos.get("teléfono"));
        req.setContacto(cr);
        String producto = datos.get("producto interés");
        if (producto != null) {
            // resolver/crear producto en DB y usar su id
            Producto p = productoRepository.findByNombre(producto)
                .orElseGet(() -> productoRepository.save(new Producto(producto, producto.toLowerCase().replace(" ", "-"))));
            req.setProductosInteres(List.of(p.getId()));
        } else {
            req.setProductosInteres(List.of());
        }
        excepcionCapturada = null;
        try {
            leadRegistrado = leadService.registrar(req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("debe establecerse el vínculo comercial entre la campaña y el lead")
    public void debeEstablecerseElVinculoComercial() {
        assertThat(excepcionCapturada).isNull();
        assertThat(leadRegistrado).isNotNull();
        assertThat(leadRegistrado.getCampaniaOrigenId()).isNotNull();
    }

    @Y("el lead debe quedar registrado bajo el canal {string} en estado {string}")
    public void elLeadDebeQuedarRegistradoBajoElCanal(String canal, String estado) {
        assertThat(leadRegistrado.getCanalOrigen()).isEqualTo(canal);
        assertThat(leadRegistrado.getEtapa()).isEqualToIgnoringCase(estado);
    }

    @Y("el lead debe quedar asociado al producto {string}")
    public void elLeadDebeQuedarAsociadoAlProducto(String nombreProducto) {
        // comprobar que entre los ids devueltos exista el producto con ese nombre
        Producto p = productoRepository.findByNombre(nombreProducto).orElseThrow();
        assertThat(leadRegistrado.getProductosInteres()).contains(p.getId());
    }

    @Y("la información de contacto del lead debe quedar registrada correctamente")
    public void laInformacionDeContactoDebeQuedarRegistrada() {
        assertThat(leadRegistrado.getContacto()).isNotNull();
        assertThat(leadRegistrado.getContacto().getEmail()).isEqualTo("contacto@techsolutions.com");
    }

    @Dado("que {string} intenta registrar un lead para la campaña {string}")
    public void intentaRegistrarUnLeadParaLaCampania(String responsable, String nombreCampania) {
        obtenerOCrearCampania(nombreCampania);
        campaniaContexto = nombreCampania;
        leadRegistrado = null;
        excepcionCapturada = null;
    }

    @Cuando("se omite el dato obligatorio {string}")
    public void seOmiteElDatoObligatorio(String campo) {
        Campania campania = campaniaRepository.findByNombre(campaniaContexto).orElseThrow();

        LeadRequest req = new LeadRequest();
        req.setCampaniaOrigenId(campania.getId());
        req.setNombre("Test Lead");
        Empresa emp = empresaRepository.findByNombre("Test SRL").orElseGet(() ->
            empresaRepository.save(new Empresa("Test SRL")));
        req.setEmpresaId(emp.getId());
        req.setCanalOrigen("Email");
        ContactoRequest cr = new ContactoRequest();
        cr.setEmail("test@test.com");
        req.setContacto(cr);
        // crear/recuperar producto y usar su id
        Producto p = productoRepository.findByNombre("PSA Soporte")
            .orElseGet(() -> productoRepository.save(new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())));
        req.setProductosInteres(List.of(p.getId()));

        switch (campo.trim()) {
            case "campaña origen" -> req.setCampaniaOrigenId(null);
            case "canal origen"   -> req.setCanalOrigen(null);
            case "producto interés" -> req.setProductosInteres(List.of()); 
            case "email"          -> cr.setEmail(null);
        }

        try {
            leadRegistrado = leadService.registrar(req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("el registro debe ser rechazado por falta de información mandatoria")
    public void elRegistroDebeSerRechazadoPorFaltaDeInformacion() {
        assertThat(excepcionCapturada).isNotNull();
    }

    @Y("no debe crearse ningún vínculo con la campaña")
    public void noDebeCrearseNingunVinculo() {
        assertThat(leadRegistrado).isNull();
    }
}
