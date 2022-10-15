package com.example.helloworld.controller;


import com.example.helloworld.entity.UserEntity;
import com.example.helloworld.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * user basic information控制层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    /**
     * 获取user basic information列表(分页)
     */
    @GetMapping("/list")
    public Page<UserEntity> list(Pageable page) {
        return null;
    }

    /**
     * 获取user basic information
     */
    @GetMapping("/get")
    public Optional<UserEntity> get(Long id) {
        return userService.findById(id);
    }

    /**
     * 添加user basic information
     */
    @PostMapping("/add")
    public void add(@RequestBody UserEntity user) {
        userService.save(user);
    }


    /**
     * 修改user basic information
     */
    @PostMapping("/update")
    public void update(@RequestBody UserEntity user) {
        userService.save(user);
    }

    /**
     * 删除user basic information
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        userService.deleteById(id);
    }

    /**
     * 获取全部数据，用于测试
     */
//    @GetMapping(path = "/all")
    public @ResponseBody Iterable<UserEntity> getAllRooms() {
        // This returns a JSON or XML with the users
        return userService.findAll();
    }

}

