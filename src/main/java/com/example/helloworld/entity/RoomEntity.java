package com.example.helloworld.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room", schema = "ss_web")
public class RoomEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "room_sex")
    private Integer roomSex;
    @Basic
    @Column(name = "room_available")
    private Integer roomAvailable;
    @Basic
    @Column(name = "bed_cnt_all")
    private Integer bedCntAll;
    @Basic
    @Column(name = "bet_cnt_available")
    private Integer betCntAvailable;
    @Basic
    @Column(name = "bed_cnt_free")
    private Integer bedCntFree;
    @Basic
    @Column(name = "floor_id")
    private Integer floorId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomSex() {
        return roomSex;
    }

    public void setRoomSex(Integer roomSex) {
        this.roomSex = roomSex;
    }

    public Integer getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(Integer roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public Integer getBedCntAll() {
        return bedCntAll;
    }

    public void setBedCntAll(Integer bedCntAll) {
        this.bedCntAll = bedCntAll;
    }

    public Integer getBetCntAvailable() {
        return betCntAvailable;
    }

    public void setBetCntAvailable(Integer betCntAvailable) {
        this.betCntAvailable = betCntAvailable;
    }

    public Integer getBedCntFree() {
        return bedCntFree;
    }

    public void setBedCntFree(Integer bedCntFree) {
        this.bedCntFree = bedCntFree;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Integer getFloorId() {
        return floorId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(roomId, that.roomId) && Objects.equals(roomSex, that.roomSex) && Objects.equals(roomAvailable, that.roomAvailable) && Objects.equals(bedCntAll, that.bedCntAll) && Objects.equals(betCntAvailable, that.betCntAvailable) && Objects.equals(bedCntFree, that.bedCntFree);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, roomSex, roomAvailable, bedCntAll, betCntAvailable, bedCntFree);
    }

}
