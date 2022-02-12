package com.instagram.instagram.model;

import javax.persistence.*;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;


@Entity
//@Table(name = "userUsernamePassword",
//        indexes = {
//                @Index(name = "username",  columnList = "username", unique = false),
//                @Index(name = "password", columnList = "password",    unique = false),
//                @Index(name = "id", columnList = "id", unique = true)})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(name = "id", nullable = false)
    private Integer id;

    private
//    @Column(name = "username", nullable = true)
    String username;
//    @Column(name = "password", nullable = true)
    private String password;

    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Integer getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
