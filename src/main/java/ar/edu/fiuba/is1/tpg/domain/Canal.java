package ar.edu.fiuba.is1.tpg.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "canal")
public class Canal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    private String tipo;

    protected Canal() {}

    public Canal(String nombre, String tipo) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Canal(String nombre) {
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getTipo() { return tipo; }
}
