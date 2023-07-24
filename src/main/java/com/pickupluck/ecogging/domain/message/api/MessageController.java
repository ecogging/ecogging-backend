package com.pickupluck.ecogging.domain.message.api;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRequestSendDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageResponseDto;
import com.pickupluck.ecogging.domain.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // 1. 쪽지 전송 POST /{messageRoomId}/messages --> 이게 쪽지함에서 답장하기
    // sendMessage()
    @PostMapping("{userId}/messageroom/{messageRoomId}/messages")
    public ResponseEntity<String> sendMesssage(
            @PathVariable("userId") Long userId,
            @PathVariable("messageRoomId") Long messageRoomId,
            @RequestBody Map<String, Object> request) {

            Long contactId = Long.parseLong(request.get("contactId").toString());
            String content = request.get("content").toString();
            MessageRequestSendDto requestDto = new MessageRequestSendDto(content);

            messageService.sendMessage(userId, messageRoomId, contactId, requestDto);

            return ResponseEntity.ok(new String("*******쪽지 전송 완료*******"));
    }

    // 2. 쪽지함 생성에서 쪽지함이 기존에 이미 존재하는 경우 리다이렉트된 쪽지 전송 POST /{messageRoomId}/redirect-message
    // sendRedirectedMessage()
    @PostMapping("/messagerooms/{messageRoomId}/redirect-message")
    public ResponseEntity<String> sendRedirectedMessage(
            @PathVariable("messageRoomId") Long messageRoomId,
            @RequestParam("curId") Long curId,
            @RequestParam("contactId") Long contactId,
            @RequestParam("content") String content) {

        System.out.println("sendRedirectedMessage 함수 진입");
        System.out.println("msgRoomID: "+messageRoomId);
        System.out.println("curId: "+curId);
        System.out.println("contactId: "+contactId);
        System.out.println("content: "+content); // 진입 성공

        // requestSendDto 생성
        MessageRequestSendDto request = new MessageRequestSendDto(content);
        System.out.println(request.getMessage());

        // 쪽지 전송
        messageService.sendMessage(curId, messageRoomId, contactId, request);
        return ResponseEntity.ok(new String("리다이렉트된 쪽지 전송 진짜 완료!"));
    }

//    // 3. 모든 쪽지 조회 GET /{messageRoomId}/messages
//    // getAllMessages()
//    @GetMapping("{userId}/messagerooms/{messageRoomId}/messages")
//    public ResponseEntity<Map<String,Object>> getAllMassages(@PathVariable("userId") Long curId,
//                                                             @PathVariable("messageRoomId") Long msgRoomId,
//                                                             @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) final Pageable pageable) {
//        Page<MessageResponseDto> allMessages = messageService.getAllMessages(curId, msgRoomId, pageable);
//
//        Map<String,Object> responseBody = new HashMap<>();
//        responseBody.put("message", "모든 쪽지 조회 완료~");
//        responseBody.put("allMsgs", allMessages);
//
//        return ResponseEntity.ok(responseBody);
//    }

}
