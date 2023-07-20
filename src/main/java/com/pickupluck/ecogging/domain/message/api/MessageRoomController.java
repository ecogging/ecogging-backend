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
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    // 1. 쪽지함 생성 - 쪽지함 기존 존재 유무 확인 단계 필요
    // - 존재하지 않을 때 생성 함수 실행 POST / 존재하면 sendRedirectedMessage()
    // createMessageRoom()
    @PostMapping("/messagerooms")
    public ResponseEntity<String> createMessageRoom(@RequestParam("curId") Long curId,
                                                    @RequestParam("content") String content,
                                                    @RequestParam("contactId") Long contactId) {

        // @RequestParam으로 프론트단에서 넘어온 데이터 각 변수에 확보 완료
        System.out.println("###createMessageRoom 함수 진입 == "+curId+","+content+","+contactId);
        // 1-1. 쪽지함 기존 존재여부 확인
        Optional<Long> isAlreadyExistId = messageRoomService.getMessageRoomId(curId, contactId);
        if (isAlreadyExistId.isPresent()) {
            System.out.println("이미 존재하는 쪽지함");
        }

        System.out.println("쪽지함 생성 완료");

        return ResponseEntity.ok("쪽지함 생성 완료");
    }

    // 2. 쪽지함 조회 - 기존 존재하고 있는 쪽지함 조회 GET /{messageRoomId}
    // getMessageRoom()

    // 3. 쪽지함 리스트 조회 GET
    // getMessageRooms()

    // 4. 쪽지함 삭제 DELETE /{messageRoomId}/delete
    // deleteMessageRoom()
}
