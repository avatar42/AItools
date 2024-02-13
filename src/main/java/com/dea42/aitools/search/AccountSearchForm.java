package com.dea42.aitools.search;

import java.io.Serializable;

import org.springframework.data.domain.Sort;

import com.dea42.aitools.entity.Account;

import lombok.Data;


/**
 * Title: accountSearchForm <br>
 * Description: Class for holding data from the account table for searching. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Data
public class AccountSearchForm implements Serializable {
	private static final long serialVersionUID = 1L;

	private String email = "";
/* info=ColInfo(fNum=1, colName=id, msgKey=Account.id, vName=id, type=Integer, jtype=null, stype=4, gsName=Id, length=0, pk=true, defaultVal=null, constraint=null, required=true, list=false, jsonIgnore=false, unique=false, hidden=false, password=false, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment= * Table name: account<br>
 * Column name: id<br>
 * Catalog name: null<br>
 * Primary key sequence: 0<br>
 * Primary key name: null<br>
 *  <br>) */
	private Integer idMin;
	private Integer idMax;
	private String name = "";
	private String password = "";
	private String userrole = "";
	private String sortField = "id";
	private int page = 1;
	private int pageSize = 10;
	private boolean sortAsc = true;
	private int totalPages = 0;
	private long totalItems = 0;
	private SearchType doOr = SearchType.ADD;
	private boolean advanced = true;
	/**
	 * Clones Account obj into form
	 *
	 * @param obj
	 */
	public static AccountSearchForm getInstance(Account obj) {
		AccountSearchForm form = new AccountSearchForm();
		form.setEmail(obj.getEmail());
		form.setIdMin(obj.getId());
		form.setIdMax(obj.getId());
		form.setName(obj.getName());
		form.setPassword(obj.getPassword());
		form.setUserrole(obj.getUserrole());
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
