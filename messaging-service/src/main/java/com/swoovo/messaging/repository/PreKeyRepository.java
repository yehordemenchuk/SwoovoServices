package com.swoovo.messaging.repository;

import com.swoovo.messaging.entity.PreKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PreKeyRepository extends JpaRepository<PreKey, Long> {
    @Query(value = """
        WITH cte AS (
            SELECT id FROM pre_keys
            WHERE user_id = :userId AND device_id = :deviceId AND used = false
            ORDER BY created_at
            LIMIT 1
            FOR UPDATE SKIP LOCKED
        )
        UPDATE pre_keys p
        SET used = true
        FROM cte
        WHERE p.id = cte.id
        RETURNING p.id, p.prekey_pub, p.user_id, p.device_id
        """, nativeQuery = true)
    Optional<PreKey> consumePreKey(@Param("userId") String userId,
                                   @Param("deviceId") int deviceId);
}
