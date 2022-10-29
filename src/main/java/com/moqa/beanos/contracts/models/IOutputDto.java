package com.moqa.beanos.contracts.models;

public interface IOutputDto<TEntity, TDto> {
    TDto fromEntity(TEntity entity, boolean expand);
}
