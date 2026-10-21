package ar.edu.fiuba.is1.tpg.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CampaniaRequest {
    private String nombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private BigDecimal presupuesto;
    private BigDecimal costo;
    private BigDecimal roi;
    private String estado;
    private List<Long> productos;
     private List<Long> canales;

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public BigDecimal getPresupuesto() { return presupuesto; }
    public void setPresupuesto(BigDecimal presupuesto) { this.presupuesto = presupuesto; }
    public BigDecimal getCosto() { return costo; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public BigDecimal getRoi() { return roi; }
    public void setRoi(BigDecimal roi) { this.roi = roi; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public List<Long> getProductos() { return productos; }
    public void setProductos(List<Long> productos) { this.productos = productos; }
    public List<Long> getCanales() { return canales; }
    public void setCanales(List<Long> canales) { this.canales = canales; }
}
