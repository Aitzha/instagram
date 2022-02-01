package com.instagram.instagram.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class FeedPosts {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer feedId;
    private Integer postId;

    public Integer getId() {
        return this.id;
    }

    public Integer getFeedId() {
        return this.feedId;
    }

    public Integer getPostId() {
        return this.postId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setFeedId(Integer feedId) {
        this.feedId = feedId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }
}
