package ar.edu.fiuba.is1.tpg.bdd;

import ar.edu.fiuba.is1.tpg.repository.*;
import io.cucumber.java.Before;
import org.springframework.beans.factory.annotation.Autowired;

public class DatabaseCleanupHook {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private LeadComercialRepository leadRepository;

    @Autowired
    private CampaniaRepository CampaniaRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Before
    public void limpiarBaseDeDatos() {
        contactoRepository.deleteAll();
        leadRepository.deleteAll();
        CampaniaRepository.deleteAll();
        productoRepository.deleteAll();
    }
}