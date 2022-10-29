package com.moqa.beanos.services;

import com.moqa.beanos.models.dtos.PostInputDto;
import com.moqa.beanos.models.dtos.PostOutputDto;
import com.moqa.beanos.models.entities.PostEntity;
import com.moqa.beanos.repositories.PostsRepository;
import org.springframework.stereotype.Service;

@Service
public class PostsService
        extends RestBaseService<
        Integer,
        PostEntity,
        PostInputDto,
        PostInputDto,
        PostOutputDto,
        PostsRepository
        > {

    public PostsService(PostsRepository repository) {
        super(repository, PostOutputDto.class);
    }
}
