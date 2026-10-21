package ar.edu.fiuba.is1.tpg.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "campania", indexes = {
    @Index(name = "idx_campania_nombre", columnList = "nombre"),
    @Index(name = "idx_campania_estado", columnList = "estado"),
    @Index(name = "idx_campania_fecha_inicio", columnList = "fecha_inicio"),
    @Index(name = "idx_campania_fecha_fin", columnList = "fecha_fin")
})
public class Campania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private LocalDate fechaFin;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal presupuesto;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal costo;

    @Column(precision = 12, scale = 2)
    private BigDecimal roi;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCampania estado;

    @ManyToMany
    @JoinTable(
        name = "campania_producto",
        joinColumns = @JoinColumn(name = "campania_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private Set<Producto> productos = new HashSet<>();

    @ManyToMany
    @JoinTable(
        name = "campania_canal",
        joinColumns = @JoinColumn(name = "campania_id"),
        inverseJoinColumns = @JoinColumn(name = "canal_id")
    )
    private Set<Canal> canales = new HashSet<>();

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    protected Campania() {}

    public Campania(String nombre, LocalDate fechaInicio, LocalDate fechaFin,
                   BigDecimal presupuesto, BigDecimal costo, EstadoCampania estado) {
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.presupuesto = presupuesto;
        this.costo = costo;
        this.estado = estado;
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public void setFechaFin(LocalDate fechaFin) { this.fechaFin = fechaFin; }
    public void setPresupuesto(BigDecimal presupuesto) { this.presupuesto = presupuesto; }
    public void setCosto(BigDecimal costo) { this.costo = costo; }
    public void setRoi(BigDecimal roi) { this.roi = roi; }
    public void marcarActualizado() { this.actualizadoEn = LocalDateTime.now(); }

    public void agregarProducto(Producto producto) {
        this.productos.add(producto);
    }

    public void agregarCanal(Canal canal) {
        this.canales.add(canal);
    }

    public void avanzarEstado(EstadoCampania nuevoEstado) {
        if (!this.estado.esSiguienteValida(nuevoEstado)) {
            throw new IllegalStateException(
                "Transición inválida de " + this.estado + " a " + nuevoEstado
            );
        }
        this.estado = nuevoEstado;
        this.actualizadoEn = LocalDateTime.now();
    }

    public boolean estaActiva() {
        return this.estado == EstadoCampania.ACTIVA;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public BigDecimal getPresupuesto() { return presupuesto; }
    public BigDecimal getCosto() { return costo; }
    public BigDecimal getRoi() { return roi; }
    public EstadoCampania getEstado() { return estado; }
    public Set<Producto> getProductos() { return productos; }
    public Set<Canal> getCanales() { return canales; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
}