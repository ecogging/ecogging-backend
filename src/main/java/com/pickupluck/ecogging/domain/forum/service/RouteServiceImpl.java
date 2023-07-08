package com.pickupluck.ecogging.domain.forum.service;

import com.pickupluck.ecogging.domain.forum.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl {

    private final RouteRepository routeRepository;
}
