package com.pickupluck.ecogging.util;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PageInfo {
	private Integer allPage;
	private Integer curPage;
	private Integer startPage;
	private Integer endPage;
	private boolean isLastPage; // isLastPage 필드 추가

	public boolean getIsLastPage() {
		return isLastPage;
	}

	public void setIsLastPage(boolean isLastPage) {
		this.isLastPage = isLastPage;
	}
}