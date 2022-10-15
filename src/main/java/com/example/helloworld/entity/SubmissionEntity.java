package com.example.helloworld.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "submission", schema = "ss_web")
public class SubmissionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "floor_id")
    private Integer floorId;
    @Basic
    @Column(name = "user_id")
    private Long userId;
    @Basic
    @Column(name = "user_cnt")
    private Integer userCnt;
    @Basic
    @Column(name = "status")
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getFloorId() {
        return floorId;
    }

    public void setFloorId(Integer floorId) {
        this.floorId = floorId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserCnt() {
        return userCnt;
    }

    public void setUserCnt(Integer userCnt) {
        this.userCnt = userCnt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubmissionEntity that = (SubmissionEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(floorId, that.floorId) && Objects.equals(userId, that.userId) && Objects.equals(userCnt, that.userCnt) && Objects.equals(status, that.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, floorId, userId, userCnt, status);
    }
}
