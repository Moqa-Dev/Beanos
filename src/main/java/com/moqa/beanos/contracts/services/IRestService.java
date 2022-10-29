package com.moqa.beanos.contracts.services;

import com.moqa.beanos.contracts.models.ICreateDto;
import com.moqa.beanos.contracts.models.IOutputDto;
import com.moqa.beanos.contracts.models.IUpdateDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IRestService<
        TId,
        TEntity,
        TCreateDto extends ICreateDto<TEntity>,
        TUpdateDto extends IUpdateDto<TEntity>,
        TOutputDto extends IOutputDto<TEntity, TOutputDto>
        > {
    Page<TOutputDto> search(String filter, boolean expand, Integer pageSize, Integer pageNumber, String sortFields);
    List<TOutputDto> get();
    TOutputDto getById(TId id);
    TOutputDto add(TCreateDto createDto);
    TOutputDto update(TId id, TUpdateDto updateDto);
    void delete(TId id);
}
