package com.rca.RCA.repository;

import com.rca.RCA.entity.AulaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AulaRepository extends JpaRepository<AulaEntity, Integer> {

    //Función para contar las aulas existentes y activas de un grado, con filtro de código y nombre
    @Query(value = "SELECT count(x) from GradoEntity g " +
            "JOIN g.aulaEntities x " +
            "JOIN x.seccionEntity s " +
            "WHERE g=x.gradoEntity " +
            "AND s.status = :status " +
            "AND x.status = :status " +
            "AND g.status = :status " +
            "AND (s.name like concat('%', :filter, '%') or g.name like concat('%', :filter, '%') or s.code like concat('%', :filter, '%') or g.code like concat('%', :filter, '%'))")
    Long findCountAula(String status, String filter);

    //Función para listar las aulas existentes y activas de un grado, con filtro de código y nombre
    @Query(value = "SELECT x from GradoEntity g " +
            "JOIN g.aulaEntities x " +
            "JOIN x.seccionEntity s " +
            "WHERE g=x.gradoEntity " +
            "AND s.status = :status " +
            "AND x.status = :status " +
            "AND g.status = :status " +
            "AND (s.name like concat('%', :filter, '%') or g.name like concat('%', :filter, '%') or s.code like concat('%', :filter, '%') or g.code like concat('%', :filter, '%'))")
    Optional<List<AulaEntity>> findAula(String status, String filter, Pageable pageable);

    //Función para obtener un aula con su Identificado Único
    Optional<AulaEntity> findByUniqueIdentifier(String uniqueIdentifier);

    @Query(value = "SELECT x from GradoEntity g " +
            "JOIN g.aulaEntities x " +
            "JOIN x.seccionEntity s " +
            "WHERE g=x.gradoEntity " +
            "AND g.id= :id_grado " +
            "AND g.status= 'CREATED'")
    Optional<List<AulaEntity>> findById_Grado(Integer id_grado);

    @Query(value = "SELECT x from GradoEntity g " +
            "JOIN g.aulaEntities x " +
            "JOIN x.seccionEntity s " +
            "WHERE g=x.gradoEntity " +
            "AND s.id= :id_seccion " +
            "AND s.status= 'CREATED'")
    Optional<List<AulaEntity>> findById_Seccion(Integer id_seccion);

    @Query(value = "SELECT x from GradoEntity g " +
            "JOIN g.aulaEntities x " +
            "JOIN x.seccionEntity s " +
            "WHERE g=x.gradoEntity " +
            "AND g.id= :id_grado " +
            "AND s.id= :id_seccion " +
            "AND x.status= 'CREATED'")
    Optional<AulaEntity> findByGradoYSeccion(Integer id_grado, Integer id_seccion);
}
