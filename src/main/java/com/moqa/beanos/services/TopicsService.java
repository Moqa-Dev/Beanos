package com.moqa.beanos.services;

import com.moqa.beanos.models.dtos.TopicInputDto;
import com.moqa.beanos.models.dtos.TopicOutputDto;
import com.moqa.beanos.models.entities.TopicEntity;
import com.moqa.beanos.repositories.TopicsRepository;
import org.springframework.stereotype.Service;

@Service
public class TopicsService
        extends RestBaseService<
        Integer,
        TopicEntity,
        TopicInputDto,
        TopicInputDto,
        TopicOutputDto,
        TopicsRepository
        > {

    public TopicsService(TopicsRepository repository) {
        super(repository, TopicOutputDto.class);
    }
}
