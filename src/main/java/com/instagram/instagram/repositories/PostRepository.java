package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Integer> {
}
