package com.dea42.aitools.paging;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Search paging support class
 * 
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 *
 */
@Getter
@Setter
@NoArgsConstructor
public class PageInfo<T> {

	public PageInfo(List<T> data) {
		this.data = data;
	}

	public PageInfo(Page<T> data) {
		this.data = data.getContent();
	}

	private List<T> data;
	private int recordsFiltered;
	private int recordsTotal;
	private int draw;

}
