package com.example.helloworld.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "submission", schema = "ss_web", catalog = "")
public class SubmissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "floor_id")
    private byte floorId;
    @Basic
    @Column(name = "user_id")
    private int userId;
    @Basic
    @Column(name = "user_cnt")
    private byte userCnt;
    @Basic
    @Column(name = "status")
    private byte status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getFloorId() {
        return floorId;
    }

    public void setFloorId(byte floorId) {
        this.floorId = floorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(byte userCnt) {
        this.userCnt = userCnt;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionEntity that = (SubmissionEntity) o;
        return id == that.id && floorId == that.floorId && userId == that.userId && userCnt == that.userCnt && status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, floorId, userId, userCnt, status);
    }
}
