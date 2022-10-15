package com.example.helloworld.controller;


import com.example.helloworld.entity.SubmissionEntity;
import com.example.helloworld.service.SubmissionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * 控制层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
@RestController
@RequestMapping("/submission")
@AllArgsConstructor
public class SubmissionController {

    private SubmissionService submissionService;

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public Page<SubmissionEntity> list(Pageable page) {
        return null;
    }

    /**
     * 获取
     */
    @GetMapping("/get")
    public Optional<SubmissionEntity> get(Long id) {
        return submissionService.findById(id);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public void add(@RequestBody SubmissionEntity submission) {
        submissionService.save(submission);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public void update(@RequestBody SubmissionEntity submission) {
        submissionService.save(submission);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        submissionService.deleteById(id);
    }

}

