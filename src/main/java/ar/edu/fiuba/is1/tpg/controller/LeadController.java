package ar.edu.fiuba.is1.tpg.controller;

import ar.edu.fiuba.is1.tpg.dto.*;
import ar.edu.fiuba.is1.tpg.service.LeadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Tag(name = "Leads", description = "Gestión de leads en el pipeline de marketing")
@RestController
@RequestMapping("/api/leads")
public class LeadController {

    private final LeadService leadService;

    public LeadController(LeadService leadService) {
        this.leadService = leadService;
    }

    @Operation(summary = "Crear un nuevo lead asociado a una campaña activa")
    @PostMapping
    public ResponseEntity<LeadResponse> registrar(@RequestBody LeadRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.registrar(request));
    }

    @Operation(summary = "Listar leads con filtros opcionales")
    @GetMapping
    public ResponseEntity<List<LeadResponse>> listar(
            @RequestParam(required = false) Long campaniaId,
            @RequestParam(required = false) String canal,
            @RequestParam(required = false) String etapa) {
        return ResponseEntity.ok(leadService.listar(campaniaId, canal, etapa));
    }

    @Operation(summary = "Obtener detalle de un lead")
    @GetMapping("/{id}")
    public ResponseEntity<LeadResponse> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.obtener(id));
    }

    @Operation(summary = "Actualizar datos de un lead")
    @PutMapping("/{id}")
    public ResponseEntity<LeadResponse> actualizar(
            @PathVariable Long id,
            @RequestBody LeadUpdateRequest request) {
        return ResponseEntity.ok(leadService.actualizar(id, request));
    }

    @Operation(summary = "Actualizar el canal de origen de un lead")
    @PatchMapping("/{id}/canal-origen")
    public ResponseEntity<Map<String, Object>> actualizarCanalOrigen(
            @PathVariable Long id,
            @RequestBody CanalOrigenRequest request) {
        return ResponseEntity.ok(leadService.actualizarCanalOrigen(id, request));
    }

    @Operation(summary = "Avanzar la etapa del lead en el pipeline de forma secuencial")
    @PatchMapping("/{id}/pipeline")
    public ResponseEntity<Map<String, Object>> avanzarPipeline(
            @PathVariable Long id,
            @RequestBody AvancePipelineRequest request) {
        return ResponseEntity.ok(leadService.avanzarPipeline(id, request));
    }

    @Operation(summary = "Registrar el cierre del lead con resultado Ganado o Perdido")
    @PatchMapping("/{id}/cierre")
    public ResponseEntity<Map<String, Object>> cerrar(
            @PathVariable Long id,
            @RequestBody CierreLeadRequest request) {
        return ResponseEntity.ok(leadService.cerrar(id, request));
    }

    @Operation(summary = "Reporte de leads agrupados por canal de origen")
    @GetMapping("/reporte/por-canal")
    public ResponseEntity<List<Map<String, Object>>> reportePorCanal(
            @RequestParam(required = false) Long campaniaId) {
        return ResponseEntity.ok(leadService.reportePorCanal(campaniaId));
    }

    @Operation(summary = "Eliminar un lead (solo si está en NUEVO, CONTACTADO o CALIFICADO)")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        leadService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}