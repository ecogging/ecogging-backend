package com.pickupluck.ecogging.domain.user.service;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.pickupluck.ecogging.domain.user.entity.Corporate;
import com.pickupluck.ecogging.domain.user.entity.User;
import com.pickupluck.ecogging.domain.user.repository.CorporateRepository;
import com.pickupluck.ecogging.domain.user.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class CorporateServiceImpl implements CorporateService {

    private UserService userService;
    private UserRepository userRepository;
    private CorporateRepository corporateRepository;

    @Override
    public Corporate findById(Long id) {
        return corporateRepository.findById(id).orElseThrow();
    }

    @Override
    public Corporate findByName(String name) {
        return corporateRepository.findByCorpName(name).orElseThrow();
    }

    @Override
    public Corporate findByRegisterNumber(String registerNumber) {
        return corporateRepository.findByCorpRegisterNumber(registerNumber).orElseThrow();
    }

    @Override
    public Corporate findByManagerEmail(String email) {
        return corporateRepository.findByManagerEmail(email).orElseThrow();
    }

    @Override
    public Corporate findByManager(User manager) {
        return corporateRepository.findByManager(manager).orElseThrow();
    }


}
