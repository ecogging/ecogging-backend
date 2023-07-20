package com.pickupluck.ecogging.domain.message.repository;

import com.pickupluck.ecogging.domain.message.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
