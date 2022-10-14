package com.rca.RCA.controller;

import com.rca.RCA.service.ApoderadoService;
import com.rca.RCA.type.ApiResponse;
import com.rca.RCA.type.Pagination;
import com.rca.RCA.type.ApoderadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("apoderado")
public class ApoderadoRESTController {

    @Autowired
    private ApoderadoService apoderadoService;

    public ApoderadoRESTController(){

    }

    @GetMapping
    public Pagination<ApoderadoDTO> list(
            @RequestParam(defaultValue = "") String filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return this.apoderadoService.getList(filter, page, size);
    }

    @PostMapping
    public ApiResponse<ApoderadoDTO> add(@RequestBody ApoderadoDTO ApoderadoDTO) {
        return this.apoderadoService.add(ApoderadoDTO);
    }

    @PutMapping
    public void update(@RequestBody ApoderadoDTO ApoderadoDTO) {
        this.apoderadoService.update(ApoderadoDTO);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        this.apoderadoService.delete(id);
    }
}
