package com.pickupluck.ecogging.domain.plogging.api;

import com.pickupluck.ecogging.domain.plogging.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
}
