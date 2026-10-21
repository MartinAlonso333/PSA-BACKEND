package ar.edu.fiuba.is1.tpg.bdd.steps;

import ar.edu.fiuba.is1.tpg.domain.*;
import ar.edu.fiuba.is1.tpg.repository.*;
import io.cucumber.java.es.Dado;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CommonMarketingSteps {

    @Autowired private CampaniaRepository campaniaRepository;
    @Autowired private ProductoRepository productoRepository;

    @Dado("que existe una campaña activa denominada {string}")
    public void queExisteUnaCampaniaActivaDenominada(String nombre) {
        campaniaRepository.findByNombre(nombre).orElseGet(() -> {
            Producto p = productoRepository.save(
                new Producto("PSA Soporte", "psa-soporte-" + System.nanoTime())
            );
            Campania c = new Campania(nombre, LocalDate.now(),
                LocalDate.now().plusMonths(1), BigDecimal.valueOf(100000),
                BigDecimal.valueOf(80000), EstadoCampania.ACTIVA);
            c.agregarProducto(p);
            return campaniaRepository.save(c);
        });
    }
}