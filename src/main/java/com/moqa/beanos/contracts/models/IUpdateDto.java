package com.moqa.beanos.contracts.models;

public interface IUpdateDto<TEntity> {
    TEntity updateEntity(TEntity tEntity);
}
