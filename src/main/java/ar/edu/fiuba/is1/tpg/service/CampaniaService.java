package ar.edu.fiuba.is1.tpg.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;
import ar.edu.fiuba.is1.tpg.domain.Producto;
import ar.edu.fiuba.is1.tpg.dto.CampaniaRequest;
import ar.edu.fiuba.is1.tpg.dto.CampaniaResponse;
import ar.edu.fiuba.is1.tpg.dto.CampaniaUpdateRequest;
import ar.edu.fiuba.is1.tpg.exception.RecursoNoEncontradoException;
import ar.edu.fiuba.is1.tpg.exception.TransicionInvalidaException;
import ar.edu.fiuba.is1.tpg.repository.CampaniaRepository;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import ar.edu.fiuba.is1.tpg.repository.LeadComercialRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;

@Service
public class CampaniaService {

    private static final Logger log = LoggerFactory.getLogger(CampaniaService.class);

    private final CampaniaRepository campaniaRepository;
    private final ProductoRepository productoRepository;
    private final LeadComercialRepository leadRepository;
    private final CanalRepository canalRepository;
    private final VentasService ventasService;
    public CampaniaService(CampaniaRepository campaniaRepository,
                           ProductoRepository productoRepository,
                           LeadComercialRepository leadRepository,
                           CanalRepository canalRepository,
                           VentasService ventasService) {
        this.campaniaRepository = campaniaRepository;
        this.productoRepository = productoRepository;
        this.leadRepository = leadRepository;
        this.canalRepository = canalRepository;
        this.ventasService = ventasService;
    }

