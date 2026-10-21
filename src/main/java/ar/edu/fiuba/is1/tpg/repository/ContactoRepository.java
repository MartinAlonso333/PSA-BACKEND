package ar.edu.fiuba.is1.tpg.repository;

import ar.edu.fiuba.is1.tpg.domain.Contacto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface ContactoRepository extends JpaRepository<Contacto, Long> {
    Optional<Contacto> findById(Long id);
    Optional<Contacto> findByLeadId(Long leadId);

}