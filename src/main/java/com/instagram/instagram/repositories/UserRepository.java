package com.instagram.instagram.repositories;

import com.instagram.instagram.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findByUsername(String username);
}
