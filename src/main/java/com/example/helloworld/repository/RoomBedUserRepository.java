package com.example.helloworld.repository;

import com.example.helloworld.entity.RoomBedUserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomBedUserRepository extends CrudRepository<RoomBedUserEntity, Long> {

}
