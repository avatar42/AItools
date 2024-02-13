package com.dea42.aitools.form;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;

import com.dea42.aitools.entity.Pics;

import lombok.Data;

/**
 * Title: pics Form <br>
 * Description: Class for holding data from the pics table for editing. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */

@Data
public class PicsForm implements Serializable {
	private static final long serialVersionUID = 1L;

    @Length(max=6)
	private String catagory;
    @Length(max=12)
	private String classname;
    @Length(max=55)
	private String filename;
    @Length(max=7)
	private String grp;
	private Integer id = 0;
	private Integer night;
    @Length(max=26)
	private String path;

	/**
	 * Clones Pics obj into form
	 *
	 * @param obj
	 */
	public static PicsForm getInstance(Pics obj) {
		PicsForm form = new PicsForm();
		if (obj != null) {
			form.setCatagory(obj.getCatagory());
			form.setClassname(obj.getClassname());
			form.setFilename(obj.getFilename());
			form.setGrp(obj.getGrp());
			form.setId(obj.getId());
			form.setNight(obj.getNight());
			form.setPath(obj.getPath());
		}
		return form;
	}
}
