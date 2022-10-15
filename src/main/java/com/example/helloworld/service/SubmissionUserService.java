package com.example.helloworld.service;


import com.example.helloworld.entity.SubmissionUserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
public interface SubmissionUserService {

    void save(SubmissionUserEntity submissionUser);

    void deleteById(Long id);

    Optional<SubmissionUserEntity> findById(Long id);

    List<SubmissionUserEntity> findById(Collection<Long> ids);

//    Page<SubmissionUserEntity> list(Pageable page);

}

