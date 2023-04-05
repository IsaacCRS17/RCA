package com.rca.RCA.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rca.RCA.type.ClaseDTO;
import lombok.Data;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "Clase")
public class ClaseEntity extends AuditoryEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @Column(name = "code", length = 15)
    private String code;
    @Column(name = "date")
    @NotBlank
    private String date;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "docentexcurso_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private DocentexCursoEntity docentexCursoEntity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "periodo_id")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private PeriodoEntity periodoEntity;


    @OneToMany(mappedBy = "claseEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private Set<AsistenciaEntity> asistenciaEntities = new HashSet<>();
    public ClaseDTO getClaseDTO(){
        ClaseDTO ClaseDTO = new ClaseDTO();
        ClaseDTO.setId(this.getUniqueIdentifier());
        ClaseDTO.setCode(this.code);
        ClaseDTO.setDate(this.date);
        ClaseDTO.setPeriodoDTO(this.periodoEntity.getPeriodoDTO());
        ClaseDTO.setDocentexCursoDTO(this.docentexCursoEntity.getDocentexCursoDTO());
        ClaseDTO.setStatus(this.getStatus());
        ClaseDTO.setCreateAt(this.getCreateAt());
        ClaseDTO.setUpdateAt(this.getUpdateAt());
        ClaseDTO.setDeleteAt(this.getDeleteAt());
        return ClaseDTO;
    }

    public void setClaseDTO(ClaseDTO ClaseDTO){
        this.setUniqueIdentifier(ClaseDTO.getId());
        this.code= ClaseDTO.getCode();
        this.date= ClaseDTO.getDate();
        this.setStatus(ClaseDTO.getStatus());
        this.setCreateAt(ClaseDTO.getCreateAt());
        this.setUpdateAt(ClaseDTO.getUpdateAt());
        this.setDeleteAt(ClaseDTO.getDeleteAt());
    }
}
