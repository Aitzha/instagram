package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Feed;
import com.instagram.instagram.model.FeedPosts;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FeedPostsRepository extends CrudRepository<FeedPosts, Integer> {
    List<FeedPosts> findByFeedId(Integer feedId);
}
