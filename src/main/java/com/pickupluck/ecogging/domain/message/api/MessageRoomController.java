package com.pickupluck.ecogging.domain.message.api;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;
import com.pickupluck.ecogging.domain.message.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    // 1. 쪽지함 생성 - 쪽지함 기존 존재 유무 확인 단계 필요
    // - 존재하지 않을 때 생성 함수 실행 POST / 존재하면 sendRedirectedMessage()
    // createMessageRoom()
    @PostMapping("/messagerooms")
    public ResponseEntity<String> createMessageRoom(@RequestBody Map<String, Object> requestBody) throws URISyntaxException, UnsupportedEncodingException {
        Long curId = Long.parseLong((String) requestBody.get("curId"));
        String content = (String) requestBody.get("content");
        Long contactId = Long.parseLong((String) requestBody.get("contactId"));

        // @RequestParam으로 프론트단에서 넘어온 데이터 각 변수에 확보 완료
        System.out.println("###createMessageRoom 함수 진입 == "+curId+","+content+","+contactId);

        // 1-1. 쪽지함 기존 존재여부 확인
        Optional<Long> isAlreadyExistId = messageRoomService.getMessageRoomId(curId, contactId);

        // 1-1-a. 둘이 대화하던 쪽지함이 이미 존재할 경우 messageService.sendRedirectedMessage()
        if (isAlreadyExistId.isPresent()) {
            System.out.println("이미 존재하는 쪽지함");

            // redirectURI 설정
            URI redirectUri = new URI(
                    new StringBuilder()
                            .append("/messagerooms/")
                            .append(isAlreadyExistId.get())
                            .append("/redirect-message?curId=")
                            .append(curId)
                            .append("&contactId=")
                            .append(contactId)
                            .append("&content=")
                            .append(URLEncoder.encode(content, StandardCharsets.UTF_8.toString()))
                            .toString()
            );

            // Header 설정
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setLocation(redirectUri);
            
            // 리다이렉트 쪽지 전송으로 우회
            return ResponseEntity.status(HttpStatus.TEMPORARY_REDIRECT)
                    .headers(httpHeaders)
                    .body(new String("이미 존재하는 쪽지함 -> 쪽지 전송 요청으로 리다이렉트됨"));

        } else {
        // 1-2. 둘이 대화하던 쪽지함이 존재하지 않을 경우 새로운 쪽지함을 생성하고 해당하는 쪽지함ID 반환
        MessageRoomIdResponseDto newMessageRoom = messageRoomService.saveMessageRoom(curId, contactId, content);
        System.out.println("쪽지함 생성 완료");

        return ResponseEntity.ok("쪽지함 생성 완료");
        }
    }

    // 2. 쪽지함 조회 - 기존 존재하고 있는 쪽지함 조회 GET /{messageRoomId}
    // getMessageRoom()

    // 3. 쪽지함 리스트 조회 GET
    // getMessageRooms()

    // 4. 쪽지함 삭제 DELETE /{messageRoomId}/delete
    // deleteMessageRoom()
}
