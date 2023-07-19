package com.pickupluck.ecogging.domain.message.api;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/messagerooms")
@RequiredArgsConstructor
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    // 쪽지함 생성
    @PostMapping
    public void createMessageRoom(@RequestParam("myId") Long curId, @RequestParam("content") String content, @RequestParam("contactId") Long contactId) {
        Long maybeMessageRoomId = messageRoomService.getMessageRoomId(curId, contactId); // 일단 상대아이디가 있는 쪽지함이 있는지 확인
        if(maybeMessageRoomId != null) {
            URI redirectUri = new URI(
                    new StringBuilder().append("/messages").append(maybeMessageRoomId)
                            .append("/redirect-message?userId=").append(curId).toString());
        }
    }
}
