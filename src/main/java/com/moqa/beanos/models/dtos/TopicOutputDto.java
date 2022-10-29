package com.moqa.beanos.models.dtos;

import com.moqa.beanos.contracts.models.IOutputDto;
import com.moqa.beanos.models.entities.BaseEntity;
import com.moqa.beanos.models.entities.PostEntity;
import com.moqa.beanos.models.entities.TopicEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TopicOutputDto extends BaseDto implements IOutputDto<TopicEntity, TopicOutputDto> {

    private Integer id;

    private String name;

    private String description;

    private Instant creationDate;

    private Instant updateDate;

    private List<PostOutputDto> posts;

    @Override
    public TopicOutputDto fromEntity(TopicEntity topicEntity, boolean expand) {
        this.id = topicEntity.getId();
        this.name = topicEntity.getName();
        this.description = topicEntity.getDescription();
        this.creationDate = topicEntity.getCreationDate();
        this.updateDate = topicEntity.getUpdateDate();
        if(expand && topicEntity.getPosts() != null)
            this.posts = topicEntity
                    .getPosts()
                    .stream()
                    .map(postEntity -> new PostOutputDto().fromEntity(postEntity, false))
                    .collect(Collectors.toList());
        return this;
    }
}
