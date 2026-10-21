package ar.edu.fiuba.is1.tpg.service;

import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.dto.CanalResponse;
import ar.edu.fiuba.is1.tpg.exception.RecursoNoEncontradoException;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CanalService {

    private final CanalRepository canalRepository;

    public CanalService(CanalRepository canalRepository) {
        this.canalRepository = canalRepository;
    }

    public List<CanalResponse> listar() {
        return canalRepository.findAll().stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
    }

    public CanalResponse obtener(Long id) {
        Canal canal = canalRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Canal con id " + id + " no encontrado"));
        return toResponse(canal);
    }

    private CanalResponse toResponse(Canal canal) {
        CanalResponse r = new CanalResponse();
        r.setId(canal.getId());
        r.setNombre(canal.getNombre());
        r.setTipo(canal.getTipo());
        return r;
    }
}
