package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.repository.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShareServiceImpl {

    private final ShareRepository shareRepository;
}
