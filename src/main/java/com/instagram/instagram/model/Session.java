package com.instagram.instagram.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;
import java.util.Random;

@Entity
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    String token;
    Integer userId;

    public Session () {
        token = generateToken();
    }

    public Integer getId() {
        return this.id;
    }

    public String getToken() {
        return this.token;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String generateToken() {
        StringBuilder sb = new StringBuilder();
        Random rand = new Random(System.currentTimeMillis());

        for(int i = 0; i < 10; i++) {
            int x = rand.nextInt();
            if(x % 36 < 26) {
                char c = (char)(x + 'a');
                sb.append(c);
            } else {
                char c = (char)(x + '0');
                sb.append(c);
            }
        }

        return sb.toString();
    }
}
