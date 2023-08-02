package com.pickupluck.ecogging.domain.plogging.service;

import com.pickupluck.ecogging.domain.BoardType;
import com.pickupluck.ecogging.domain.comment.repository.CommentRepository;
import com.pickupluck.ecogging.domain.notification.dto.NotificationSaveDto;
import com.pickupluck.ecogging.domain.notification.entity.NotificationType;
import com.pickupluck.ecogging.domain.notification.service.NotificationService;
import com.pickupluck.ecogging.domain.plogging.dto.AccompanyDTO;
import com.pickupluck.ecogging.domain.plogging.entity.Accompany;
import com.pickupluck.ecogging.domain.plogging.entity.Participation;
import com.pickupluck.ecogging.domain.plogging.repository.AccompanyRepository;
import com.pickupluck.ecogging.domain.plogging.repository.ParticipationRepository;
import com.pickupluck.ecogging.domain.scrap.entity.Accompanyscrap;
import com.pickupluck.ecogging.domain.scrap.repository.AccompanyscrapRepository;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;
import com.pickupluck.ecogging.util.PageInfo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class AccompanyServiceImpl implements AccompanyService {

    private final AccompanyRepository accompanyRepository;
    private final ParticipationRepository participationRepository;
    private final AccompanyscrapRepository accompanyscrapRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final Integer onePage = 10;

    private final NotificationService notificationService;
    private final CommentRepository commentRepository;

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
            accompany.setUser(user.get());
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
    @Transactional
    public void removeAccompany(Long id) throws Exception {
        accompanyRepository.deleteById(id);
        commentRepository.deleteByBoardTypeAndArticleId(BoardType.ACCOMPANY, id);
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
            participationRepository.save(new Participation(userId,accompany,false));
            accompany.setJoincnt(accompany.getJoincnt()+1);
            accompanyRepository.save(accompany);

            // notification
            // 발송: 동행 참여자
            final Long notiSenderId = userId;
            // 수신: 게시글 작성자
            final Long notiReceiverId = accompany.getUser().getId();
            // 타겟 : 게시글 아이디
            final Long notiTargetId = accompanyId;

            final String notiDetail = accompany.getTitle();

            final NotificationType notiType = NotificationType.ACCOMPANY;

            notificationService.createNotification(
                    NotificationSaveDto.builder()
                            .receiverId(notiReceiverId)
                            .senderId(notiSenderId)
                            .targetId(notiTargetId)
                            .detail(notiDetail)
                            .type(notiType)
                            .build()
                    );

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
            accompanyscrapRepository.save(new Accompanyscrap(user, accompany));
            return true;
        } else { //참여중, 참여취소
            accompanyscrapRepository.deleteById(accompanyscrap.get().getScrapId());
            return false;
        }
    }

    @Override
    public Map<String, Object> getMainAccompanyList() throws Exception {
        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();

        List<Accompany> listLatest = accompanyRepository.findTop3ByOrderByCreatedAtDesc(); // 상위 3개의 데이터 가져오기

        for (Accompany accomp : listLatest) { // Accompany -> AccompanyDTO로 변환해서 List로 만들어주고
            AccompanyDTO accompanyDTO = new AccompanyDTO(accomp);
            list.add(accompanyDTO);
        }
        map.put("list", list); // list통째로 map에 'list' key : 리스트 value 로 넣어서 리턴
        return map;
    }

    private PageInfo calcPage(Integer allPage, Integer page) {
        PageInfo pageInfo = new PageInfo();
        pageInfo.setCurPage(page);
        pageInfo.setAllPage(allPage);
        int startPage = (page-1)/10*10+1;
        int endPage = startPage+10-1;
        if(endPage>pageInfo.getAllPage()) endPage=pageInfo.getAllPage();
        pageInfo.setStartPage(startPage);
        pageInfo.setEndPage(endPage);
        return pageInfo;
    }

    @Override
    public Map<String, Object> getMyAccompanyList(Long userId, Integer page) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1,5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Accompany> accompanyPage = accompanyRepository.findByUserIdAndSaveFalse(userId, pageRequest);

        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();
        for(Accompany accompany : accompanyPage.getContent()){
            AccompanyDTO accompanyDTO = new AccompanyDTO(accompany);
            list.add(accompanyDTO);
        }
        map.put("list", list);
        PageInfo pageInfo = calcPage(accompanyPage.getTotalPages(), page);
        map.put("pageInfo", pageInfo);
        return map;
    }

    @Override
    public Map<String, Object> getMyAccompanyTempList(Long userId, Integer page) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1,5, Sort.by(Sort.Direction.DESC, "id"));
        Page<Accompany> accompanyPage = accompanyRepository.findByUserIdAndSaveTrue(userId, pageRequest);
        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();
        for(Accompany accompany : accompanyPage.getContent()){
            AccompanyDTO accompanyDTO = new AccompanyDTO(accompany);
            list.add(accompanyDTO);
        }
        map.put("list", list);
        PageInfo pageInfo = calcPage(accompanyPage.getTotalPages(), page);
        map.put("pageInfo", pageInfo);
        return map;
    }

    @Override
    public Map<String, Object> getMyParticipationList(Long userId, Integer page) throws Exception {

        PageRequest pageRequest = PageRequest.of(page-1,5, Sort.by(Sort.Direction.DESC, "participationId"));
        Page<Participation> participationPage = participationRepository.findByUserId(userId, pageRequest);

        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();
        for(Participation participation : participationPage.getContent()){
            AccompanyDTO accompanyDTO = new AccompanyDTO(participation.getAccompany());
            list.add(accompanyDTO);
        }
        map.put("list", list);
        PageInfo pageInfo = calcPage(participationPage.getTotalPages(), page);
        map.put("pageInfo", pageInfo);

        return map;
    }

    @Override
    public Map<String, Object> getMyAccompanyscrapList(Long userId, Integer page) throws Exception {
        PageRequest pageRequest = PageRequest.of(page-1,5, Sort.by(Sort.Direction.DESC, "scrapId"));
        Page<Accompanyscrap>  accompanyscrapPage = accompanyscrapRepository.findByUserId(userId, pageRequest);

        Map<String, Object> map = new HashMap<>();
        List<AccompanyDTO> list = new ArrayList<>();
        for(Accompanyscrap accompanyscrap : accompanyscrapPage.getContent()){
            AccompanyDTO accompanyDTO = new AccompanyDTO(accompanyscrap.getAccompany());
            list.add(accompanyDTO);
        }

        map.put("list", list);
        PageInfo pageInfo = calcPage(accompanyscrapPage.getTotalPages(), page);
        map.put("pageInfo", pageInfo);

        return map;
    }
}
