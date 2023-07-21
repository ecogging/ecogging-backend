package com.pickupluck.ecogging;

import com.pickupluck.ecogging.domain.plogging.repository.AccompanyRepository;
import com.pickupluck.ecogging.domain.plogging.service.AccompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
class EcoggingApplicationTests {
	@Autowired
    AccompanyService service;
	@Test
	void contextLoads() throws Exception {
        Map<String, Object> map = service.getMyAccompanyList(Long.valueOf(14),1);
		System.out.println(map);
	}
	@Test
	void getMyAccompanyTempList() throws Exception {
		Map<String, Object> map = service.getMyAccompanyTempList(Long.valueOf(14),1);
		System.out.println(map);
	}
	@Test
	void getMyAccompanyscrapList() throws Exception {
		Map<String, Object> map = service.getMyAccompanyscrapList(Long.valueOf(1),1);
		System.out.println(map);
	}
	@Test
	void getMyParticipationList() throws Exception {
		Map<String, Object> map = service.getMyParticipationList(Long.valueOf(1),1);
		System.out.println(map);
	}
}
