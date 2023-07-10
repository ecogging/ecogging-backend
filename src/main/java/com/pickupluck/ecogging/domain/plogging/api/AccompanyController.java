package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.service.AccompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class AccompanyController {

    @Autowired
    private AccompanyService accompanyService;

    @GetMapping("/accompany/{page}")
    public ResponseEntity<Map<String,Object>> accompanyList(@PathVariable Integer page,
                                                            @RequestParam("orderby") String orderby) {
        System.out.println(orderby);
        ResponseEntity<List<AccompanyDTO>> res = null;
        try {
            Map<String,Object> map = accompanyService.getAccompanyList(page, orderby);
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/accompanywrite/{temp}")
    public ResponseEntity<String> accompanyWrite(@PathVariable Integer temp, @RequestBody AccompanyDTO accompanyDTO) {
        System.out.println(temp);
        System.out.println(accompanyDTO);

        ResponseEntity<String> res = null;
        try {
            accompanyService.setAccompany(temp, accompanyDTO);
            return new ResponseEntity<>("동행모집 등록", HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}