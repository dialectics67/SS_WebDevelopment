package com.example.helloworld.controller;


import com.example.helloworld.entity.RoomBedUserEntity;
import com.example.helloworld.entity.RoomEntity;
import com.example.helloworld.service.RoomBedUserService;
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
@RequestMapping("/roomBedUser")
@AllArgsConstructor
public class RoomBedUserController {

    private RoomBedUserService roomBedUserService;
    private RoomController roomController;

    /**
     * 获取列表(分页)
     */
    @GetMapping("/list")
    public Page<RoomBedUserEntity> list(Pageable page) {
        return null;
    }

    /**
     * 获取
     */
    @GetMapping("/get")
    public Optional<RoomBedUserEntity> get(Long id) {
        return roomBedUserService.findById(id);
    }

    /**
     * 添加
     */
    @PostMapping("/add")
    public void add(@RequestBody RoomBedUserEntity roomBedUser) {
        roomBedUserService.save(roomBedUser);
    }


    /**
     * 修改
     */
    @PostMapping("/update")
    public void update(@RequestBody RoomBedUserEntity roomBedUser) {
        roomBedUserService.save(roomBedUser);
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    public void delete(Long id) {
        roomBedUserService.deleteById(id);
    }

    /**
     * 初始化所有的room_bed
     */
//    @GetMapping("/init")
    public void initTable() {
        Iterable<RoomEntity> allRooms = roomController.getAllRooms();
        for (RoomEntity room : allRooms) {
            for (long i = 0; i < room.getBedCntAll(); i++) {
                RoomBedUserEntity roomBedUserEntity = new RoomBedUserEntity(room.getId(), i, null);
                roomBedUserService.save(roomBedUserEntity);
            }
        }
    }
}

