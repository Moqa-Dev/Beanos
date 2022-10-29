package com.moqa.beanos.services;

import com.moqa.beanos.contracts.models.ICreateDto;
import com.moqa.beanos.contracts.models.IOutputDto;
import com.moqa.beanos.contracts.models.IUpdateDto;
import com.moqa.beanos.contracts.services.IRestService;
import com.moqa.beanos.infrastructure.exceptions_handling.exceptions.BadRequestException;
import com.moqa.beanos.models.entities.PostEntity;
import com.turkraft.springfilter.boot.FilterSpecification;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
public abstract class RestBaseService<
        TId,
        TEntity,
        TCreateDto extends ICreateDto<TEntity>,
        TUpdateDto extends IUpdateDto<TEntity>,
        TOutputDto extends IOutputDto<TEntity, TOutputDto>,
        TRepository extends JpaRepository<TEntity, TId> & JpaSpecificationExecutor<TEntity>
        >
        implements IRestService<
        TId,
        TEntity,
        TCreateDto,
        TUpdateDto,
        TOutputDto
        > {

    private final TRepository repository;
    private final Class<TOutputDto> outputDtoClass;

    public RestBaseService(TRepository repository, Class<TOutputDto> outputDtoClass) {
        this.repository = repository;
        this.outputDtoClass = outputDtoClass;
    }

    @Override
    public Page<TOutputDto> search(String filter, boolean expand, Integer pageSize, Integer pageNumber, String sortFields) {
        Specification<TEntity> search = Specification.where(null);
        if(filter != null && !filter.trim().isEmpty())
            search = new FilterSpecification<>(filter);
        Pageable pageable = Pageable.unpaged();
        if(pageSize != null && pageNumber != null){
            if(sortFields != null && !sortFields.isEmpty()){
                Sort sort = Sort.by(sortFields.split(","));
                sort.and(Sort.sort(PostEntity.class));
                pageable = PageRequest.of(pageNumber, pageSize, sort);
            }else {
                pageable = PageRequest.of(pageNumber, pageSize);
            }
        }
        Page<TEntity> exampleEntities = repository.findAll(search, pageable);
        List<TOutputDto> outputDTOs = exampleEntities.getContent()
                .stream()
                .map(e -> this.createInstance().fromEntity(e, expand))
                .collect(Collectors.toList());
        return new PageImpl<TOutputDto>(outputDTOs, exampleEntities.getPageable(), exampleEntities.getTotalElements());
    }

    @Override
    public List<TOutputDto> get() {
        return repository
                .findAll()
                .stream()
                .map(e -> this.createInstance().fromEntity(e, true))
                .collect(Collectors.toList()
                );
    }

    @Override
    public TOutputDto getById(TId id) {
        return this.createInstance().fromEntity(
                repository
                        .findById(id)
                        .orElseThrow(() -> new BadRequestException("Entity not found")
                        ), true
        );
    }

    @Override
    public TOutputDto add(TCreateDto createDto) {
        return this.createInstance().fromEntity(
                repository.save(
                        createDto.toEntity()
                ), false
        );
    }

    @Override
    public TOutputDto update(TId id, TUpdateDto updateDto) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Entity not found"));
        updateDto.updateEntity(entity);
        repository.saveAndFlush(entity);
        return this.createInstance().fromEntity(repository.getById(id), false);
    }

    @Override
    public void delete(TId id) {
        TEntity entity = repository.findById(id)
                .orElseThrow(() -> new BadRequestException("Entity not found"));
        repository.delete(entity);
    }

    private TOutputDto createInstance(){
        try {
            return outputDtoClass.getConstructor().newInstance();
        } catch (Exception e) {
            return null;
        }
    }
}
