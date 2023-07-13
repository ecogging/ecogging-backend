package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.plogging.entity.Participation;
import com.pickupluck.ecogging.domain.plogging.repository.AccompanyRepository;
import com.pickupluck.ecogging.domain.plogging.repository.ParticipationRepository;
import com.pickupluck.ecogging.domain.scrap.entity.Accompanyscrap;
import com.pickupluck.ecogging.domain.scrap.repository.AccompanyscrapRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class AccompanyServiceImpl implements AccompanyService {

    private final AccompanyRepository accompanyRepository;

    private final ParticipationRepository participationRepository;
    private final AccompanyscrapRepository accompanyscrapRepository;
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    @Override
    public Map<String, Object> getAccompanyList(Integer page, String orderby) throws Exception {
        PageRequest pageRequest = null;
        Page<Accompany> accompanyPage = null;
        if(orderby.equals("meetingDate")) { //임박순
            pageRequest = PageRequest.of(page-1,6, Sort.by(Sort.Direction.ASC, "meetingDate"));
            accompanyPage = accompanyRepository.findBySaveFalseAndActiveTrue(pageRequest);
        } else if(orderby.equals("activeFalse")) { //모집완료
            pageRequest = PageRequest.of(page-1,6, Sort.by(Sort.Direction.DESC, "createdAt"));
            accompanyPage = accompanyRepository.findBySaveFalseAndActiveFalse(pageRequest);
        } else { //최신순
            pageRequest = PageRequest.of(page-1,6, Sort.by(Sort.Direction.DESC, "createdAt"));
            accompanyPage = accompanyRepository.findBySaveFalseAndActiveTrue(pageRequest);
        }
        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();
        for(Accompany accompany : accompanyPage.getContent()){
            AccompanyDTO accompanyDTO = new AccompanyDTO(accompany);
            list.add(accompanyDTO);
        }
        map.put("list", list);
        map.put("hasNext", accompanyPage.hasNext());
        return map;
    }

    @Override
    public void setAccompany(Integer temp, AccompanyDTO accompanyDTO) throws Exception {
        Optional<User> user = userRepository.findById(accompanyDTO.getUserId());
        Accompany accompany = modelMapper.map(accompanyDTO, Accompany.class);
        if(user.isPresent()) {
            accompany.setWriter(user.get());
        }
        accompany.setSave(temp==1);
        System.out.println(accompany);
        accompanyRepository.save(accompany);
    }

    @Override
    public AccompanyDTO getAccompany(Long id) throws Exception {
        Optional<Accompany> oaccompany = accompanyRepository.findById(id);
        if(oaccompany.isEmpty()) return null;
        Accompany accompany = oaccompany.get();
        System.out.println("accompany:"+accompany);
        AccompanyDTO accompanyDTO = new AccompanyDTO(accompany);
        //조회수 증가
        accompany.setViews(accompany.getViews()+1);
        accompanyRepository.save(accompany);

        return accompanyDTO;
    }

    @Override
    public void removeAccompany(Long id) throws Exception {
        accompanyRepository.deleteById(id);
    }

    @Override
    public Boolean isParticipation(Long userId, Long accompanyId) throws Exception {
        Optional<Participation> oparticipation = participationRepository.findByUserIdAndAccompanyId(userId,accompanyId);
        if(oparticipation.isPresent()) return true;
        else return false;
    }


    @Override
    public Boolean toggleParticipation(Long userId, Long accompanyId) throws Exception {
        Optional<Participation> oparticipation = participationRepository.findByUserIdAndAccompanyId(userId,accompanyId);
        Accompany accompany = accompanyRepository.findById(accompanyId).get();
        if(oparticipation.isEmpty()) { //참여신청
            participationRepository.save(new Participation(userId,accompanyId,false));
            accompany.setJoincnt(accompany.getJoincnt()+1);
            accompanyRepository.save(accompany);
            return true;
        } else { //참여중, 참여취소
            participationRepository.deleteById(oparticipation.get().getParticipationId());
            accompany.setJoincnt(accompany.getJoincnt()-1);
            accompanyRepository.save(accompany);
            return false;
        }

    }

    @Override
    public Boolean isAccompanyScrap(Long userId, Long accompanyId) throws Exception {
        User user = userRepository.findById(userId).get();
        Accompany accompany = accompanyRepository.findById(accompanyId).get();
        Optional<Accompanyscrap> accompanyscrap = accompanyscrapRepository.findByUserAndAccompany(user,accompany);
        if(accompanyscrap.isPresent()) return true;
        else return false;
    }

    @Override
    public Boolean toggleAccompanyScrap(Long userId, Long accompanyId) throws Exception {
        User user = userRepository.findById(userId).get();
        Accompany accompany = accompanyRepository.findById(accompanyId).get();
        Optional<Accompanyscrap> accompanyscrap = accompanyscrapRepository.findByUserAndAccompany(user,accompany);

        if(accompanyscrap.isEmpty()) { //참여신청
            accompanyscrapRepository.save(new Accompanyscrap(null, user,accompany));
            return true;
        } else { //참여중, 참여취소
            accompanyscrapRepository.deleteById(accompanyscrap.get().getScrapId());
            return false;
        }
    }
}
