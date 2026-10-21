package ar.edu.fiuba.is1.tpg.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "empresa")
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(unique = true)
    private String cuit;

    private String direccion;

    protected Empresa() {}

    public Empresa(String nombre) {
        this.nombre = nombre;
    }

    public Empresa(String nombre, String razonSocial, String cuit, String direccion) {
        this.nombre = nombre;
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.direccion = direccion;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRazonSocial() { return razonSocial; }
    public void setRazonSocial(String razonSocial) { this.razonSocial = razonSocial; }
    public String getCuit() { return cuit; }
    public void setCuit(String cuit) { this.cuit = cuit; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}