    @Transactional
    public CampaniaResponse registrar(CampaniaRequest request) {
        validarCampaniaRequest(request);

        EstadoCampania estado = parsearEstado(request.getEstado());
        Campania campania = new Campania(
            request.getNombre(),
            request.getFechaInicio(),
            request.getFechaFin(),
            request.getPresupuesto(),
            request.getCosto(),
            estado
        );

        for (Long productoId : request.getProductos()) {
            Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + productoId + " no encontrado"));
            campania.agregarProducto(producto);
        }
         if (request.getCanales() != null) {
            for (Long canalId : request.getCanales()) {
                Canal canal = canalRepository.findById(canalId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Canal con id " + canalId + " no encontrado"));
                campania.agregarCanal(canal);
            }
        }
        if (request.getRoi() != null) {
            campania.setRoi(request.getRoi());
        }

        Campania guardada = campaniaRepository.save(campania);
        return toResponse(guardada);
    }

    public List<CampaniaResponse> listar(String estadoFiltro) {
        List<Campania> campanias = estadoFiltro != null
            ? campaniaRepository.findByEstado(parsearEstado(estadoFiltro))
            : campaniaRepository.findAll();
        return campanias.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public CampaniaResponse obtener(Long id) {
        return toResponse(buscarPorId(id));
    }

    @Transactional
    public CampaniaResponse actualizar(Long id, CampaniaUpdateRequest request) {
        Campania campania = buscarPorId(id);

        if (request.getNombre() != null){
            campania.setNombre(request.getNombre());
        }
        if (request.getFechaInicio() != null){
            campania.setFechaInicio(request.getFechaInicio());
        }
        if (request.getFechaFin() != null){
            campania.setFechaFin(request.getFechaFin());
        }
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        if (request.getPresupuesto() != null){
            campania.setPresupuesto(request.getPresupuesto());
        }
        if (request.getCosto() != null){
            campania.setCosto(request.getCosto());
        }
        if (request.getRoi() != null){
            campania.setRoi(request.getRoi());
        }
        if (request.getCanales() != null) {
            campania.getCanales().clear();
            for (Long canalId : request.getCanales()) {
                Canal canal = canalRepository.findById(canalId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Canal con id " + canalId + " no encontrado"));
                campania.agregarCanal(canal);
            }
        }
        if (request.getProductos() != null) {
            campania.getProductos().clear();
            for (Long prodId : request.getProductos()) {
                Producto producto = productoRepository.findById(prodId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + prodId + " no encontrado"));
                campania.agregarProducto(producto);
            }
        }

        campania.marcarActualizado();

        Campania guardada = campaniaRepository.save(campania);
        return toResponse(guardada);
    }

    @Transactional
    public Map<String, Object> avanzarEstado(Long id, String nuevoEstadoStr) {
        Campania campania = buscarPorId(id);
        EstadoCampania estadoAnterior = campania.getEstado();
        EstadoCampania nuevoEstado = parsearEstado(nuevoEstadoStr);

        try {
            campania.avanzarEstado(nuevoEstado);
        } catch (IllegalStateException e) {
            throw new TransicionInvalidaException(e.getMessage());
        }

        campaniaRepository.save(campania);
        return Map.of(
            "id", campania.getId(),
            "estadoAnterior", estadoAnterior.name(),
            "estadoActual", campania.getEstado().name()
        );
    }

    public Map<String, Object> obtenerMetricas(Long id) {
        Campania campania = buscarPorId(id);
        List<LeadComercial> leads = leadRepository.findByCampaniaId(id);
        long leadsGenerados = leads.size();
        List<LeadComercial> leadsGanados = leads.stream()
            .filter(l -> l.getResultadoCierre() != null &&
                         l.getResultadoCierre().name().equals("GANADO"))
            .toList();
        long leadsConvertidos = leadsGanados.size();

        double conversion = leadsGenerados > 0
            ? (double) leadsConvertidos / leadsGenerados * 100
            : 0;

        BigDecimal montoGanadoTotal = BigDecimal.ZERO;
        BigDecimal roi = null;
        try {
            montoGanadoTotal = leadsGanados.stream()
                .map(l -> ventasService.obtenerMontoGanadoPorLead(l.getId()))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (campania.getCosto().compareTo(BigDecimal.ZERO) > 0 && montoGanadoTotal.compareTo(BigDecimal.ZERO) > 0) {
                roi = montoGanadoTotal.subtract(campania.getCosto())
                    .divide(campania.getCosto(), 4, RoundingMode.HALF_UP);
            }
            campania.setRoi(roi);
            campaniaRepository.save(campania);
        } catch (Exception e) {
            log.warn("Error al calcular ROI para campaña {}: {}", id, e.getMessage());
        }

        Map<String, Object> result = new java.util.HashMap<>();
        result.put("CampaniaId", id);
        result.put("leadsGenerados", leadsGenerados);
        result.put("leadsConvertidos", leadsConvertidos);
        result.put("conversion", BigDecimal.valueOf(conversion).setScale(2, RoundingMode.HALF_UP));
        result.put("montoGanadoTotal", montoGanadoTotal);
        result.put("roi", roi != null ? roi.setScale(4, RoundingMode.HALF_UP) : null);
        return result;
    }

    public Campania buscarPorId(Long id) {
        return campaniaRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Campaña con id " + id + " no encontrada"));
    }

    public Campania buscarPorNombre(String nombre) {
        return campaniaRepository.findByNombre(nombre)
            .orElseThrow(() -> new RecursoNoEncontradoException("Campaña '" + nombre + "' no encontrada"));
    }
    @Transactional
    public void eliminar(Long id) {
        Campania campania = buscarPorId(id);
        long leadsAsociados = leadRepository.findByCampaniaId(id).size();
        if (leadsAsociados > 0) {
            throw new IllegalStateException(
                "No se puede eliminar la campaña '" + campania.getNombre()
                + "' porque tiene " + leadsAsociados + " lead(s) asociado(s)"
            );
        }
        campaniaRepository.delete(campania);
    }

    private void validarCampaniaRequest(CampaniaRequest request) {
        if (request.getNombre() == null || request.getNombre().isBlank()){
            throw new IllegalArgumentException("El nombre de la campaña es obligatorio");
        }
        if (request.getPresupuesto() == null){
            throw new IllegalArgumentException("El presupuesto es obligatorio");
        }   
        if (request.getCosto() == null){
            throw new IllegalArgumentException("El costo es obligatorio");
        }
        if (request.getEstado() == null || request.getEstado().isBlank()){
            throw new IllegalArgumentException("El estado es obligatorio");
        }
        if (request.getProductos() == null || request.getProductos().isEmpty()){
            throw new IllegalArgumentException("La campaña debe tener al menos un producto asociado");
        }
        if (request.getFechaInicio() == null){
            throw new IllegalArgumentException("La fecha de inicio es obligatoria");
        }
        if (request.getFechaFin() == null){
            throw new IllegalArgumentException("La fecha de fin es obligatoria");
        }
        validarFechas(request.getFechaInicio(), request.getFechaFin());
        if (request.getCanales() == null || request.getCanales().isEmpty()){
            throw new IllegalArgumentException("La campaña debe tener al menos un canal asociado");
        }
    }

    private EstadoCampania parsearEstado(String estado) {
        try {
            return EstadoCampania.valueOf(estado.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Estado de campaña inválido: " + estado);
        }
    }
    
    CampaniaResponse toResponse(Campania c) {
        CampaniaResponse r = new CampaniaResponse();
        r.setId(c.getId());
        r.setNombre(c.getNombre());
        r.setFechaInicio(c.getFechaInicio());
        r.setFechaFin(c.getFechaFin());
        r.setPresupuesto(c.getPresupuesto());
        r.setCosto(c.getCosto());
        r.setRoi(c.getRoi());
        r.setEstado(c.getEstado().name());
        r.setProductos(c.getProductos().stream().map(Producto::getId).collect(Collectors.toList()));
        r.setFechaCreacion(c.getCreadoEn());
        r.setCanales(c.getCanales().stream().map(Canal::getId).collect(Collectors.toList()));
        return r;
    }

    // Metodo auxiliar para validar fechas
    private void validarFechas(LocalDate fechaInicio, LocalDate fechaFin) {
    if (fechaInicio == null || fechaFin == null) return;
    if (fechaFin.isBefore(fechaInicio))
        throw new IllegalArgumentException("La fecha de fin no puede ser anterior a la fecha de inicio");
    }
}
