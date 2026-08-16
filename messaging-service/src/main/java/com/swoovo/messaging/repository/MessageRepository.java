package com.swoovo.messaging.repository;

import com.swoovo.messaging.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT COUNT(m) FROM Message m WHERE m.chat.id = :chatId")
    long countByChatId(@Param("chatId") Long chatId);

    @Query("SELECT m FROM Message m LEFT JOIN m.chat c WHERE c.id = :chatId")
    Page<Message> findByChatId(@Param("chatId") Long chatId, Pageable pageable);

    // Додамо метод для отримання всіх повідомлень
    List<Message> findAll();
}
