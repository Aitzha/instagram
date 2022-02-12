package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Session;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SessionRepository extends CrudRepository<Session, Integer> {
    Optional<Session> findByToken(String token);
}
