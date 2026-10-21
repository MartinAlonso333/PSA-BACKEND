package ar.edu.fiuba.is1.tpg.dto;

import java.util.List;

public class LeadUpdateRequest {
    private String nombre;
    private Long empresaId;
    private Boolean quitarEmpresa;
    private EmpresaRequest empresa;
    private Long campaniaOrigenId;
    private String canalOrigen;
    private ContactoRequest contacto;
    private List<Long> productosInteres;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Long getEmpresaId() { return empresaId; }
    public void setEmpresaId(Long empresaId) { this.empresaId = empresaId; }
    public Boolean getQuitarEmpresa() { return quitarEmpresa; }
    public void setQuitarEmpresa(Boolean quitarEmpresa) { this.quitarEmpresa = quitarEmpresa; }
    public EmpresaRequest getEmpresa() { return empresa; }
    public void setEmpresa(EmpresaRequest empresa) { this.empresa = empresa; }
    public Long getCampaniaOrigenId() { return campaniaOrigenId; }
    public void setCampaniaOrigenId(Long campaniaOrigenId) { this.campaniaOrigenId = campaniaOrigenId; }
    public String getCanalOrigen() { return canalOrigen; }
    public void setCanalOrigen(String canalOrigen) { this.canalOrigen = canalOrigen; }
    public ContactoRequest getContacto() { return contacto; }
    public void setContacto(ContactoRequest contacto) { this.contacto = contacto; }
    public List<Long> getProductosInteres() { return productosInteres; }
    public void setProductosInteres(List<Long> productosInteres) { this.productosInteres = productosInteres; }
}
