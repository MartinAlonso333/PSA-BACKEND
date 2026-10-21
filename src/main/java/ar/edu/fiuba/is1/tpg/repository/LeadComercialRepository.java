package ar.edu.fiuba.is1.tpg.repository;

import ar.edu.fiuba.is1.tpg.domain.Canal;
import ar.edu.fiuba.is1.tpg.domain.Empresa;
import ar.edu.fiuba.is1.tpg.domain.EtapaPipeline;
import ar.edu.fiuba.is1.tpg.domain.LeadComercial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeadComercialRepository extends JpaRepository<LeadComercial, Long> {
    List<LeadComercial> findByCampaniaId(Long campaniaId);
    List<LeadComercial> findByCanal(Canal canal);
    List<LeadComercial> findByEtapa(EtapaPipeline etapa);
    List<LeadComercial> findByEmpresa(Empresa empresa);

    @Query("SELECT l.canal.nombre, COUNT(l) FROM LeadComercial l GROUP BY l.canal.nombre")
    List<Object[]> contarPorCanal();

    // CORREGIDO: "l.campania.id" en lugar de "l.Campania.id"
    @Query("SELECT l.canal.nombre, COUNT(l) FROM LeadComercial l WHERE l.campania.id = :campaniaId GROUP BY l.canal.nombre")
    List<Object[]> contarPorCanalYCampania(@Param("campaniaId") Long campaniaId);
}