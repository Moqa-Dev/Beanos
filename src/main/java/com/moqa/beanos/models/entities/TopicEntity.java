package com.moqa.beanos.models.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "TOPICS")
public class TopicEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TOPICS_ID_SEQ_GEN")
    @SequenceGenerator(name = "TOPICS_ID_SEQ_GEN", sequenceName = "TOPICS_ID_SEQ", initialValue = 100, allocationSize = 1)
    @Column(name = "ID", nullable = false, unique = true)
    private Integer id;

    @NotNull(message = "Name is a required field!")
    @Size(max = 100, message = "Name name must be less than 100 characters")
    @Column(name = "NAME")
    private String name;

    @NotNull(message = "Description is a required field!")
    @Size(max = 300, message = "Value name must be less than 100 characters")
    @Column(name = "DESCRIPTION")
    private String description;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy="topicId", fetch = FetchType.LAZY)
    private List<PostEntity> posts;

}
