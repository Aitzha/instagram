package com.instagram.instagram.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer followeeId;
    private Integer followerId;

    public Subscription(Integer followeeId, Integer followerId) {
        this.followeeId = followeeId;
        this.followerId = followerId;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getFolloweeId() {
        return this.followeeId;
    }

    public Integer getFollowerId() {
        return this.followerId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFolloweeIdId(Integer followeeId) {
        this.followeeId = followeeId;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }
}
