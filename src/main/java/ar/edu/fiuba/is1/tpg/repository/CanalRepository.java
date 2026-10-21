package ar.edu.fiuba.is1.tpg.repository;

import ar.edu.fiuba.is1.tpg.domain.Canal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Long> {
    Optional<Canal> findByNombre(String nombre);
}
