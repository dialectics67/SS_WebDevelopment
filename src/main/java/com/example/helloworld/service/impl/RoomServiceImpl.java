package com.example.helloworld.service.impl;


import com.example.helloworld.entity.RoomEntity;
import com.example.helloworld.repository.RoomRepository;
import com.example.helloworld.service.RoomService;
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
    public Optional<RoomEntity> findById(Long id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<RoomEntity> findById(Collection<Long> ids) {
        Iterable<RoomEntity> iterable = roomRepository.findAllById(ids);
        return StreamSupport.stream(iterable.spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomEntity> findByRoomSex(Integer roomSex) {
        return roomRepository.findAllByRoomSex(roomSex);
    }

    @Override
    public List<RoomEntity> findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIsTrue(Integer floorId, Integer roomSex, Integer bedCntFree) {
        return roomRepository.findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIs(floorId, roomSex, bedCntFree, 1);
    }


    @Override
    public Iterable<RoomEntity> findAll() {
        return roomRepository.findAll();
    }

}

