package ar.edu.fiuba.is1.tpg.dto;

import java.time.LocalDateTime;
import java.util.List;

public class LeadResponse {
    private Long id;
    private Long campaniaOrigenId;
    private String campaniaNombre;
    private EmpresaResponse empresa;
    private String nombre;
    private String canalOrigen;
    private String etapa;
    private String resultadoCierre;
    private String motivoPerdida;
    private ContactoRequest contacto;
    private List<Long> productosInteres;
    private LocalDateTime fechaCreacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getCampaniaOrigenId() { return campaniaOrigenId; }
    public void setCampaniaOrigenId(Long campaniaOrigenId) { this.campaniaOrigenId = campaniaOrigenId; }
    public String getCampaniaNombre() { return campaniaNombre; }
    public void setCampaniaNombre(String campaniaNombre) { this.campaniaNombre = campaniaNombre; }
    public EmpresaResponse getEmpresa() { return empresa; }
    public void setEmpresa(EmpresaResponse empresa) { this.empresa = empresa; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCanalOrigen() { return canalOrigen; }
    public void setCanalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; }
    public String getEtapa() { return etapa; }
    public void setEtapa(String etapa) { this.etapa = etapa; }
    public String getResultadoCierre() { return resultadoCierre; }
    public void setResultadoCierre(String resultadoCierre) { this.resultadoCierre = resultadoCierre; }
    public String getMotivoPerdida() { return motivoPerdida; }
    public void setMotivoPerdida(String motivoPerdida) { this.motivoPerdida = motivoPerdida; }
    public ContactoRequest getContacto() { return contacto; }
    public void setContacto(ContactoRequest contacto) { this.contacto = contacto; }
    public List<Long> getProductosInteres() { return productosInteres; }
    public void setProductosInteres(List<Long> productosInteres) { this.productosInteres = productosInteres; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDateTime fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
