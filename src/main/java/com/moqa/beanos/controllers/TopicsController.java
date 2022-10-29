package com.moqa.beanos.controllers;

import com.moqa.beanos.models.dtos.TopicInputDto;
import com.moqa.beanos.models.dtos.TopicOutputDto;
import com.moqa.beanos.models.entities.TopicEntity;
import com.moqa.beanos.services.TopicsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/topics")
public class TopicsController extends RestBaseController<Integer, TopicEntity, TopicInputDto, TopicInputDto, TopicOutputDto> {

    public TopicsController(TopicsService topicsService) {
        super(topicsService);
    }
}
