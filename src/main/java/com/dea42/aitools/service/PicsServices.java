package com.dea42.aitools.service;


import com.dea42.aitools.entity.Pics;
import com.dea42.aitools.paging.Column;
import com.dea42.aitools.paging.Direction;
import com.dea42.aitools.paging.Order;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.repo.PicsRepository;
import com.dea42.aitools.search.PicsSearchForm;
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
 * Title: PicsServices <br>
 * Description: PicsServices. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Service
public class PicsServices {
    @Autowired
    private PicsRepository picsRepository;

	public Page<Pics> listAll(PicsSearchForm form) {
		SearchSpecification<Pics> searchSpec = new SearchSpecification<Pics>();
		if (form != null) {
			log.debug(form.toString());
			searchSpec.setDoOr(form.getDoOr());
			if (!StringUtils.isBlank(form.getCatagory())) {
				searchSpec.add(new SearchCriteria<String>(null,"catagory", form.getCatagory().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getClassname())) {
				searchSpec.add(new SearchCriteria<String>(null,"classname", form.getClassname().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getFilename())) {
				searchSpec.add(new SearchCriteria<String>(null,"filename", form.getFilename().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getGrp())) {
				searchSpec.add(new SearchCriteria<String>(null,"grp", form.getGrp().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (form.getNightMin() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"night", form.getNightMin(),
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getNightMax() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"night", form.getNightMax(),
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (!StringUtils.isBlank(form.getPath())) {
				searchSpec.add(new SearchCriteria<String>(null,"path", form.getPath().toLowerCase(),
					SearchOperation.LIKE));
			}

		} else {
			form = new PicsSearchForm();
		}

		// OR queries assume at least one SearchCriteria or return nothing
		if (searchSpec.getList().isEmpty()) {
			searchSpec.setDoOr(SearchType.ADD);
		}
		Pageable pageable = PageRequest.of(form.getPage() - 1, form.getPageSize(), form.getSort());

		if (log.isInfoEnabled())
			log.info("searchSpec:" + searchSpec);
		return picsRepository.findAll(searchSpec, pageable);
	}

	public Pics save(Pics pics) {
		return picsRepository.save(pics);
	}
	
	public Pics get(Integer id) {
		return picsRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		picsRepository.deleteById(id);
	}

	public PageInfo<Pics> getPicss(HttpServletRequest request, PagingRequest pagingRequest) {

		PicsSearchForm form =  (PicsSearchForm) request.getSession().getAttribute("picsSearchForm");

		if (form == null ) {
			form = new PicsSearchForm();
		} else if (StringUtils.isNotBlank(pagingRequest.getSearch().getValue())) {

			String value = pagingRequest.getSearch().getValue();
			log.info("Searching for:" + value);
			form.setCatagory(value);
			form.setClassname(value);
			form.setFilename(value);
			form.setGrp(value);
			form.setPath(value);
			form.setDoOr(SearchType.OR);
			form.setAdvanced(false);
		} else if (!form.isAdvanced() && StringUtils.isBlank(pagingRequest.getSearch().getValue())) {
			form = new PicsSearchForm();

		}
		form.setPage((pagingRequest.getStart() / pagingRequest.getLength()) + 1);
		form.setPageSize(pagingRequest.getLength());
		Order order = pagingRequest.getOrder().get(0);
		int columnIndex = order.getColumn();
		Column column = pagingRequest.getColumns().get(columnIndex);
		form.setSortField(column.getData());
		form.setSortAsc(order.getDir().equals(Direction.asc));

		Page<Pics> filtered = listAll(form);
		int count = (int) filtered.getTotalElements();

		PageInfo<Pics> pageInfo = new PageInfo<Pics>(filtered);
		pageInfo.setRecordsFiltered(count);
		pageInfo.setRecordsTotal(count);
		pageInfo.setDraw(pagingRequest.getDraw());

		request.getSession().setAttribute("picsSearchForm", form);


		return pageInfo;
	}


}
