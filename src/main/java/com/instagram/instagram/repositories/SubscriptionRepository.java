package com.instagram.instagram.repositories;

import com.instagram.instagram.model.Subscription;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SubscriptionRepository extends CrudRepository<Subscription, Integer> {
    List<Subscription> findByFollowerId(Integer followerId);
}
