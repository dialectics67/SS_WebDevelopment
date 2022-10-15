package com.example.helloworld.repository;

import com.example.helloworld.entity.SubmissionEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionRepository extends CrudRepository<SubmissionEntity, Long> {
}
