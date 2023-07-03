package com.pickupluck.ecogging.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
    private Integer allPage;
    private Integer curPage;
    private Integer startPage;
    private Integer endPage;
}
