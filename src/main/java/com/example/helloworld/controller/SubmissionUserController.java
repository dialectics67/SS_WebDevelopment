package com.example.helloworld.controller;


import com.example.helloworld.entity.SubmissionUserEntity;
import com.example.helloworld.service.SubmissionUserService;
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
@RequestMapping("/submissionUser")
@AllArgsConstructor
public class SubmissionUserController {

    private SubmissionUserService submissionUserService;

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public Page<SubmissionUserEntity> list(Pageable page) {
        return null;
    }

    /**
     * 获取
     */
    @GetMapping("/get")
    public Optional<SubmissionUserEntity> get(Long id) {
        return submissionUserService.findById(id);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public void add(@RequestBody SubmissionUserEntity submissionUser) {
        submissionUserService.save(submissionUser);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public void update(@RequestBody SubmissionUserEntity submissionUser) {
        submissionUserService.save(submissionUser);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        submissionUserService.deleteById(id);
    }

}

