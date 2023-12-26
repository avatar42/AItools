package com.dea42.aitools.search;

import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.utils.MessageHelper;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * Title: detectionsSearchForm <br>
 * Description: Class for holding data from the detections table for searching. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Data
public class DetectionsSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;

/* info=ColInfo(fNum=3, colName=Classid, msgKey=Detections.classid, vName=classid, type=Integer, jtype=null, stype=4, gsName=Classid, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment=null) */
	private Integer classidMin;
	private Integer classidMax;
/* info=ColInfo(fNum=8, colName=Confidence, msgKey=Detections.confidence, vName=confidence, type=BigDecimal, jtype=null, stype=6, gsName=Confidence, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=10, colPrecision=2000000000, comment=null) */
	private BigDecimal confidenceMin;
	private BigDecimal confidenceMax;
/* info=ColInfo(fNum=1, colName=id, msgKey=Detections.id, vName=id, type=Integer, jtype=null, stype=4, gsName=Id, length=0, pk=true, defaultVal=null, constraint=null, required=true, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment= * Table name: detections<br>
 * Column name: id<br>
 * Catalog name: null<br>
 * Primary key sequence: 0<br>
 * Primary key name: null<br>
 *  <br>) */
	private Integer idMin;
	private Integer idMax;
/* info=ColInfo(fNum=2, colName=Picid, msgKey=Detections.picid, vName=picid, type=Integer, jtype=null, stype=4, gsName=Picid, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment=null) */
	private Integer picidMin;
	private Integer picidMax;
/* info=ColInfo(fNum=6, colName=XMax, msgKey=Detections.xmax, vName=xmax, type=BigDecimal, jtype=null, stype=6, gsName=Xmax, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=10, colPrecision=2000000000, comment=null) */
	private BigDecimal xmaxMin;
	private BigDecimal xmaxMax;
/* info=ColInfo(fNum=4, colName=XMin, msgKey=Detections.xmin, vName=xmin, type=BigDecimal, jtype=null, stype=6, gsName=Xmin, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=10, colPrecision=2000000000, comment=null) */
	private BigDecimal xminMin;
	private BigDecimal xminMax;
/* info=ColInfo(fNum=7, colName=YMax, msgKey=Detections.ymax, vName=ymax, type=BigDecimal, jtype=null, stype=6, gsName=Ymax, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=10, colPrecision=2000000000, comment=null) */
	private BigDecimal ymaxMin;
	private BigDecimal ymaxMax;
/* info=ColInfo(fNum=5, colName=YMin, msgKey=Detections.ymin, vName=ymin, type=BigDecimal, jtype=null, stype=6, gsName=Ymin, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=10, colPrecision=2000000000, comment=null) */
	private BigDecimal yminMin;
	private BigDecimal yminMax;
	private String sortField = "id";
	private int page = 1;
	private int pageSize = 10;
	private boolean sortAsc = true;
	private int totalPages = 0;
	private long totalItems = 0;
	private SearchType doOr = SearchType.ADD;
	private boolean advanced = true;
	/**
	 * Clones Detections obj into form
	 *
	 * @param obj
	 */
	public static DetectionsSearchForm getInstance(Detections obj) {
		DetectionsSearchForm form = new DetectionsSearchForm();
		form.setClassidMin(obj.getClassid());
		form.setClassidMax(obj.getClassid());
		form.setConfidenceMin(obj.getConfidence());
		form.setConfidenceMax(obj.getConfidence());
		form.setIdMin(obj.getId());
		form.setIdMax(obj.getId());
		form.setPicidMin(obj.getPicid());
		form.setPicidMax(obj.getPicid());
		form.setXmaxMin(obj.getXmax());
		form.setXmaxMax(obj.getXmax());
		form.setXminMin(obj.getXmin());
		form.setXminMax(obj.getXmin());
		form.setYmaxMin(obj.getYmax());
		form.setYmaxMax(obj.getYmax());
		form.setYminMin(obj.getYmin());
		form.setYminMax(obj.getYmin());
		return form;
	}

	/**
	 * Generate a Sort from fields
	 * @return
	 */
	public Sort getSort() {
		if (sortAsc)
			return Sort.by(sortField).ascending();

		return Sort.by(sortField).descending();
	}

	public String getSortDir() {
		if (sortAsc)
			return "asc";
		else
			return "desc";
	}

	public String getReverseSortDir() {
		if (sortAsc)
			return "desc";
		else
			return "asc";
	}

	boolean getSortAscFlip() {
		return !sortAsc;
	}
}
