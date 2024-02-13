package com.dea42.aitools.service;


import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.dea42.aitools.entity.Servers;
import com.dea42.aitools.paging.Column;
import com.dea42.aitools.paging.Direction;
import com.dea42.aitools.paging.Order;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.repo.ServersRepository;
import com.dea42.aitools.search.SearchCriteria;
import com.dea42.aitools.search.SearchOperation;
import com.dea42.aitools.search.SearchSpecification;
import com.dea42.aitools.search.SearchType;
import com.dea42.aitools.search.ServersSearchForm;

import lombok.extern.slf4j.Slf4j;


/**
 * Title: ServersServices <br>
 * Description: ServersServices. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Service
public class ServersServices {
    @Autowired
    private ServersRepository serversRepository;

	public Page<Servers> listAll(ServersSearchForm form) {
		SearchSpecification<Servers> searchSpec = new SearchSpecification<Servers>();
		if (form != null) {
			log.debug(form.toString());
			searchSpec.setDoOr(form.getDoOr());
			if (!StringUtils.isBlank(form.getModel())) {
				searchSpec.add(new SearchCriteria<String>(null,"model", form.getModel().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getUrl())) {
				searchSpec.add(new SearchCriteria<String>(null,"url", form.getUrl().toLowerCase(),
					SearchOperation.LIKE));
			}

		} else {
			form = new ServersSearchForm();
		}

		// OR queries assume at least one SearchCriteria or return nothing
		if (searchSpec.getList().isEmpty()) {
			searchSpec.setDoOr(SearchType.ADD);
		}
		Pageable pageable = PageRequest.of(form.getPage() - 1, form.getPageSize(), form.getSort());

		if (log.isInfoEnabled())
			log.info("searchSpec:" + searchSpec);
		return serversRepository.findAll(searchSpec, pageable);
	}

	public Servers save(Servers servers) {
		return serversRepository.save(servers);
	}
	
	public Servers get(Integer id) {
		return serversRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		serversRepository.deleteById(id);
	}

	public PageInfo<Servers> getServerss(HttpServletRequest request, PagingRequest pagingRequest) {

		ServersSearchForm form =  (ServersSearchForm) request.getSession().getAttribute("serversSearchForm");

		if (form == null ) {
			form = new ServersSearchForm();
		} else if (StringUtils.isNotBlank(pagingRequest.getSearch().getValue())) {

			String value = pagingRequest.getSearch().getValue();
			log.info("Searching for:" + value);
			form.setModel(value);
			form.setUrl(value);
			form.setDoOr(SearchType.OR);
			form.setAdvanced(false);
		} else if (!form.isAdvanced() && StringUtils.isBlank(pagingRequest.getSearch().getValue())) {
			form = new ServersSearchForm();

		}
		form.setPage((pagingRequest.getStart() / pagingRequest.getLength()) + 1);
		form.setPageSize(pagingRequest.getLength());
		Order order = pagingRequest.getOrder().get(0);
		int columnIndex = order.getColumn();
		Column column = pagingRequest.getColumns().get(columnIndex);
		form.setSortField(column.getData());
		form.setSortAsc(order.getDir().equals(Direction.asc));

		Page<Servers> filtered = listAll(form);
		int count = (int) filtered.getTotalElements();

		PageInfo<Servers> pageInfo = new PageInfo<Servers>(filtered);
		pageInfo.setRecordsFiltered(count);
		pageInfo.setRecordsTotal(count);
		pageInfo.setDraw(pagingRequest.getDraw());

		request.getSession().setAttribute("serversSearchForm", form);


		return pageInfo;
	}


}
