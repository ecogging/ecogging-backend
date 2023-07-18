package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.MessageSaveRequestDto;
import com.pickupluck.ecogging.domain.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceI implements MessageService{
    private final MessageRepository messageRepository;

    @Override
    public Long save(MessageSaveRequestDto requestDto) {
        return messageRepository.save(requestDto.toEntity()).getId();
    }
}
