package com.moqa.beanos.models.dtos;

import com.moqa.beanos.contracts.models.ICreateDto;
import com.moqa.beanos.contracts.models.IUpdateDto;
import com.moqa.beanos.models.entities.BaseEntity;
import com.moqa.beanos.models.entities.PostEntity;
import com.moqa.beanos.models.entities.TopicEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class TopicInputDto extends BaseDto implements ICreateDto<TopicEntity>, IUpdateDto<TopicEntity> {

    private String name;

    private String description;

    @Override
    public TopicEntity toEntity(){
        TopicEntity topicEntity = new TopicEntity();
        topicEntity.setName(this.name);
        topicEntity.setDescription(this.description);
        return topicEntity;
    }

    @Override
    public TopicEntity updateEntity(TopicEntity topicEntity){
        topicEntity.setName(this.name);
        topicEntity.setDescription(this.description);
        return topicEntity;
    }
}
