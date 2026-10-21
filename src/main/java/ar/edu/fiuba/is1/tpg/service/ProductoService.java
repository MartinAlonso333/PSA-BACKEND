package ar.edu.fiuba.is1.tpg.service;

import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.dto.ProductoResponse;
import ar.edu.fiuba.is1.tpg.exception.RecursoNoEncontradoException;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<ProductoResponse> listar() {
        return productoRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public ProductoResponse obtener(Long id) {
        Producto producto = productoRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + id + " no encontrado"));
        return toResponse(producto);
    }

    private ProductoResponse toResponse(Producto producto) {
        ProductoResponse r = new ProductoResponse();
        r.setId(producto.getId());
        r.setNombre(producto.getNombre());
        r.setCodigo(producto.getCodigo());
        return r;
    }
}
