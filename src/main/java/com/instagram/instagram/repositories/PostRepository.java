package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByUserId(Integer usedId);
}
