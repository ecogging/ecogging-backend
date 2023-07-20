package com.pickupluck.ecogging.domain.message.repository;

import com.pickupluck.ecogging.domain.message.entity.Message;
import com.pickupluck.ecogging.domain.message.entity.MessageContent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageContentRepository extends JpaRepository<MessageContent, Long> {
}
