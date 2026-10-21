package ar.edu.fiuba.is1.tpg.controller;

import ar.edu.fiuba.is1.tpg.dto.CanalResponse;
import ar.edu.fiuba.is1.tpg.service.CanalService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Canales", description = "Consulta de canales")
@RestController
@RequestMapping("/api/canales")
public class CanalController {

    private final CanalService canalService;

    public CanalController(CanalService canalService) {
        this.canalService = canalService;
    }

    @Operation(summary = "Listar todos los canales")
    @GetMapping
    public ResponseEntity<List<CanalResponse>> listar() {
        return ResponseEntity.ok(canalService.listar());
    }

    @Operation(summary = "Obtener un canal por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CanalResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(canalService.obtener(id));
    }
}
