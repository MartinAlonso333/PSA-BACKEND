package ar.edu.fiuba.is1.tpg.dto;

import java.util.List;

public class LeadRequest {
    private Long campaniaOrigenId;
    private Long empresaId;
    private String nombre;
    private String canalOrigen;
    private ContactoRequest contacto;
    private List<Long> productosInteres;

    public Long getCampaniaOrigenId() { return campaniaOrigenId; }
    public void setCampaniaOrigenId(Long campaniaOrigenId) { this.campaniaOrigenId = campaniaOrigenId; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getCanalOrigen() { return canalOrigen; }
    public void setCanalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; }
    public ContactoRequest getContacto() { return contacto; }
    public void setContacto(ContactoRequest contacto) { this.contacto = contacto; }
    public List<Long> getProductosInteres() { return productosInteres; }
    public void setProductosInteres(List<Long> productosInteres) { this.productosInteres = productosInteres; }
}
