package com.example.helloworld.service;


import com.example.helloworld.entity.SubmissionEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
public interface SubmissionService {

    void save(SubmissionEntity submission);

    void deleteById(Long id);

    Optional<SubmissionEntity> findById(Long id);

    List<SubmissionEntity> findById(Collection<Long> ids);

//    Page<SubmissionEntity> list(Pageable page);

}

