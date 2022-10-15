package com.example.helloworld.service.impl;


import com.example.helloworld.entity.SubmissionEntity;
import com.example.helloworld.repository.SubmissionRepository;
import com.example.helloworld.service.SubmissionService;
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
public class SubmissionServiceImpl implements SubmissionService {

    @Resource
    private SubmissionRepository submissionRepository;

    @Override
    public void save(SubmissionEntity submission) {
        submissionRepository.save(submission);
    }

    @Override
    public void deleteById(Long id) {
        submissionRepository.deleteById(id);
    }

    @Override
    public Optional<SubmissionEntity> findById(Long id) {
        return submissionRepository.findById(id);
    }

    @Override
    public List<SubmissionEntity> findById(Collection<Long> ids) {
        Iterable<SubmissionEntity> iterable = submissionRepository.findAllById(ids);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

}

