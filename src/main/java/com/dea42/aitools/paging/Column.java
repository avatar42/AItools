package com.dea42.aitools.paging;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Search paging support class
 * 
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 *
 */
@Data
@NoArgsConstructor
public class Column {

	private String data;
	private String name;
	private Boolean searchable;
	private Boolean orderable;
	private Search search;
}
