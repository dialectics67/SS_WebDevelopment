package com.example.helloworld.controller;


import com.example.helloworld.entity.RoomEntity;
import com.example.helloworld.service.RoomService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


/**
 * 控制层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:17
 */
@RestController
@RequestMapping("/room")
@AllArgsConstructor
public class RoomController {

    private RoomService roomService;

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public Page<RoomEntity> list(Pageable page) {
        return null;
    }


    /**
     * 获取
     */
    @GetMapping("/get")
    public Optional<RoomEntity> get(Long id) {
        return roomService.findById(id);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public void add(@RequestBody RoomEntity room) {
        roomService.save(room);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public void update(@RequestBody RoomEntity room) {
        roomService.save(room);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        roomService.deleteById(id);
    }


    /**
     * 获取全部数据，用于测试
     */
//    @GetMapping(path = "/all")
    public @ResponseBody Iterable<RoomEntity> getAllRooms() {
        // This returns a JSON or XML with the users
        return roomService.findAll();
    }
}

