package com.example.helloworld.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "submission_user", schema = "ss_web")
public class SubmissionUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private Long id;
    @Basic
    @Column(name = "submission_id")
    private Long submissionId;
    @Basic
    @Column(name = "user_id")
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSubmissionId() {
        return submissionId;
    }

    public void setSubmissionId(Long submissionId) {
        this.submissionId = submissionId;
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
        SubmissionUserEntity that = (SubmissionUserEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(submissionId, that.submissionId) && Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, submissionId, userId);
    }
}
