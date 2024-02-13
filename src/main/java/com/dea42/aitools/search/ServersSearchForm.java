package com.dea42.aitools.search;

import java.io.Serializable;

import org.springframework.data.domain.Sort;

import com.dea42.aitools.entity.Servers;

import lombok.Data;


/**
 * Title: serversSearchForm <br>
 * Description: Class for holding data from the servers table for searching. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Data
public class ServersSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;

/* info=ColInfo(fNum=1, colName=id, msgKey=Servers.id, vName=id, type=Integer, jtype=null, stype=4, gsName=Id, length=0, pk=true, defaultVal=null, constraint=null, required=true, list=true, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment= * Table name: servers<br>
 * Column name: id<br>
 * Catalog name: null<br>
 * Primary key sequence: 0<br>
 * Primary key name: null<br>
 *  <br>) */
	private Integer idMin;
	private Integer idMax;
	private String model = "";
	private String url = "";
	private String sortField = "id";
	private int page = 1;
	private int pageSize = 10;
	private boolean sortAsc = true;
	private int totalPages = 0;
	private long totalItems = 0;
	private SearchType doOr = SearchType.ADD;
	private boolean advanced = true;
	/**
	 * Clones Servers obj into form
	 *
	 * @param obj
	 */
	public static ServersSearchForm getInstance(Servers obj) {
		ServersSearchForm form = new ServersSearchForm();
		form.setIdMin(obj.getId());
		form.setIdMax(obj.getId());
		form.setModel(obj.getModel());
		form.setUrl(obj.getUrl());
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
