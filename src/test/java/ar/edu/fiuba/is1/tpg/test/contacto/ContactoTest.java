package ar.edu.fiuba.is1.tpg.test.contacto;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.Contacto;
import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;

class ContactoTest {

    private LeadComercial lead;

    @BeforeEach
    void setUp() {
        Campania Campania = new Campania(
            "Black Friday PSA 2025",
            LocalDate.of(2025, 11, 1),
            LocalDate.of(2025, 11, 30),
            BigDecimal.valueOf(150000),
            BigDecimal.valueOf(120000),
            EstadoCampania.ACTIVA
        );
        Canal canal = new Canal("LinkedIn", "Red social");
        Empresa empresa = new Empresa("Tech SRL");
        lead = new LeadComercial("Juan Pérez", empresa, canal, Campania);
    }

    @Test
    void contactoGuardaEmailCorrectamente() {
        Contacto contacto = new Contacto(lead, "juan@techsrl.com", "1134567890");

        assertThat(contacto.getEmail()).isEqualTo("juan@techsrl.com");
    }

    @Test
    void contactoGuardaTelefonoCorrectamente() {
        Contacto contacto = new Contacto(lead, "juan@techsrl.com", "1134567890");

        assertThat(contacto.getTelefono()).isEqualTo("1134567890");
    }

    @Test
    void contactoAdmiteTelefonoNulo() {
        Contacto contacto = new Contacto(lead, "juan@techsrl.com", null);

        assertThat(contacto.getTelefono()).isNull();
    }
}
