package ar.edu.fiuba.is1.tpg.repository;

import ar.edu.fiuba.is1.tpg.domain.Campania;
import ar.edu.fiuba.is1.tpg.domain.EstadoCampania;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CampaniaRepository extends JpaRepository<Campania, Long> {
    List<Campania> findByEstado(EstadoCampania estado);
    Optional<Campania> findByNombre(String nombre);
}
