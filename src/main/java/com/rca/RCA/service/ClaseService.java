package com.rca.RCA.service;

import com.rca.RCA.entity.ClaseEntity;
import com.rca.RCA.repository.ClaseRepository;
import com.rca.RCA.type.ApiResponse;
import com.rca.RCA.type.Pagination;
import com.rca.RCA.type.ClaseDTO;
import com.rca.RCA.util.Code;
import com.rca.RCA.util.ConstantsGeneric;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    public Pagination<ClaseDTO> getList(String filter, int page, int size) {

        Pagination<ClaseDTO> pagination = new Pagination();
        pagination.setCountFilter(this.claseRepository.findCountEntities(ConstantsGeneric.CREATED_STATUS, filter));
        if (pagination.getCountFilter() > 0) {
            Pageable pageable = PageRequest.of(page, size);
            List<ClaseEntity> ClaseEntities = this.claseRepository.findEntities(ConstantsGeneric.CREATED_STATUS, filter, pageable).orElse(new ArrayList<>());
            pagination.setList(ClaseEntities.stream().map(ClaseEntity::getClaseDTO).collect(Collectors.toList()));
        }
        pagination.setTotalPages(pagination.processAndGetTotalPages(size));
        return pagination;
    }

    //Agregar Clase
    public ApiResponse<ClaseDTO> add(ClaseDTO ClaseDTO) {
        ApiResponse<ClaseDTO> apiResponse = new ApiResponse<>();
        System.out.println(ClaseDTO.toString());
        ClaseDTO.setId(UUID.randomUUID().toString());
        ClaseDTO.setCode(Code.generateCode(Code.CLASS_CODE, this.claseRepository.count() + 1, Code.CLASS_LENGTH));
        ClaseDTO.setStatus(ConstantsGeneric.CREATED_STATUS);
        ClaseDTO.setCreateAt(LocalDateTime.now());
        /*validamos
        Optional<ClaseEntity> optionalClaseEntity = this.claseRepository.findByName(ClaseDTO.getName());
        if (optionalClaseEntity.isPresent()) {
            apiResponse.setSuccessful(false);
            apiResponse.setCode("Clase_EXISTS");
            apiResponse.setMessage("No se registro, el Clase existe");
            return apiResponse;
        }*/
        //change dto to entity
        ClaseEntity ClaseEntity = new ClaseEntity();
        ClaseEntity.setClaseDTO(ClaseDTO);

        apiResponse.setData(this.claseRepository.save(ClaseEntity).getClaseDTO());
        apiResponse.setSuccessful(true);
        apiResponse.setMessage("ok");
        return apiResponse;
    }

    //Modificar Clase
    public void update(ClaseDTO ClaseDTO) {
        Optional<ClaseEntity> optionalClaseEntity = this.claseRepository.findByUniqueIdentifier(ClaseDTO.getId());
        if (optionalClaseEntity.isPresent()) {
            ClaseDTO.setUpdateAt(LocalDateTime.now());
            /*validamos que no se repita
            Optional<ClaseEntity> optionalClaseEntityValidation = this.claseRepository.findByName(ClaseDTO.getName(), ClaseDTO.getName());
            if (optionalClaseEntityValidation.isPresent()) {
                System.out.println("No se actulizo, el Clase existe");
                return;
            }*/
            ClaseEntity ClaseEntity = optionalClaseEntity.get();
            //set update data
            if (ClaseDTO.getCode() != null) {
                ClaseEntity.setCode(ClaseDTO.getCode());
            }
            ClaseEntity.setUpdateAt(ClaseDTO.getUpdateAt());
            //update in database
            this.claseRepository.save(ClaseEntity);
        } else {
            System.out.println("No existe la Clase para poder actualizar");
        }
    }

    //Borrar Clase
    public void delete(String id) {
        Optional<ClaseEntity> optionalClaseEntity = this.claseRepository.findByUniqueIdentifier(id);
        if (optionalClaseEntity.isPresent()) {
            ClaseEntity ClaseEntity = optionalClaseEntity.get();
            ClaseEntity.setStatus(ConstantsGeneric.DELETED_STATUS);
            ClaseEntity.setDeleteAt(LocalDateTime.now());
            this.claseRepository.save(ClaseEntity);
        } else {
            System.out.println("No existe el Clase para poder eliminar");
        }
    }
}