package com.instagram.instagram.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Feed {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer viewerId;

    public Feed() {}

    public Feed(Integer viewerId) {
        this.viewerId = viewerId;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getViewerId() {
        return this.viewerId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setViewerId(Integer viewerId) {
        this.viewerId = viewerId;
    }
}
