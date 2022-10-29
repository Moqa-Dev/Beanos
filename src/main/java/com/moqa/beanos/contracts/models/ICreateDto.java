package com.moqa.beanos.contracts.models;

import com.moqa.beanos.models.entities.PostEntity;

public interface ICreateDto<TEntity> {
    TEntity toEntity();
}
