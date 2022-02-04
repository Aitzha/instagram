package com.instagram.instagram.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userId;
    private String imagePath;

    public Post() {}

    public Post(String imagePath, Integer userId) {
        this.imagePath = imagePath;
        this.userId = userId;
    }

    public Integer getId() {
        return this.id;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public String getImagePath() {
        return this.imagePath;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
