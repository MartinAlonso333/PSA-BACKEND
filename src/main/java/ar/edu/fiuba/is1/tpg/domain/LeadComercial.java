package ar.edu.fiuba.is1.tpg.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "lead_comercial", indexes = {
    @Index(name = "idx_lead_campania_id", columnList = "campania_id"),
    @Index(name = "idx_lead_canal_id", columnList = "canal_id"),
    @Index(name = "idx_lead_empresa_id", columnList = "empresa_id"),
    @Index(name = "idx_lead_etapa", columnList = "etapa")
})
public class LeadComercial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtapaPipeline etapa;

    @ManyToOne(optional = false)
    @JoinColumn(name = "canal_id", nullable = false)
    private Canal canal;

    @ManyToOne
    @JoinColumn(name = "canal_anterior_id")
    private Canal canalAnterior;

    @ManyToOne(optional = false)
    @JoinColumn(name = "campania_id", nullable = false)
    private Campania campania;

    @Enumerated(EnumType.STRING)
    @Column(name = "resultado_cierre")
    private ResultadoCierre resultadoCierre;

    @Column(name = "motivo_perdida")
    private String motivoPerdida;

    @ManyToMany
    @JoinTable(
        name = "lead_producto",
        joinColumns = @JoinColumn(name = "lead_id"),
        inverseJoinColumns = @JoinColumn(name = "producto_id")
    )
    private Set<Producto> productosInteres = new HashSet<>();

    @Column(name = "creado_en")
    private LocalDateTime creadoEn;

    @Column(name = "actualizado_en")
    private LocalDateTime actualizadoEn;

    protected LeadComercial() {}

    public LeadComercial(String nombre, Empresa empresa, Canal canal, Campania campania) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.canal = canal;
        this.campania = campania;
        this.etapa = EtapaPipeline.NUEVO;
        this.creadoEn = LocalDateTime.now();
        this.actualizadoEn = LocalDateTime.now();
    }

    public void avanzarEtapa(EtapaPipeline etapaDestino) {
        if (!this.etapa.esSiguienteValida(etapaDestino)) {
            throw new IllegalStateException(
                "Transición inválida de " + this.etapa + " a " + etapaDestino
            );
        }
        this.etapa = etapaDestino;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void cerrarComoGanado() {
        if (this.estaCerrado()) {
            throw new IllegalStateException("El lead ya está cerrado con resultado: " + this.resultadoCierre);
        }
        this.resultadoCierre = ResultadoCierre.GANADO;
        this.etapa = EtapaPipeline.CONVERTIDO;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void cerrarComoPerdido(String motivo) {
        if (this.estaCerrado()) {
            throw new IllegalStateException("El lead ya está cerrado con resultado: " + this.resultadoCierre);
        }
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("El motivo es obligatorio al marcar un lead como Perdido");
        }
        this.resultadoCierre = ResultadoCierre.PERDIDO;
        this.etapa = EtapaPipeline.PERDIDO;
        this.motivoPerdida = motivo;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void actualizarCanalOrigen(Canal nuevoCanal) {
        this.canalAnterior = this.canal;
        this.canal = nuevoCanal;
        this.actualizadoEn = LocalDateTime.now();
    }

    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setEmpresa(Empresa empresa) { this.empresa = empresa; }
    public void setCampania(Campania campania) { this.campania = campania; }
    public void marcarActualizado() { this.actualizadoEn = LocalDateTime.now(); }

    public void agregarProductoInteres(Producto producto) {
        this.productosInteres.add(producto);
    }

    public boolean estaCerrado() {
        return this.resultadoCierre != null;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public Empresa getEmpresa() { return empresa; }
    public EtapaPipeline getEtapa() { return etapa; }
    public Canal getCanal() { return canal; }
    public Canal getCanalAnterior() { return canalAnterior; }
    public Campania getCampania() { return campania; }
    public ResultadoCierre getResultadoCierre() { return resultadoCierre; }
    public String getMotivoPerdida() { return motivoPerdida; }
    public Set<Producto> getProductosInteres() { return productosInteres; }
    public LocalDateTime getCreadoEn() { return creadoEn; }
    public LocalDateTime getActualizadoEn() { return actualizadoEn; }
}