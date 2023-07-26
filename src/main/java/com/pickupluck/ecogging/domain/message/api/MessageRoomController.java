package com.pickupluck.ecogging.domain.message.api;

import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestCreateDto;
import com.pickupluck.ecogging.domain.message.dto.request.MessageRoomRequestGetDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomIdResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomListResponseDto;
import com.pickupluck.ecogging.domain.message.dto.response.MessageRoomResponseDto;
import com.pickupluck.ecogging.domain.message.service.MessageRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MessageRoomController {

    private final MessageRoomService messageRoomService;

    // 1. 쪽지함 생성 - 쪽지함 기존 존재 유무 확인 단계 필요
    // - 존재하지 않을 때 생성 함수 실행 POST / 존재하면 sendRedirectedMessage()
    // createMessageRoom() -->  아이디에서 쪽지 보내기
    @PostMapping("/messagerooms")
    public ResponseEntity<String> createMessageRoom(@RequestBody Map<String, Object> requestBody) throws URISyntaxException, UnsupportedEncodingException {
        Long curId = Long.parseLong((String) requestBody.get("curId"));
        String content = (String) requestBody.get("content");
        Long contactId = Long.parseLong((String) requestBody.get("contactId"));

        // @RequestParam으로 프론트단에서 넘어온 데이터 각 변수에 확보 완료

        // 1-1. 쪽지함 기존 존재여부 확인
        Optional<Long> isAlreadyExistId = messageRoomService.getMessageRoomId(curId, contactId);

        // 1-2. 둘이 대화하던 쪽지함이 이미 존재할 경우 messageService.sendRedirectedMessage()
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
        // 1-3. 둘이 대화하던 쪽지함이 존재하지 않을 경우 새로운 쪽지함을 생성하고 해당하는 쪽지함ID 반환
        MessageRoomIdResponseDto newMessageRoom = messageRoomService.saveMessageRoom(curId, contactId, content);

        return ResponseEntity.ok("쪽지함 생성 완료");
        }
    }

    // 2. 쪽지함 조회 - 기존 존재하고 있는 쪽지함 조회 GET /{messageRoomId} - 쪽지 목록까지!
    // getMessageRoom()
    @GetMapping("/{userId}/messageroom/{messageRoomId}")
    public ResponseEntity<Map<String, Object>> getMessageRoom(@PathVariable("userId")Long userId,
                                                              @PathVariable("messageRoomId")Long messageRoomId,
                                                              @RequestParam("pageNo") int pageNo
    ) {
        pageNo = pageNo==0 ? 0 : (pageNo-1);

        // 2-1. MessageRoom -> getRequest 생성
        MessageRoomRequestGetDto request = new MessageRoomRequestGetDto(messageRoomId);
        // 2-2. 생성한 MessageRoomRequestGetDto, userId로 MessageRoomResponseDto 획득
        Map<String, Object> responseMap = messageRoomService.getMessageRoom(userId, request, pageNo);

        MessageRoomResponseDto response = (MessageRoomResponseDto)responseMap.get("res");
        int all = (int)responseMap.get("all");

        // 2-3. responseBody에 message, data(쪽지함 상세 리스트 담은 - contactNickname & messages ) 저장
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "쪽지함 상세 리스트 조회 완료");
        responseBody.put("data", response);
        responseBody.put("allCount", all);

        return ResponseEntity.ok(responseBody);
    }

    // 3. 쪽지함 리스트 조회 GET
    // getMessageRooms()
    @GetMapping("/mypage/{userId}/messagerooms")
    public ResponseEntity<Map<String,Object>> getMessageRooms(
            @PathVariable("userId")Long userId,
            @RequestParam("pageNo") int pageNo) {

        pageNo = pageNo==0 ? 0 : (pageNo-1);
        Pageable pageable = PageRequest.of(pageNo, 10, Sort.by("max_created_at").descending()); // Pageable 객체 조건 맞춰 생성

        Map<String, Object> myMessageRoomsMap = messageRoomService.getMessageRooms(userId, pageable);

        // 쪽지함 목록
        Page<MessageRoomListResponseDto> response = (Page<MessageRoomListResponseDto>)myMessageRoomsMap.get("res");
        if (response.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // 전체 쪽지함 개수
        int all = (int)myMessageRoomsMap.get("all");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "쪽지방 리스트 조회 완료~");
        responseBody.put("data", response.getContent());
        responseBody.put("allCount", all);

        return ResponseEntity.ok(responseBody);
    }


    // 4. 쪽지함 삭제 DELETE /{messageRoomId}/delete
    // deleteMessageRoom()
    @DeleteMapping("/{userId}/messageroom/{messageRoomId}")
    public ResponseEntity<Map<String, Object>> deleteMessageRoom(@PathVariable("userId")Long userId,
                                                                 @PathVariable("messageRoomId")Long messageRoomId) {
        // Service의 delete method 실행 -> 쪽지함 삭제
        messageRoomService.deleteMessageRoom(userId, messageRoomId);

        // Map에 '쪽지함 삭제 완료' 메세지 설정해서 리턴
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", "쪽지함 삭제 완료");
        
        return ResponseEntity.ok(responseBody);
    }

    // 5. 쪽지함 복수 삭제 DELETE /mypage/{userId}/messagerooms
    @DeleteMapping("/mypage/{userId}/messagerooms")
    public ResponseEntity<Map<String, Object>> deleteMessageRooms(@PathVariable("userId")Long userId,
                                                                  @RequestBody List<Integer> requestBody) {
        List<Integer> deleteMsgRoomsIdInt = requestBody;

        // Long 으로 변환해서 쪽지함 하나씩 차례차례 삭제 처리
        for(int i=0;i<deleteMsgRoomsIdInt.size();i++){
            Long tempToLong = Long.parseLong(deleteMsgRoomsIdInt.get(i).toString()); // Long으로 변환
            messageRoomService.deleteMessageRoom(userId, tempToLong);
        }

        // Map에 문자열 설정해서 리턴
        Map<String, Object> response = new HashMap<>();
        response.put("message", "쪽지함 복수 삭제 완료");

        return ResponseEntity.ok(response);
    }

    // 6. 쪽지함 읽음 처리
    @PutMapping("/mypage/{userId}/messageroom/read")
    public ResponseEntity<Map<String, Object>> updateMessagesRead(@PathVariable("userId")Long userId,
                                                                  @RequestBody Long messageRoomId) {

        messageRoomService.updateMessagesRead(userId, messageRoomId);

        Map<String, Object> response = new HashMap<>();
        response.put("msg", "쪽지함 읽기 완료");

        return ResponseEntity.ok(response);
    }

    // 7. 복수 쪽지함 읽음 처리
    @PutMapping("/mypage/{userId}/messagerooms/read")
    public ResponseEntity<Map<String, Object>> updateMessagesReadAll(@PathVariable("userId")Long userId,
                                                                     @RequestBody List<String> requestBody) {
        List<String> updateMsgRoomsIdInt = requestBody;

        // Long 으로 변환해서 쪽지함 하나씩 차례차례 읽음 처리
        for(int i=0;i<updateMsgRoomsIdInt.size();i++){
            Long tempToLong = Long.parseLong(updateMsgRoomsIdInt.get(i)); // Long으로 변환
            messageRoomService.updateMessagesReadAll(userId, tempToLong);
        }

        // Map에 문자열 설정해서 리턴
        Map<String, Object> response = new HashMap<>();
        response.put("msg", "쪽지함 복수 읽기 완료");

        return ResponseEntity.ok(response);
    }

}
