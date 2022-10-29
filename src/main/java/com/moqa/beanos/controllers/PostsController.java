package com.moqa.beanos.controllers;

import com.moqa.beanos.models.dtos.PostInputDto;
import com.moqa.beanos.models.dtos.PostOutputDto;
import com.moqa.beanos.models.entities.PostEntity;
import com.moqa.beanos.services.PostsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/posts")
public class PostsController extends RestBaseController<Integer, PostEntity, PostInputDto, PostInputDto, PostOutputDto> {
    
    public PostsController(PostsService postsService) {
        super(postsService);
    }
}
