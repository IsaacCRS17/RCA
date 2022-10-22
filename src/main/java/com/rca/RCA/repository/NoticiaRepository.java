package com.rca.RCA.repository;

import com.rca.RCA.entity.NoticiaEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NoticiaRepository extends JpaRepository<NoticiaEntity, Integer> {

    @Query(value = "select n from NoticiaEntity n join n.usuarioEntity u " +
            "where u = n.usuarioEntity and u.status=:status " +
            "and (u.code like concat('%', :filter, '%') or u.pa_surname like concat('%', :filter, '%') or " +
            "u.ma_surname like concat('%', :filter, '%') or u.name like concat('%', :filter, '%') or " +
            "u.numdoc like concat('%', :filter, '%'))")
    Optional<List<NoticiaEntity>> findEntities(String status, String filter, Pageable pageable);

    @Query(value = "select count(n) from NoticiaEntity n join n.usuarioEntity u " +
            "where u = n.usuarioEntity and u.status=:status " +
            "and (u.code like concat('%', :filter, '%') or u.pa_surname like concat('%', :filter, '%') or " +
            "u.ma_surname like concat('%', :filter, '%') or u.name like concat('%', :filter, '%') or " +
            "u.numdoc like concat('%', :filter, '%'))")

   
    Long findCountEntities(String status, String filter);


    Optional<NoticiaEntity> findByUniqueIdentifier(String uniqueIdentifier);

    Optional<NoticiaEntity> findByTitle(String title);

    @Query(value = "select n from NoticiaEntity n " +
            "where n.title = :title and n.uniqueIdentifier <> :uniqueIdentifier ")
    Optional<NoticiaEntity> findByTitle(String title, String uniqueIdentifier);
}
