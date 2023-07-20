package com.pickupluck.ecogging.domain.message.api;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import com.pickupluck.ecogging.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 1. 쪽지 전송 POST /{messageRoomId}/messages
    // sendMessage()

    // 2. 쪽지함 생성에서 쪽지함이 기존에 이미 존재하는 경우 리다이렉트된 쪽지 전송 POST /{messageRoomId}/redirect-message
    // sendRedirectedMessage()
    @PostMapping("/messagerooms/{messageRoomId}/redirect-message")
    public ResponseEntity<String> sendRedirectedMessage(
            @PathVariable("messageRoomId") Long messageRoomId,
            @RequestParam("curId") Long curId,
            @RequestParam("content") String content) {

        System.out.println("sendRedirectedMessage 함수 진입");
        System.out.println("msgRoomID: "+messageRoomId);
        System.out.println("curId: "+curId);
        System.out.println("content: "+content); // 진입 성공

        // requestSendDto 생성
        MessageRequestSendDto request = new MessageRequestSendDto(content);
        System.out.println(request.getMessage());

        // 쪽지 전송
        messageService.sendMessage(curId, messageRoomId, request);
        return ResponseEntity.ok(new String("리다이렉트된 쪽지 전송 진짜 완료!"));
    }

    // 3. 모든 쪽지 조회 GET /{messageRoomId}/messages
    // getAllMessages()

}
