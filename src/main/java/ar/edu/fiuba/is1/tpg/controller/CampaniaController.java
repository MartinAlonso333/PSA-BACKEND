package ar.edu.fiuba.is1.tpg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ar.edu.fiuba.is1.tpg.dto.CampaniaRequest;
import ar.edu.fiuba.is1.tpg.dto.CampaniaResponse;
import ar.edu.fiuba.is1.tpg.dto.CampaniaUpdateRequest;
import ar.edu.fiuba.is1.tpg.service.CampaniaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Campañas", description = "Gestión de campañas de marketing")
@RestController
@RequestMapping("/api/campanias")
public class CampaniaController {

    private final CampaniaService campaniaService;

    public CampaniaController(CampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    @Operation(summary = "Registrar una nueva campaña")
    @PostMapping
    public ResponseEntity<CampaniaResponse> registrar(@RequestBody CampaniaRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(campaniaService.registrar(request));
    }

    @Operation(summary = "Listar campañas con filtro opcional por estado")
    @GetMapping
    public ResponseEntity<List<CampaniaResponse>> listar(
            @RequestParam(required = false) String estado) {
        return ResponseEntity.ok(campaniaService.listar(estado));
    }

    @Operation(summary = "Obtener detalle de una campaña")
    @GetMapping("/{id}")
    public ResponseEntity<CampaniaResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(campaniaService.obtener(id));
    }

    @Operation(summary = "Actualizar datos de una campaña")
    @PutMapping("/{id}")
    public ResponseEntity<CampaniaResponse> actualizar(
            @PathVariable Long id,
            @RequestBody CampaniaUpdateRequest request) {
        return ResponseEntity.ok(campaniaService.actualizar(id, request));
    }

    @Operation(summary = "Avanzar el estado de una campaña")
    @PatchMapping("/{id}/estado")
    public ResponseEntity<Map<String, Object>> avanzarEstado(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        return ResponseEntity.ok(campaniaService.avanzarEstado(id, body.get("nuevoEstado")));
    }

    @Operation(summary = "Obtener métricas derivadas de efectividad de una campaña")
    @GetMapping("/{id}/metricas")
    public ResponseEntity<Map<String, Object>> obtenerMetricas(@PathVariable Long id) {
        return ResponseEntity.ok(campaniaService.obtenerMetricas(id));
    }
    @Operation(summary = "Eliminar una campaña")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        campaniaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}