package com.pickupluck.ecogging.domain.message.service;

import com.pickupluck.ecogging.domain.message.dto.MessageSaveRequestDto;

public interface MessageService {

    public Long save(MessageSaveRequestDto requestDto);

}
