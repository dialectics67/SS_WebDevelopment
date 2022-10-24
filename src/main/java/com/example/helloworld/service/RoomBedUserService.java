package com.example.helloworld.service;


import com.example.helloworld.entity.RoomBedUserEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


/**
 * 业务层
 *
 * @author dialectics67
 * @since 2022-10-15 19:07:18
 */
public interface RoomBedUserService {

    void save(RoomBedUserEntity roomBedUser);

    void deleteById(Long id);

    Optional<RoomBedUserEntity> findById(Long id);

    Optional<RoomBedUserEntity> findByUserId(Long id);

    List<RoomBedUserEntity> findById(Collection<Long> ids);

    List<RoomBedUserEntity> findAllByRoomIdIsAndUserIdIsNull(Long roomId);

}

