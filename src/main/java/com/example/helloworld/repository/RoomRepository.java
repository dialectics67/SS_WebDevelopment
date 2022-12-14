package com.example.helloworld.repository;

import com.example.helloworld.entity.RoomEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends CrudRepository<RoomEntity, Long> {
    List<RoomEntity> findAllByRoomSex(Integer roomSex);

    List<RoomEntity> findAllByFloorIdIsAndRoomSexIsAndBedCntFreeGreaterThanEqualAndRoomAvailableIs(Integer floorId, Integer roomSex, Integer bedCntFree, Integer roomAvailable);

    List<RoomEntity> findAllByFloorIdAndRoomSex(Integer floorId, Integer roomSex);
}
