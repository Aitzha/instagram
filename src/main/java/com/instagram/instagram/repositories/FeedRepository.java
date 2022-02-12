package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Feed;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface FeedRepository extends CrudRepository<Feed, Integer> {
    Optional<Feed> findByViewerId(Integer viewerId);
}
