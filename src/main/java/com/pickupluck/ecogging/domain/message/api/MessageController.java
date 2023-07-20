package com.pickupluck.ecogging.domain.message.api;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MessageController {

    // 1. 쪽지 전송 POST /{messageRoomId}/messages
    // sendMessage()

    // 2. 쪽지함 생성에서 쪽지함이 기존에 이미 존재하는 경우 리다이렉트된 쪽지 전송 POST /{messageRoomId}/redirect-message
    // sendRedirectedMessage()

    // 3. 모든 쪽지 조회 GET /{messageRoomId}/messages
    // getAllMessages()
}
