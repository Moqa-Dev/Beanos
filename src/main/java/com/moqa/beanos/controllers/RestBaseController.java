package com.moqa.beanos.controllers;

import com.moqa.beanos.contracts.models.ICreateDto;
import com.moqa.beanos.contracts.models.IOutputDto;
import com.moqa.beanos.contracts.models.IUpdateDto;
import com.moqa.beanos.contracts.services.IRestService;
import com.moqa.beanos.models.api.Response;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Validated
public abstract class RestBaseController<
        TId,
        TEntity,
        TCreateDto extends ICreateDto<TEntity>,
        TUpdateDto extends IUpdateDto<TEntity>,
        TOutputDto extends IOutputDto<TEntity, TOutputDto>
        > 
        extends BaseController {

    private final IRestService<TId, TEntity, TCreateDto, TUpdateDto, TOutputDto> restService;
    
    public RestBaseController(IRestService<TId, TEntity, TCreateDto, TUpdateDto, TOutputDto> restService) {
        this.restService = restService;
    }

    @GetMapping(value = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<List<TOutputDto>>> search(
            @RequestParam(required = false) String filter,
            @RequestParam(required = false) boolean expand,
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String sortFields){
        return formatResponse(restService.search(filter, expand, pageSize, pageNumber, sortFields));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<List<TOutputDto>>> getAll() {
        return formatResponse(restService.get());
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<TOutputDto>> getById(@PathVariable("id") TId id) {
        return formatResponse(restService.getById(id));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<TOutputDto>> add(
            @RequestBody @Valid TCreateDto createDto) {
        return formatResponse(restService.add(createDto));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<TOutputDto>> updateById(
            @PathVariable("id") TId id,
            @RequestBody @Valid TUpdateDto updateDto) {
        return formatResponse(restService.update(id, updateDto));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Response<Object>> deleteById(@PathVariable("id") TId id) {
        restService.delete(id);
        return formatResponse();
    }

}
