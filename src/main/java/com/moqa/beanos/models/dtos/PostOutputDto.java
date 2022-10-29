package com.moqa.beanos.models.dtos;

import com.moqa.beanos.contracts.models.IOutputDto;
import com.moqa.beanos.models.entities.BaseEntity;
import com.moqa.beanos.models.entities.PostEntity;
import com.moqa.beanos.models.entities.TopicEntity;
import com.moqa.beanos.models.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostOutputDto extends BaseEntity implements IOutputDto<PostEntity, PostOutputDto> {

    private Integer id;

    private String name;

    private String content;

    private PostStatus status;

    private Instant creationDate;

    private Instant updateDate;

    private TopicOutputDto topic;

    @Override
    public PostOutputDto fromEntity(PostEntity postEntity, boolean expand) {
        this.id = postEntity.getId();
        this.name = postEntity.getName();
        this.content = postEntity.getContent();
        this.status = postEntity.getStatus();
        this.creationDate = postEntity.getCreationDate();
        this.updateDate = postEntity.getUpdateDate();
        if(expand && topic != null)
            this.topic = new TopicOutputDto().fromEntity(postEntity.getTopic(), false);
        return this;
    }
}
