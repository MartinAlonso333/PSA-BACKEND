package ar.edu.fiuba.is1.tpg.service;

import java.util.List;

import org.springframework.stereotype.Service;

import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.dto.EmpresaRequest;
import ar.edu.fiuba.is1.tpg.dto.EmpresaResponse;
import ar.edu.fiuba.is1.tpg.exception.RecursoNoEncontradoException;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<EmpresaResponse> listar() {
        return empresaRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }
    public EmpresaResponse obtener(Long id) {
    Empresa empresa = empresaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Empresa no encontrada con id: " + id));
    return toResponse(empresa);
    }
    public EmpresaResponse crear(EmpresaRequest request) {
        String nombre = request.getNombre();
        String razonSocial = request.getRazonSocial();
        String cuit = request.getCuit();
        String direccion = request.getDireccion();
    // Si viene CUIT, buscar si ya existe y devolverla
    if (cuit != null && !cuit.isBlank()) {
        return empresaRepository.findByCuit(cuit)
                .map(this::toResponse)
                .orElseGet(() -> {
                    Empresa empresa = new Empresa(nombre, razonSocial, cuit, direccion);
                    return toResponse(empresaRepository.save(empresa));
                });
    }
    Empresa empresa = new Empresa(nombre, razonSocial, cuit, direccion);
    return toResponse(empresaRepository.save(empresa));
    }
    
    private EmpresaResponse toResponse(Empresa e) {
        EmpresaResponse r = new EmpresaResponse();
        r.setId(e.getId());
        r.setNombre(e.getNombre());
        r.setRazonSocial(e.getRazonSocial());
        r.setCuit(e.getCuit());
        r.setDireccion(e.getDireccion());
        return r;
    }
}