package com.pickupluck.ecogging.domain.user.service;

import com.pickupluck.ecogging.domain.user.entity.Corporate;
import com.pickupluck.ecogging.domain.user.entity.User;

public interface CorporateService {

    public Corporate findById(Long id);

    public Corporate findByName(String name);

    public Corporate findByRegisterNumber(String registerNumber);

    public Corporate findByManagerEmail(String email);

    public Corporate findByManager(User manager);
}
