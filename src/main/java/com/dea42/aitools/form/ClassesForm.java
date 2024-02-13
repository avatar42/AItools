package com.dea42.aitools.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.dea42.aitools.entity.Classes;

import lombok.Data;

/**
 * Title: classes Form <br>
 * Description: Class for holding data from the classes table for editing. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */

@Data
public class ClassesForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer active;
    @Length(max=7)
	private String catagory;
    @Length(max=16)
	private String classname;
    @Length(max=12)
	private String grp;
	private Integer id = 0;
    @Length(max=8)
	private String replacewith;

	/**
	 * Clones Classes obj into form
	 *
	 * @param obj
	 */
	public static ClassesForm getInstance(Classes obj) {
		ClassesForm form = new ClassesForm();
		if (obj != null) {
			form.setActive(obj.getActive());
			form.setCatagory(obj.getCatagory());
			form.setClassname(obj.getClassname());
			form.setGrp(obj.getGrp());
			form.setId(obj.getId());
			form.setReplacewith(obj.getReplacewith());
		}
		return form;
	}
}
