package ar.edu.fiuba.is1.tpg.bdd.steps;

import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.dto.EmpresaRequest;
import ar.edu.fiuba.is1.tpg.dto.EmpresaResponse;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;
import ar.edu.fiuba.is1.tpg.service.EmpresaService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class HU26Steps {

    @Autowired private EmpresaService empresaService;
    @Autowired private EmpresaRepository empresaRepository;

    private List<EmpresaResponse> listaEmpresas;
    private EmpresaResponse empresaDetalle;
    private EmpresaResponse empresaCreada;
    private Exception excepcionCapturada;

    @Dado("que existen empresas registradas en el sistema")
    public void queExistenEmpresasRegistradas() {
        if (empresaRepository.count() == 0) {
            empresaRepository.save(new Empresa("TechCorp", "TechCorp S.A.", "30-12345678-9", "Av. Siempre Viva 123"));
            empresaRepository.save(new Empresa("InnovaSA", "Innova Soluciones S.A.", "30-23456789-0", "Calle Falsa 456"));
        }
    }

    @Cuando("se solicita el listado de empresas")
    public void seSolicitaListadoDeEmpresas() {
        listaEmpresas = empresaService.listar();
    }

    @Entonces("deben devolverse todas las empresas disponibles")
    public void debenDevolverseTodasLasEmpresas() {
        assertThat(listaEmpresas).isNotEmpty();
    }

    @Dado("que existe una empresa con nombre {string}")
    public void queExisteUnaEmpresaConNombre(String nombre) {
        empresaRepository.findByNombre(nombre).orElseGet(() ->
            empresaRepository.save(new Empresa(nombre, nombre + " S.A.", "30-00000000-0", "Dirección " + nombre)));
    }

    @Cuando("se solicita el detalle de la empresa {string}")
    public void seSolicitaDetalleDeLaEmpresa(String nombre) {
        Empresa e = empresaRepository.findByNombre(nombre).orElseThrow();
        try {
            empresaDetalle = empresaService.obtener(e.getId());
        } catch (Exception ex) {
            excepcionCapturada = ex;
        }
    }

    @Entonces("debe devolverse la información completa de la empresa")
    public void debeDevolverseInformacionCompleta() {
        assertThat(empresaDetalle).isNotNull();
        assertThat(empresaDetalle.getNombre()).isEqualTo("TechCorp");
    }

    @Y("debe incluir su razón social, CUIT y dirección")
    public void debeIncluirRazonSocialCuitDireccion() {
        assertThat(empresaDetalle.getRazonSocial()).isNotBlank();
        assertThat(empresaDetalle.getCuit()).isNotBlank();
        assertThat(empresaDetalle.getDireccion()).isNotBlank();
    }

    @Dado("que se desea registrar una nueva empresa")
    public void queSeDeseaRegistrarUnaNuevaEmpresa() {
        // no-op
    }

    @Cuando("se crea la empresa con los siguientes datos:")
    public void seCreaLaEmpresaConLosSiguientesDatos(DataTable dataTable) {
        EmpresaRequest req = new EmpresaRequest();
        for (Map<String, String> row : dataTable.asMaps()) {
            String campo = row.get("campo");
            String valor = row.get("valor");
            switch (campo) {
                case "nombre" -> req.setNombre(valor);
                case "razón social" -> req.setRazonSocial(valor);
                case "cuit" -> req.setCuit(valor);
                case "dirección" -> req.setDireccion(valor);
            }
        }
        try {
            empresaCreada = empresaService.crear(req);
        } catch (Exception e) {
            excepcionCapturada = e;
        }
    }

    @Entonces("la empresa debe ser creada exitosamente")
    public void laEmpresaDebeSerCreadaExitosamente() {
        assertThat(excepcionCapturada).isNull();
        assertThat(empresaCreada).isNotNull();
        assertThat(empresaCreada.getNombre()).isEqualTo("Nueva Empresa S.A.");
    }

    @Y("debe quedar disponible en el listado de empresas")
    public void debeQuedarDisponibleEnListado() {
        List<EmpresaResponse> listado = empresaService.listar();
        assertThat(listado).anyMatch(e -> e.getNombre().equals("Nueva Empresa S.A."));
    }
}
