package com.moqa.beanos.models.dtos;

import com.moqa.beanos.contracts.models.ICreateDto;
import com.moqa.beanos.contracts.models.IUpdateDto;
import com.moqa.beanos.models.entities.PostEntity;
import com.moqa.beanos.models.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PostInputDto extends BaseDto implements ICreateDto<PostEntity>, IUpdateDto<PostEntity> {

    private String name;

    private String content;

    private Integer topicId;

    private PostStatus status;

    @Override
    public PostEntity toEntity(){
        PostEntity postEntity = new PostEntity();
        postEntity.setName(this.name);
        postEntity.setContent(this.content);
        postEntity.setTopicId(this.topicId);
        postEntity.setStatus(this.status);
        return postEntity;
    }

    @Override
    public PostEntity updateEntity(PostEntity postEntity){
        postEntity.setName(this.name);
        postEntity.setContent(this.content);
        postEntity.setTopicId(this.topicId);
        postEntity.setStatus(this.status);
        return postEntity;
    }
}
