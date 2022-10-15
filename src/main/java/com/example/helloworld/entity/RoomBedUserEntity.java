package com.example.helloworld.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "room_bed_user", schema = "ss_web")
public class RoomBedUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "room_id")
    private Long roomId;
    @Basic
    @Column(name = "bed_id")
    private Long bedId;
    @Basic
    @Column(name = "user_id")
    private Long userId;

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

    public Long getBedId() {
        return bedId;
    }

    public void setBedId(Long bedId) {
        this.bedId = bedId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomBedUserEntity that = (RoomBedUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(roomId, that.roomId) && Objects.equals(bedId, that.bedId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, bedId, userId);
    }

    public RoomBedUserEntity() {
    }

    public RoomBedUserEntity(Long roomId, Long bedId, Long userId) {
        this.roomId = roomId;
        this.bedId = bedId;
        this.userId = userId;
    }
}
