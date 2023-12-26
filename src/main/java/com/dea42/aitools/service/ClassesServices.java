package com.dea42.aitools.service;


import com.dea42.aitools.entity.Classes;
import com.dea42.aitools.paging.Column;
import com.dea42.aitools.paging.Direction;
import com.dea42.aitools.paging.Order;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.repo.ClassesRepository;
import com.dea42.aitools.search.ClassesSearchForm;
import com.dea42.aitools.search.SearchCriteria;
import com.dea42.aitools.search.SearchOperation;
import com.dea42.aitools.search.SearchSpecification;
import com.dea42.aitools.search.SearchType;
import com.dea42.aitools.utils.Utils;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Title: ClassesServices <br>
 * Description: ClassesServices. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Service
public class ClassesServices {
    @Autowired
    private ClassesRepository classesRepository;

	public Page<Classes> listAll(ClassesSearchForm form) {
		SearchSpecification<Classes> searchSpec = new SearchSpecification<Classes>();
		if (form != null) {
			log.debug(form.toString());
			searchSpec.setDoOr(form.getDoOr());
			if (form.getActiveMin() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"active", form.getActiveMin(),
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getActiveMax() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"active", form.getActiveMax(),
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (!StringUtils.isBlank(form.getCatagory())) {
				searchSpec.add(new SearchCriteria<String>(null,"catagory", form.getCatagory().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getClassname())) {
				searchSpec.add(new SearchCriteria<String>(null,"classname", form.getClassname().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getGrp())) {
				searchSpec.add(new SearchCriteria<String>(null,"grp", form.getGrp().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getReplacewith())) {
				searchSpec.add(new SearchCriteria<String>(null,"replacewith", form.getReplacewith().toLowerCase(),
					SearchOperation.LIKE));
			}

		} else {
			form = new ClassesSearchForm();
		}

		// OR queries assume at least one SearchCriteria or return nothing
		if (searchSpec.getList().isEmpty()) {
			searchSpec.setDoOr(SearchType.ADD);
		}
		Pageable pageable = PageRequest.of(form.getPage() - 1, form.getPageSize(), form.getSort());

		if (log.isInfoEnabled())
			log.info("searchSpec:" + searchSpec);
		return classesRepository.findAll(searchSpec, pageable);
	}

	public Classes save(Classes classes) {
		return classesRepository.save(classes);
	}
	
	public Classes get(Integer id) {
		return classesRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		classesRepository.deleteById(id);
	}

	public PageInfo<Classes> getClassess(HttpServletRequest request, PagingRequest pagingRequest) {

		ClassesSearchForm form =  (ClassesSearchForm) request.getSession().getAttribute("classesSearchForm");

		if (form == null ) {
			form = new ClassesSearchForm();
		} else if (StringUtils.isNotBlank(pagingRequest.getSearch().getValue())) {

			String value = pagingRequest.getSearch().getValue();
			log.info("Searching for:" + value);
			form.setCatagory(value);
			form.setClassname(value);
			form.setGrp(value);
			form.setReplacewith(value);
			form.setDoOr(SearchType.OR);
			form.setAdvanced(false);
		} else if (!form.isAdvanced() && StringUtils.isBlank(pagingRequest.getSearch().getValue())) {
			form = new ClassesSearchForm();

		}
		form.setPage((pagingRequest.getStart() / pagingRequest.getLength()) + 1);
		form.setPageSize(pagingRequest.getLength());
		Order order = pagingRequest.getOrder().get(0);
		int columnIndex = order.getColumn();
		Column column = pagingRequest.getColumns().get(columnIndex);
		form.setSortField(column.getData());
		form.setSortAsc(order.getDir().equals(Direction.asc));

		Page<Classes> filtered = listAll(form);
		int count = (int) filtered.getTotalElements();

		PageInfo<Classes> pageInfo = new PageInfo<Classes>(filtered);
		pageInfo.setRecordsFiltered(count);
		pageInfo.setRecordsTotal(count);
		pageInfo.setDraw(pagingRequest.getDraw());

		request.getSession().setAttribute("classesSearchForm", form);


		return pageInfo;
	}


}
