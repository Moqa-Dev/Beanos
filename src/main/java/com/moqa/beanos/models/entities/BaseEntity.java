package com.moqa.beanos.models.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "CREATION_DATE")
    @NotNull(message = "Create Date is a required field!")
    private Instant creationDate;

    @Column(name = "UPDATE_DATE")
    @NotNull(message = "Update Date is a required field!")
    private Instant updateDate;

    @PrePersist
    public void prePersist() {
        updateDate = creationDate = Instant.now();
    }

    @PreUpdate
    public void preUpdate() {
        updateDate = Instant.now();
    }
}
