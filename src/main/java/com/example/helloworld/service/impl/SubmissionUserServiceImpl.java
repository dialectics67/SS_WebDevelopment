package com.example.helloworld.service.impl;


import com.example.helloworld.entity.SubmissionUserEntity;
import com.example.helloworld.repository.SubmissionUserRepository;
import com.example.helloworld.service.SubmissionUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
@Service
public class SubmissionUserServiceImpl implements SubmissionUserService {

    @Resource
    private SubmissionUserRepository submissionUserRepository;

    @Override
    public void save(SubmissionUserEntity submissionUser) {
        submissionUserRepository.save(submissionUser);
    }

    @Override
    public void deleteById(Long id) {
        submissionUserRepository.deleteById(id);
    }

    @Override
    public Optional<SubmissionUserEntity> findById(Long id) {
        return submissionUserRepository.findById(id);
    }

    @Override
    public List<SubmissionUserEntity> findById(Collection<Long> ids) {
        Iterable<SubmissionUserEntity> iterable = submissionUserRepository.findAllById(ids);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }
}

