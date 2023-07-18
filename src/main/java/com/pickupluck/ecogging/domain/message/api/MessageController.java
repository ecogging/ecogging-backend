package com.pickupluck.ecogging.domain.message.api;


import com.pickupluck.ecogging.domain.message.dto.MessageSaveRequestDto;
import com.pickupluck.ecogging.domain.message.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
@RequiredArgsConstructor
@RestController
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/messages")
    public Long save(@RequestBody MessageSaveRequestDto requestDto){
        System.out.println("블랙맘바이즈커밍");
        return messageService.save(requestDto);
    }
}
