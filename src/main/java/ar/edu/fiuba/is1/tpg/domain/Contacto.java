package ar.edu.fiuba.is1.tpg.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "contacto")
public class Contacto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "lead_id", nullable = false, unique = true)
    private LeadComercial lead;

    @Column(nullable = false)
    private String email;

    private String telefono;

    protected Contacto() {}

    public Contacto(LeadComercial lead, String email, String telefono) {
        this.lead = lead;
        this.email = email;
        this.telefono = telefono;
    }

    public Long getId() { return id; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    public void setEmail(String email) { this.email = email; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}