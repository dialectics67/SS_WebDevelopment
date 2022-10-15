package com.example.helloworld.repository;

import com.example.helloworld.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
}
