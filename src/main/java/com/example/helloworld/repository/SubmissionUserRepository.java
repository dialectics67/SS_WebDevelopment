package com.example.helloworld.repository;

import com.example.helloworld.entity.SubmissionUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubmissionUserRepository extends CrudRepository<SubmissionUserEntity, Long> {

}
