package com.example.helloworld.repository;

import com.example.helloworld.entity.RoomBedUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomBedUserRepository extends CrudRepository<RoomBedUserEntity, Long> {
    Optional<RoomBedUserEntity> findByUserId(Long userId);

    List<RoomBedUserEntity> findAllByRoomIdIsAndUserIdIsNull(Long roomId);
}
