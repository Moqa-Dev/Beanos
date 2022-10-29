package com.moqa.beanos.repositories;

import com.moqa.beanos.models.entities.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicsRepository extends JpaRepository<TopicEntity, Integer>, JpaSpecificationExecutor<TopicEntity> {
}
