package com.dea42.aitools.form;

import java.io.Serializable;
import java.math.BigDecimal;

import com.dea42.aitools.entity.Detections;

import lombok.Data;

/**
 * Title: detections Form <br>
 * Description: Class for holding data from the detections table for editing. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */

@Data
public class DetectionsForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer classid;
	private BigDecimal confidence;
	private Integer id = 0;
	private Integer picid;
	private BigDecimal xmax;
	private BigDecimal xmin;
	private BigDecimal ymax;
	private BigDecimal ymin;

	/**
	 * Clones Detections obj into form
	 *
	 * @param obj
	 */
	public static DetectionsForm getInstance(Detections obj) {
		DetectionsForm form = new DetectionsForm();
		if (obj != null) {
			form.setClassid(obj.getClassid());
			form.setConfidence(obj.getConfidence());
			form.setId(obj.getId());
			form.setPicid(obj.getPicid());
			form.setXmax(obj.getXmax());
			form.setXmin(obj.getXmin());
			form.setYmax(obj.getYmax());
			form.setYmin(obj.getYmin());
		}
		return form;
	}
}
