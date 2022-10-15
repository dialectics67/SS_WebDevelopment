package com.example.helloworld.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room", schema = "ss_web", catalog = "")
public class RoomEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "room_id")
    private int roomId;
    @Basic
    @Column(name = "room_sex")
    private byte roomSex;
    @Basic
    @Column(name = "room_available")
    private byte roomAvailable;
    @Basic
    @Column(name = "bed_cnt_all")
    private byte bedCntAll;
    @Basic
    @Column(name = "bet_cnt_available")
    private byte betCntAvailable;
    @Basic
    @Column(name = "bed_cnt_free")
    private byte bedCntFree;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public byte getRoomSex() {
        return roomSex;
    }

    public void setRoomSex(byte roomSex) {
        this.roomSex = roomSex;
    }

    public byte getRoomAvailable() {
        return roomAvailable;
    }

    public void setRoomAvailable(byte roomAvailable) {
        this.roomAvailable = roomAvailable;
    }

    public byte getBedCntAll() {
        return bedCntAll;
    }

    public void setBedCntAll(byte bedCntAll) {
        this.bedCntAll = bedCntAll;
    }

    public byte getBetCntAvailable() {
        return betCntAvailable;
    }

    public void setBetCntAvailable(byte betCntAvailable) {
        this.betCntAvailable = betCntAvailable;
    }

    public byte getBedCntFree() {
        return bedCntFree;
    }

    public void setBedCntFree(byte bedCntFree) {
        this.bedCntFree = bedCntFree;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomEntity that = (RoomEntity) o;
        return id == that.id && roomId == that.roomId && roomSex == that.roomSex && roomAvailable == that.roomAvailable && bedCntAll == that.bedCntAll && betCntAvailable == that.betCntAvailable && bedCntFree == that.bedCntFree;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, roomSex, roomAvailable, bedCntAll, betCntAvailable, bedCntFree);
    }
}
