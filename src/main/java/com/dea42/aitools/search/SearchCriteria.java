package com.dea42.aitools.search;

import lombok.Data;

/**
 * Class for holding Criteria info
 * 
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 *
 */
@Data
public class SearchCriteria<T> {
	private String key;
	// field to joined on 
	private String join;
	private Object value;
	private SearchOperation operation;

	public SearchCriteria() {
	}

	public SearchCriteria(String join, String key, T value, SearchOperation operation) {
		this.join = join;
		this.key = key;
		this.value = value;
		this.operation = operation;
	}

}
