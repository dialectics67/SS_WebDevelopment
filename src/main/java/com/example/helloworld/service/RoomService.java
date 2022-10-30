package com.example.helloworld.service;


import com.example.helloworld.entity.RoomEntity;

import java.util.*;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:17
 */
public interface RoomService {

    void save(RoomEntity room);

    void deleteById(Long id);

    Optional<RoomEntity> findAllById(Long id);

    List<RoomEntity> findAllById(Collection<Long> ids);

    List<RoomEntity> findAllByRoomSex(Integer roomSex);

    List<RoomEntity> findAllByFloorIdAndRoomSex(Integer floorId, Integer roomSex);

    List<RoomEntity> findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIsTrue(Integer floorId, Integer roomSex, Integer bedCntFree);

    Iterable<RoomEntity> findAll();

    // 查找所有含有指定性别房间的楼层
    Set<Integer> findAllFloorIdBySex(Integer roomSex);

    // 统计指定楼中指定性别的剩余床位数量
    Long sumFreeBedByFloorIdAndSex(Integer floorId, Integer roomSex);

    // 给定性别，找到所有含有该性别的楼，然后统计每栋楼中的该性别的床位总数，构成map返回
    Map<Integer, Long> sumAllFreeBedBySex(Integer roomSex);

}

