package com.rca.RCA.repository;

import com.rca.RCA.entity.EvaluacionEntity;
import com.rca.RCA.entity.UsuarioEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EvaluacionRepository extends JpaRepository<EvaluacionEntity, Integer> {

    //Obtener evaluaciones por docentexCurso o por alumno
    @Query(value = "select e from EvaluacionEntity e JOIN e.alumnoEntity a JOIN e.docentexCursoEntity dc " +
            "JOIN e.periodoEntity p " +
            "WHERE a = e.alumnoEntity and dc = e.docentexCursoEntity and p = e.periodoEntity " +
            "and e.status = :status and a.status = :status and dc.status = :status " +
            "and (a.code like concat('%', :filter, '%') or dc.code like concat('%', :filter, '%') or " +
            "a.uniqueIdentifier like concat('%', :filter, '%'))")
    Optional<List<EvaluacionEntity>> findEntities(String status, String filter, Pageable pageable);

    @Query(value = "select count(e) from EvaluacionEntity e JOIN e.alumnoEntity a JOIN e.docentexCursoEntity dc " +
            "JOIN e.periodoEntity p "+
            "WHERE a = e.alumnoEntity and dc = e.docentexCursoEntity and p = e.periodoEntity " +
            "and e.status = :status and a.status = :status and dc.status = :status " +
            "and (a.code like concat('%', :filter, '%') or dc.code like concat('%', :filter, '%') or " +
            "a.uniqueIdentifier like concat('%', :filter, '%'))")
    Long findCountEntities(String status, String filter);


    Optional<EvaluacionEntity> findByUniqueIdentifier(String uniqueIdentifier);

    @Query(value = "SELECT e FROM PeriodoEntity p " +
            "JOIN p.evaluacionEntities e " +
            "WHERE p=e.periodoEntity " +
            "AND p.uniqueIdentifier= :id_periodo " +
            "AND e.status= :status " +
            "AND p.status= :status ")
    Optional<List<EvaluacionEntity>> findById_Periodo(String id_periodo, String status);
    @Query(value = "SELECT e FROM DocentexCursoEntity d " +
            "JOIN d.evaluacionEntities e " +
            "WHERE d=e.docentexCursoEntity " +
            "AND d.uniqueIdentifier= :id_dxc " +
            "AND e.status= :status " +
            "AND d.status= :status ")
    Optional<List<EvaluacionEntity>> findById_DXC(String id_dxc, String status);
}