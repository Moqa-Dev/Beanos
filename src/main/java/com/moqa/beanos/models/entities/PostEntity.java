package com.moqa.beanos.models.entities;

import com.moqa.beanos.models.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "POSTS")
public class PostEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "POSTS_ID_SEQ_GEN")
    @SequenceGenerator(name = "POSTS_ID_SEQ_GEN", sequenceName = "POSTS_ID_SEQ", initialValue = 100, allocationSize = 1)
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "Name is a required field!")
    @Size(max = 100, message = "Name name must be less than 100 characters")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "Content is a required field!")
    @Size(min = 100, message = "Content name must be more than 100 characters")
    @Column(name = "CONTENT")
    private String content;

    @NotNull(message = "Status is a required field!")
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @NotNull(message = "Topic ID is a required field!")
    @Column(name = "TOPIC_ID")
    private Integer topicId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="TOPIC_ID", nullable=false, insertable = false, updatable = false)
    private TopicEntity topic;
}
