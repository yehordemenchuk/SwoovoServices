package com.swoovo.friends.repository;

import com.swoovo.friends.entities.FriendsRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsRecordEntity, Long> {
}
