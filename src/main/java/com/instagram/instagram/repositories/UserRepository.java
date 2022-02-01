package com.instagram.instagram.repositories;

import com.instagram.instagram.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {
}
