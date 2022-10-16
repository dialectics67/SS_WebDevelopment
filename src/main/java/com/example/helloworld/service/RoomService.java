package com.example.helloworld.service;


import com.example.helloworld.entity.RoomEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:17
 */
public interface RoomService {

    void save(RoomEntity room);

    void deleteById(Long id);

    Optional<RoomEntity> findById(Long id);

    List<RoomEntity> findById(Collection<Long> ids);

    List<RoomEntity> findByRoomSex(Integer roomSex);

    Iterable<RoomEntity> findAll();

//    Page<RoomEntity> list(Pageable page);

}

