package com.dea42.aitools.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.dea42.aitools.entity.Servers;

import lombok.Data;

/**
 * Title: servers Form <br>
 * Description: Class for holding data from the servers table for editing. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */

@Data
public class ServersForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id = 0;
    @Length(max=13)
	private String model;
    @Length(max=39)
	private String url;

	/**
	 * Clones Servers obj into form
	 *
	 * @param obj
	 */
	public static ServersForm getInstance(Servers obj) {
		ServersForm form = new ServersForm();
		if (obj != null) {
			form.setId(obj.getId());
			form.setModel(obj.getModel());
			form.setUrl(obj.getUrl());
		}
		return form;
	}
}
