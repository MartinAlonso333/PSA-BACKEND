package ar.edu.fiuba.is1.tpg.bdd.steps;

import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.dto.CanalResponse;
import ar.edu.fiuba.is1.tpg.dto.ProductoResponse;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import ar.edu.fiuba.is1.tpg.service.CanalService;
import ar.edu.fiuba.is1.tpg.service.ProductoService;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HU25Steps {

    @Autowired private ProductoService productService;
    @Autowired private CanalService canalService;
    @Autowired private ProductoRepository productRepository;
    @Autowired private CanalRepository canalRepository;

    private List<ProductoResponse> listaProductos;
    private ProductoResponse productoDetalle;
    private List<CanalResponse> listaCanales;
    private CanalResponse canalDetalle;
    private Exception excepcionCapturada;

    @Dado("que existen productos registrados en el sistema")
    public void queExistenProductosRegistrados() {
        if (productRepository.count() == 0) {
            productRepository.save(new Producto("CRM Básico BDD", "CRM-BDD-001"));
            productRepository.save(new Producto("CRM Premium BDD", "CRM-BDD-002"));
        }
    }

    @Cuando("se solicita el listado de productos")
    public void seSolicitaListadoDeProductos() {
        listaProductos = productService.listar();
    }

    @Entonces("deben devolverse todos los productos disponibles")
    public void debenDevolverseTodosLosProductos() {
        assertThat(listaProductos).isNotEmpty();
    }

    @Y("cada producto debe incluir su nombre y código")
    public void cadaProductoDebeIncluirNombreYCodigo() {
        for (ProductoResponse p : listaProductos) {
            assertThat(p.getNombre()).isNotBlank();
            assertThat(p.getCodigo()).isNotBlank();
        }
    }

    @Dado("que existe un producto con nombre {string}")
    public void queExisteUnProductoConNombre(String nombre) {
        productRepository.findByNombre(nombre).orElseGet(() ->
            productRepository.save(new Producto(nombre, nombre.toLowerCase().replace(" ", "-") + "-" + System.nanoTime())));
    }

    @Cuando("se solicita el detalle del producto {string}")
    public void seSolicitaDetalleDelProducto(String nombre) {
        Producto p = productRepository.findByNombre(nombre).orElseThrow();
        try {
            productoDetalle = productService.obtener(p.getId());
        } catch (Exception e) {
            excepcionCapturada = e;
            productoDetalle = null;
        }
    }

    @Entonces("debe devolverse la información del producto")
    public void debeDevolverseInformacionDelProducto() {
        assertThat(excepcionCapturada).as("Error al obtener detalle: " + excepcionCapturada).isNull();
        assertThat(productoDetalle).isNotNull();
    }

    @Y("debe incluir su nombre y código")
    public void debeIncluirNombreYCodigo() {
        assertThat(productoDetalle.getNombre()).isNotBlank();
        assertThat(productoDetalle.getCodigo()).isNotBlank();
    }

    @Dado("que existen canales registrados en el sistema")
    public void queExistenCanalesRegistrados() {
        if (canalRepository.count() == 0) {
            canalRepository.save(new Canal("Email BDD", "DIGITAL"));
            canalRepository.save(new Canal("Teléfono BDD", "TELEFONICO"));
        }
    }

    @Cuando("se solicita el listado de canales")
    public void seSolicitaListadoDeCanales() {
        listaCanales = canalService.listar();
    }

    @Entonces("deben devolverse todos los canales disponibles")
    public void debenDevolverseTodosLosCanales() {
        assertThat(listaCanales).isNotEmpty();
    }

    @Dado("que existe un canal con nombre {string}")
    public void queExisteUnCanalConNombre(String nombre) {
        canalRepository.findByNombre(nombre).orElseGet(() ->
            canalRepository.save(new Canal(nombre, "DIGITAL")));
    }

    @Cuando("se solicita el detalle del canal {string}")
    public void seSolicitaDetalleDelCanal(String nombre) {
        Canal c = canalRepository.findByNombre(nombre).orElseThrow();
        try {
            canalDetalle = canalService.obtener(c.getId());
        } catch (Exception e) {
            excepcionCapturada = e;
            canalDetalle = null;
        }
    }

    @Entonces("debe devolverse la información del canal")
    public void debeDevolverseInformacionDelCanal() {
        assertThat(excepcionCapturada).as("Error al obtener detalle: " + excepcionCapturada).isNull();
        assertThat(canalDetalle).isNotNull();
        assertThat(canalDetalle.getNombre()).isEqualTo("Email BDD");
    }
}
