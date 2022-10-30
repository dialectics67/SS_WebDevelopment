package com.example.helloworld.service.impl;


import com.example.helloworld.entity.RoomEntity;
import com.example.helloworld.repository.RoomRepository;
import com.example.helloworld.service.RoomService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:17
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Resource
    private RoomRepository roomRepository;

    @Override
    public void save(RoomEntity room) {
        roomRepository.save(room);
    }

    @Override
    public void deleteById(Long id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Optional<RoomEntity> findAllById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<RoomEntity> findAllById(Collection<Long> ids) {
        Iterable<RoomEntity> iterable = roomRepository.findAllById(ids);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "room", key = "'allRoomBySex:'+#roomSex")
    public List<RoomEntity> findAllByRoomSex(Integer roomSex) {
        return roomRepository.findAllByRoomSex(roomSex);
    }

    @Override
    @Cacheable(value = "room", key = "'allRoomByFloorIdAndSex:'+#floorId+'And'+#roomSex")
    public List<RoomEntity> findAllByFloorIdAndRoomSex(Integer floorId, Integer roomSex) {
        return roomRepository.findAllByFloorIdAndRoomSex(floorId, roomSex);
    }

    @Override
    @Cacheable(value = "room", key = "'allFloorIdBySex:'+#roomSex")
    public Set<Integer> findAllFloorIdBySex(Integer roomSex) {
        // 根据性别查找所有房间
        List<RoomEntity> roomEntityList = this.findAllByRoomSex(roomSex);
        // 统计楼号
        Set<Integer> floorSet = new HashSet<>();
        for (RoomEntity roomEntity : roomEntityList) {
            floorSet.add(roomEntity.getFloorId());
        }
        return floorSet;
    }

    @Override
    public Iterable<RoomEntity> findAll() {
        return roomRepository.findAll();
    }

    @Override
    public List<RoomEntity> findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIsTrue(Integer floorId, Integer roomSex, Integer bedCntFree) {
        return roomRepository.findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIs(floorId, roomSex, bedCntFree, 1);
    }

    @Override
    @Cacheable(value = "room", key = "'freeBedSumByFloorIdAndSex'+#floorId+'And'+#roomSex")
    public Long sumFreeBedByFloorIdAndSex(Integer floorId, Integer roomSex) {
        Long sumBed = 0L;
        // 根据楼号和性别查找所有的房间
        List<RoomEntity> roomEntityList = this.findAllByFloorIdAndRoomSex(floorId, roomSex);
        for (RoomEntity roomEntity : roomEntityList) {
            sumBed += roomEntity.getBedCntFree();
        }
        return sumBed;
    }

    @Override
    @Cacheable(value = "room", key = "'allFreeBedSumBySex:'+#roomSex")
    public Map<Integer, Long> sumAllFreeBedBySex(Integer roomSex) {
        // 查找所有含有该性别的楼号
        Set<Integer> floorIdSet = this.findAllFloorIdBySex(roomSex);
        Map<Integer, Long> floorBedCntFree = new HashMap<>();
        for (Integer floorId : floorIdSet) {
            // 根据楼号和性别统计床位数量
            floorBedCntFree.put(floorId, this.sumFreeBedByFloorIdAndSex(floorId, roomSex));
        }
        return floorBedCntFree;
    }


}

