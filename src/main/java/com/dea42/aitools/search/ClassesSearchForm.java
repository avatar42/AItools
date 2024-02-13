package com.dea42.aitools.search;

import java.io.Serializable;

import org.springframework.data.domain.Sort;

import com.dea42.aitools.entity.Classes;

import lombok.Data;


/**
 * Title: classesSearchForm <br>
 * Description: Class for holding data from the classes table for searching. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Data
public class ClassesSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;

/* info=ColInfo(fNum=5, colName=Active, msgKey=Classes.active, vName=active, type=Integer, jtype=null, stype=4, gsName=Active, length=0, pk=false, defaultVal=null, constraint=null, required=false, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment=null) */
	private Integer activeMin;
	private Integer activeMax;
	private String catagory = "";
	private String classname = "";
	private String grp = "";
/* info=ColInfo(fNum=1, colName=id, msgKey=Classes.id, vName=id, type=Integer, jtype=null, stype=4, gsName=Id, length=0, pk=true, defaultVal=null, constraint=null, required=true, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment= * Table name: classes<br>
 * Column name: id<br>
 * Catalog name: null<br>
 * Primary key sequence: 0<br>
 * Primary key name: null<br>
 *  <br>) */
	private Integer idMin;
	private Integer idMax;
	private String replacewith = "";
	private String sortField = "id";
	private int page = 1;
	private int pageSize = 10;
	private boolean sortAsc = true;
	private int totalPages = 0;
	private long totalItems = 0;
	private SearchType doOr = SearchType.ADD;
	private boolean advanced = true;
	/**
	 * Clones Classes obj into form
	 *
	 * @param obj
	 */
	public static ClassesSearchForm getInstance(Classes obj) {
		ClassesSearchForm form = new ClassesSearchForm();
		form.setActiveMin(obj.getActive());
		form.setActiveMax(obj.getActive());
		form.setCatagory(obj.getCatagory());
		form.setClassname(obj.getClassname());
		form.setGrp(obj.getGrp());
		form.setIdMin(obj.getId());
		form.setIdMax(obj.getId());
		form.setReplacewith(obj.getReplacewith());
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
