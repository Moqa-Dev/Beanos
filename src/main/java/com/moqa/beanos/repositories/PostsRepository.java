package com.moqa.beanos.repositories;

import com.moqa.beanos.models.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<PostEntity, Integer>, JpaSpecificationExecutor<PostEntity> {
}
