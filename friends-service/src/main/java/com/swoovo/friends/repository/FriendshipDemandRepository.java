package com.swoovo.friends.repository;

import com.swoovo.friends.entities.FriendshipDemandEntity;
import com.swoovo.friends.entities.RequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipDemandRepository extends JpaRepository<FriendshipDemandEntity, Long> {
    @Query("SELECT COUNT(fr) > 0 FROM FriendshipDemandEntity fr WHERE fr.senderId = :senderId AND fr.receiverId = :receiverId AND fr.status = :status")
    boolean existsBySenderIdAndReceiverIdAndStatus(@Param("senderId") long senderId, @Param("receiverId") long receiverId, @Param("status") RequestStatus status);

    @Query("SELECT fr FROM FriendshipDemandEntity fr WHERE fr.receiverId = :receiverId AND fr.status = :status")
    List<FriendshipDemandEntity> findByReceiverIdAndStatus(@Param("receiverId") long receiverId, @Param("status") RequestStatus status);

    @Query("SELECT fr FROM FriendshipDemandEntity fr WHERE (fr.senderId = :userId1 AND fr.receiverId = :userId2) OR (fr.senderId = :userId2 AND fr.receiverId = :userId1)")
    List<FriendshipDemandEntity> findExistingRequests(@Param("userId1") long userId1, @Param("userId2") long userId2);

    @Query("SELECT fr FROM FriendshipDemandEntity fr WHERE fr.senderId = :userId OR fr.receiverId = :userId")
    List<FriendshipDemandEntity> findAllByUserId(@Param("userId") long userId);
}
