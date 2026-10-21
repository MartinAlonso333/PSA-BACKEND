package ar.edu.fiuba.is1.tpg.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class VentasService {

    private static final Logger log = LoggerFactory.getLogger(VentasService.class);

    private final RestTemplate restTemplate;
    private final String ventasBaseUrl;

    public VentasService(RestTemplate restTemplate,
                         @Value("${app.ventas.base-url:https://two026-1c-tpg-squad-3.onrender.com}") String ventasBaseUrl) {
        this.restTemplate = restTemplate;
        this.ventasBaseUrl = ventasBaseUrl;
    }

    public Map<String, Object> obtenerOportunidadPorLead(Long leadId) {
        String url = ventasBaseUrl + "/oportunidades/lead/" + leadId;
        try {
            ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
            return response.getBody();
        } catch (ResourceAccessException e) {
            log.warn("Backend de Ventas no disponible para lead {}: {}", leadId, e.getMessage());
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("leadId", leadId);
            fallback.put("montoGanado", null);
            fallback.put("estado", "NO_DISPONIBLE");
            fallback.put("mensaje", "Backend de Ventas no disponible");
            return fallback;
        } catch (Exception e) {
            log.warn("Error al consultar oportunidad para lead {}: {}", leadId, e.getMessage());
            Map<String, Object> fallback = new HashMap<>();
            fallback.put("leadId", leadId);
            fallback.put("montoGanado", null);
            fallback.put("estado", "ERROR");
            fallback.put("mensaje", e.getMessage());
            return fallback;
        }
    }

    public BigDecimal obtenerMontoGanadoPorLead(Long leadId) {
        Map<String, Object> oportunidad = obtenerOportunidadPorLead(leadId);
        Object monto = oportunidad.get("montoGanado");
        if (monto == null) return BigDecimal.ZERO;
        if (monto instanceof BigDecimal) return (BigDecimal) monto;
        if (monto instanceof Number) return BigDecimal.valueOf(((Number) monto).doubleValue());
        try {
            return new BigDecimal(monto.toString());
        } catch (Exception e) {
            return BigDecimal.ZERO;
        }
    }
}
