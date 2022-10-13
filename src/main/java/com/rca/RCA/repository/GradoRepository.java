package com.rca.RCA.repository;

import com.rca.RCA.entity.GradoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GradoRepository extends JpaRepository<GradoEntity, Integer> {

    //Función para listar los grados activos con filro de código o nombre
    @Query(value = "select c from GradoEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Optional<List<GradoEntity>> findGrados(String status, String filter, Pageable pageable);

    //Función para contar los grados activos con filro de código o nombre
    @Query(value = "select count(c) from GradoEntity c " +
            "where c.status = :status " +
            "and (c.code like concat('%', :filter, '%') or c.name like concat('%', :filter, '%'))"+
            "order by c.name")
    Long findCountGrados(String status, String filter);

    //Función para obtener un grado con su Identificado Único
    Optional<GradoEntity> findByUniqueIdentifier(String uniqueIdentifier);
    //Función para obtener un grado con su nombre
    Optional<GradoEntity> findByName(Character name);
}

