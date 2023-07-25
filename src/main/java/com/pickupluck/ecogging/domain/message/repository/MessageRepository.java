package com.pickupluck.ecogging.domain.message.repository;

import com.pickupluck.ecogging.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE m.messageRoom.id = :messageRoomId")
    List<Message> findAllByMessageRoomId(@Param("messageRoomId") Long messageRoomId);
}
