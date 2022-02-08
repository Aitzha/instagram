package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Feed;
import com.instagram.instagram.model.FeedPosts;
import org.springframework.data.repository.CrudRepository;

public interface FeedPostsRepository extends CrudRepository<FeedPosts, Integer> {
}
