package ar.edu.fiuba.is1.tpg.service;

import ar.edu.fiuba.is1.tpg.domain.*;
import ar.edu.fiuba.is1.tpg.dto.*;
import ar.edu.fiuba.is1.tpg.exception.CampaniaInactivaException;
import ar.edu.fiuba.is1.tpg.exception.RecursoNoEncontradoException;
import ar.edu.fiuba.is1.tpg.exception.TransicionInvalidaException;
import ar.edu.fiuba.is1.tpg.repository.CanalRepository;
import ar.edu.fiuba.is1.tpg.repository.ContactoRepository;
import ar.edu.fiuba.is1.tpg.repository.EmpresaRepository;
import ar.edu.fiuba.is1.tpg.repository.LeadComercialRepository;
import ar.edu.fiuba.is1.tpg.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LeadService {

    private final LeadComercialRepository leadRepository;
    private final ContactoRepository contactoRepository;
    private final ProductoRepository productoRepository;
    private final CanalRepository canalRepository;
    private final EmpresaRepository empresaRepository;
    private final CampaniaService campaniaService;

    public LeadService(LeadComercialRepository leadRepository,
                       ContactoRepository contactoRepository,
                       ProductoRepository productoRepository,
                       CanalRepository canalRepository,
                       EmpresaRepository empresaRepository,
                       CampaniaService campaniaService) {
        this.leadRepository = leadRepository;
        this.contactoRepository = contactoRepository;
        this.productoRepository = productoRepository;
        this.canalRepository = canalRepository;
        this.empresaRepository = empresaRepository;
        this.campaniaService = campaniaService;
    }

    private Canal resolverOCrearCanal(String nombreCanal) {
        return canalRepository.findByNombre(nombreCanal)
            .orElseGet(() -> canalRepository.save(new Canal(nombreCanal)));
    }

    @Transactional
    public LeadResponse registrar(LeadRequest request) {
        validarLeadRequest(request);

        Campania campania = campaniaService.buscarPorId(request.getCampaniaOrigenId());
        if (!campania.estaActiva()) {
            throw new CampaniaInactivaException(campania.getNombre());
        }

        Canal canal = resolverOCrearCanal(request.getCanalOrigen());

        Empresa empresa = null;
        if (request.getEmpresaId() != null) {
            empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa con id " + request.getEmpresaId() + " no encontrada"));
        }

        LeadComercial lead = new LeadComercial(
            request.getNombre(),
            empresa,
            canal,
            campania
        );

        for (Long productoId : request.getProductosInteres()) {
            Producto producto = productoRepository.findById(productoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + productoId + " no encontrado"));
            lead.agregarProductoInteres(producto);
        }

        LeadComercial guardado = leadRepository.save(lead);

        ContactoRequest contactoReq = request.getContacto();
        Contacto contacto = new Contacto(guardado, contactoReq.getEmail(),
            contactoReq.getTelefono());
        contactoRepository.save(contacto);

        return toResponse(guardado, contacto);
    }

    @Transactional(readOnly = true)
    public List<LeadResponse> listar(Long campaniaId, String canal, String etapa) {
        List<LeadComercial> leads;
        if (campaniaId != null) {
            leads = leadRepository.findByCampaniaId(campaniaId);
        } else if (canal != null) {
            Canal canalEntity = canalRepository.findByNombre(canal).orElse(null);
            leads = canalEntity != null
                ? leadRepository.findByCanal(canalEntity)
                : List.of();
        } else if (etapa != null) {
            leads = leadRepository.findByEtapa(EtapaPipeline.valueOf(etapa.toUpperCase()));
        } else {
            leads = leadRepository.findAll();
        }
        return leads.stream().map(l -> toResponse(l, null)).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public LeadResponse obtener(Long id) {
        LeadComercial lead = buscarPorId(id);
        return toResponse(lead, null);
    }

    @Transactional
    public LeadResponse actualizar(Long id, LeadUpdateRequest request) {
        LeadComercial lead = buscarPorId(id);

        if (request.getNombre() != null) {
            lead.setNombre(request.getNombre());
        }
        if (request.getQuitarEmpresa() != null && request.getQuitarEmpresa()) {
            lead.setEmpresa(null);
        } else if (request.getEmpresa() != null) {
            EmpresaRequest er = request.getEmpresa();
            Empresa empresa;
            if (lead.getEmpresa() != null) {
                empresa = lead.getEmpresa();
                empresa.setNombre(er.getNombre());
                if (er.getRazonSocial() != null) empresa.setRazonSocial(er.getRazonSocial());
                if (er.getCuit() != null) empresa.setCuit(er.getCuit());
                if (er.getDireccion() != null) empresa.setDireccion(er.getDireccion());
                empresaRepository.save(empresa);
            } else {
                empresa = new Empresa(er.getNombre(), er.getRazonSocial(), er.getCuit(), er.getDireccion());
                empresa = empresaRepository.save(empresa);
            }
            lead.setEmpresa(empresa);
        } else if (request.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(request.getEmpresaId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Empresa con id " + request.getEmpresaId() + " no encontrada"));
            lead.setEmpresa(empresa);
        }
        if (request.getCampaniaOrigenId() != null) {
            Campania nuevaCampania = campaniaService.buscarPorId(request.getCampaniaOrigenId());
            lead.setCampania(nuevaCampania);
        }
        if (request.getCanalOrigen() != null) {
            Canal nuevoCanal = canalRepository.findByNombre(request.getCanalOrigen())
                .orElseGet(() -> canalRepository.save(new Canal(request.getCanalOrigen())));
            lead.actualizarCanalOrigen(nuevoCanal);
        }
        if (request.getProductosInteres() != null) {
            lead.getProductosInteres().clear();
            for (Long productoId : request.getProductosInteres()) {
                Producto producto = productoRepository.findById(productoId)
                    .orElseThrow(() -> new RecursoNoEncontradoException("Producto con id " + productoId + " no encontrado"));
                lead.agregarProductoInteres(producto);
            }
        }
        if (request.getContacto() != null) {
            Contacto contacto = contactoRepository.findByLeadId(id).orElse(null);
            if (contacto != null) {
                if (request.getContacto().getEmail() != null)
                    contacto.setEmail(request.getContacto().getEmail());
                if (request.getContacto().getTelefono() != null)
                    contacto.setTelefono(request.getContacto().getTelefono());

                contactoRepository.save(contacto);
            }
        }

        lead.marcarActualizado();
        LeadComercial guardado = leadRepository.save(lead);
        Contacto c = contactoRepository.findByLeadId(id).orElse(null);
        return toResponse(guardado, c);
    }

    private EtapaPipeline parseEtapa(String nombreEtapa) {
        String normalizado = nombreEtapa.trim().toLowerCase();
        if (normalizado.equals("convertido en oportunidad")) {
            return EtapaPipeline.CONVERTIDO;
        }
        try {
            return EtapaPipeline.valueOf(nombreEtapa.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Etapa inválida: " + nombreEtapa);
        }
    }

    @Transactional
    public Map<String, Object> avanzarPipeline(Long id, AvancePipelineRequest request) {
        if (request.getEtapaSiguiente() == null || request.getEtapaSiguiente().isBlank()) {
            throw new IllegalArgumentException("La etapa siguiente es obligatoria para avanzar el pipeline");
        }

        LeadComercial lead = buscarPorId(id);
        EtapaPipeline etapaAnterior = lead.getEtapa();
        EtapaPipeline etapaDestino = parseEtapa(request.getEtapaSiguiente());

        try {
            lead.avanzarEtapa(etapaDestino);
        } catch (IllegalStateException e) {
            throw new TransicionInvalidaException(e.getMessage());
        }

        leadRepository.save(lead);
        return Map.of(
            "id", lead.getId(),
            "etapaAnterior", etapaAnterior.name(),
            "etapaActual", lead.getEtapa().name()
        );
    }

    @Transactional
    public Map<String, Object> cerrar(Long id, CierreLeadRequest request) {
        if (request.getResultado() == null || request.getResultado().isBlank()) {
            throw new IllegalArgumentException(
                "El resultado es obligatorio para cerrar un lead (Ganado o Perdido)"
            );
        }

        LeadComercial lead = buscarPorId(id);

        try {
            ResultadoCierre resultado = ResultadoCierre.valueOf(request.getResultado().toUpperCase());
            if (resultado == ResultadoCierre.GANADO) {
                lead.cerrarComoGanado();
            } else {
                lead.cerrarComoPerdido(request.getMotivo());
            }
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("No enum")) {
                throw new IllegalArgumentException("Resultado inválido: debe ser GANADO o PERDIDO");
            }
            throw e;
        }

        leadRepository.save(lead);
        return Map.of(
            "id", lead.getId(),
            "resultado", lead.getResultadoCierre().name(),
            "motivo", lead.getMotivoPerdida() != null ? lead.getMotivoPerdida() : "",
            "campaniaVinculada", lead.getCampania().getNombre()
        );
    }

    @Transactional
    public Map<String, Object> actualizarCanalOrigen(Long id, CanalOrigenRequest request) {
        if (request.getCanalOrigen() == null || request.getCanalOrigen().isBlank()) {
            throw new IllegalArgumentException("El canal de origen es obligatorio");
        }
        LeadComercial lead = buscarPorId(id);
        String anterior = lead.getCanal().getNombre();
        if (anterior.equals(request.getCanalOrigen())) {
            return Map.of(
                "id", lead.getId(),
                "canalOrigenAnterior", anterior,
                "canalOrigenActual", lead.getCanal().getNombre(),
                "mensaje", "El lead ya tiene asignado ese canal"
            );
        }
        Canal nuevoCanal = resolverOCrearCanal(request.getCanalOrigen());
        lead.actualizarCanalOrigen(nuevoCanal);
        leadRepository.save(lead);
        return Map.of(
            "id", lead.getId(),
            "canalOrigenAnterior", anterior,
            "canalOrigenActual", lead.getCanal().getNombre()
        );
    }

    public List<Map<String, Object>> reportePorCanal(Long campaniaId) {
        List<Object[]> rows = campaniaId != null
            ? leadRepository.contarPorCanalYCampania(campaniaId)
            : leadRepository.contarPorCanal();

        return rows.stream()
            .map(r -> Map.<String, Object>of("canal", r[0], "cantidad", r[1]))
            .collect(Collectors.toList());
    }

    @Transactional
    public void eliminar(Long id) {
        LeadComercial lead = buscarPorId(id);
        EtapaPipeline etapa = lead.getEtapa();
        if (etapa == EtapaPipeline.CONVERTIDO || etapa == EtapaPipeline.PERDIDO) {
            throw new IllegalStateException(
                "No se puede eliminar un lead en estado " + etapa
                + ". Solo se pueden eliminar leads en NUEVO, CONTACTADO o CALIFICADO"
            );
        }
        contactoRepository.findByLeadId(id).ifPresent(contactoRepository::delete);
        leadRepository.delete(lead);
    }

    public LeadComercial buscarPorId(Long id) {
        return leadRepository.findById(id)
            .orElseThrow(() -> new RecursoNoEncontradoException("Lead con id " + id + " no encontrado"));
    }

    private void validarLeadRequest(LeadRequest request) {
        if (request.getCampaniaOrigenId() == null)
            throw new IllegalArgumentException("La campaña origen es obligatoria");
        if (request.getNombre() == null || request.getNombre().isBlank())
            throw new IllegalArgumentException("El nombre del lead es obligatorio");
        if (request.getCanalOrigen() == null || request.getCanalOrigen().isBlank())
            throw new IllegalArgumentException("El canal de origen es obligatorio");
        if (request.getContacto() == null || request.getContacto().getEmail() == null || request.getContacto().getEmail().isBlank())
            throw new IllegalArgumentException("El email de contacto es obligatorio");
        if (request.getProductosInteres() == null || request.getProductosInteres().isEmpty())
            throw new IllegalArgumentException("Debe especificarse al menos un producto de interés");
    }

    LeadResponse toResponse(LeadComercial l, Contacto contacto) {
        LeadResponse r = new LeadResponse();
        r.setId(l.getId());
        r.setCampaniaOrigenId(l.getCampania().getId());
        r.setCampaniaNombre(l.getCampania().getNombre());
        if (l.getEmpresa() != null) {
            EmpresaResponse er = new EmpresaResponse();
            er.setId(l.getEmpresa().getId());
            er.setNombre(l.getEmpresa().getNombre());
            er.setRazonSocial(l.getEmpresa().getRazonSocial());
            er.setCuit(l.getEmpresa().getCuit());
            er.setDireccion(l.getEmpresa().getDireccion());
            r.setEmpresa(er);
        }
        r.setNombre(l.getNombre());
        r.setCanalOrigen(l.getCanal().getNombre());
        r.setEtapa(l.getEtapa().name());
        if (l.getResultadoCierre() != null)
            r.setResultadoCierre(l.getResultadoCierre().name());
        r.setMotivoPerdida(l.getMotivoPerdida());
        r.setProductosInteres(l.getProductosInteres().stream()
            .map(Producto::getId).collect(Collectors.toList()));
        r.setFechaCreacion(l.getCreadoEn());
        if (contacto != null) {
            ContactoRequest c = new ContactoRequest();
            c.setEmail(contacto.getEmail());
            c.setTelefono(contacto.getTelefono());
            r.setContacto(c);
        } else {
            Contacto contactoOpt = contactoRepository.findByLeadId(l.getId()).orElse(null);
            if (contactoOpt != null) {
                ContactoRequest c = new ContactoRequest();
                c.setEmail(contactoOpt.getEmail());
                c.setTelefono(contactoOpt.getTelefono());
                r.setContacto(c);
            }
        }
        return r;
    }
}
