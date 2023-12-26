package com.dea42.aitools.service;


import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.paging.Column;
import com.dea42.aitools.paging.Direction;
import com.dea42.aitools.paging.Order;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.repo.DetectionsRepository;
import com.dea42.aitools.search.DetectionsSearchForm;
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
 * Title: DetectionsServices <br>
 * Description: DetectionsServices. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Service
public class DetectionsServices {
    @Autowired
    private DetectionsRepository detectionsRepository;

	public Page<Detections> listAll(DetectionsSearchForm form) {
		SearchSpecification<Detections> searchSpec = new SearchSpecification<Detections>();
		if (form != null) {
			log.debug(form.toString());
			searchSpec.setDoOr(form.getDoOr());
			if (form.getClassidMin() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"classid", form.getClassidMin(),
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getClassidMax() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"classid", form.getClassidMax(),
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (form.getConfidenceMin() != null) {
				BigDecimal bd = form.getConfidenceMin();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"confidence",bd,
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getConfidenceMax() != null) {
				BigDecimal bd = form.getConfidenceMax();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_UP);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"confidence",bd,
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (form.getPicidMin() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"picid", form.getPicidMin(),
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getPicidMax() != null) {
				searchSpec.add(new SearchCriteria<Integer>(null,"picid", form.getPicidMax(),
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (form.getXmaxMin() != null) {
				BigDecimal bd = form.getXmaxMin();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"xmax",bd,
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getXmaxMax() != null) {
				BigDecimal bd = form.getXmaxMax();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_UP);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"xmax",bd,
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (form.getXminMin() != null) {
				BigDecimal bd = form.getXminMin();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"xmin",bd,
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getXminMax() != null) {
				BigDecimal bd = form.getXminMax();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_UP);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"xmin",bd,
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (form.getYmaxMin() != null) {
				BigDecimal bd = form.getYmaxMin();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"ymax",bd,
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getYmaxMax() != null) {
				BigDecimal bd = form.getYmaxMax();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_UP);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"ymax",bd,
					SearchOperation.LESS_THAN_EQUAL));
			}
			if (form.getYminMin() != null) {
				BigDecimal bd = form.getYminMin();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_DOWN);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"ymin",bd,
					SearchOperation.GREATER_THAN_EQUAL));
			}
			if (form.getYminMax() != null) {
				BigDecimal bd = form.getYminMax();
// SQLite rounds scales > 10 in select where compare though returns all decimals
				if (bd.scale() > 10) {
					bd = bd.setScale(10, BigDecimal.ROUND_UP);
				}
				searchSpec.add(new SearchCriteria<BigDecimal>(null,"ymin",bd,
					SearchOperation.LESS_THAN_EQUAL));
			}

		} else {
			form = new DetectionsSearchForm();
		}

		// OR queries assume at least one SearchCriteria or return nothing
		if (searchSpec.getList().isEmpty()) {
			searchSpec.setDoOr(SearchType.ADD);
		}
		Pageable pageable = PageRequest.of(form.getPage() - 1, form.getPageSize(), form.getSort());

		if (log.isInfoEnabled())
			log.info("searchSpec:" + searchSpec);
		return detectionsRepository.findAll(searchSpec, pageable);
	}

	public Detections save(Detections detections) {
		return detectionsRepository.save(detections);
	}
	
	public Detections get(Integer id) {
		return detectionsRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		detectionsRepository.deleteById(id);
	}

	public PageInfo<Detections> getDetectionss(HttpServletRequest request, PagingRequest pagingRequest) {

		DetectionsSearchForm form =  (DetectionsSearchForm) request.getSession().getAttribute("detectionsSearchForm");

		if (form == null ) {
			form = new DetectionsSearchForm();
		} else if (StringUtils.isNotBlank(pagingRequest.getSearch().getValue())) {

			String value = pagingRequest.getSearch().getValue();
			log.info("Searching for:" + value);
			form.setDoOr(SearchType.OR);
			form.setAdvanced(false);
		} else if (!form.isAdvanced() && StringUtils.isBlank(pagingRequest.getSearch().getValue())) {
			form = new DetectionsSearchForm();

		}
		form.setPage((pagingRequest.getStart() / pagingRequest.getLength()) + 1);
		form.setPageSize(pagingRequest.getLength());
		Order order = pagingRequest.getOrder().get(0);
		int columnIndex = order.getColumn();
		Column column = pagingRequest.getColumns().get(columnIndex);
		form.setSortField(column.getData());
		form.setSortAsc(order.getDir().equals(Direction.asc));

		Page<Detections> filtered = listAll(form);
		int count = (int) filtered.getTotalElements();

		PageInfo<Detections> pageInfo = new PageInfo<Detections>(filtered);
		pageInfo.setRecordsFiltered(count);
		pageInfo.setRecordsTotal(count);
		pageInfo.setDraw(pagingRequest.getDraw());

		request.getSession().setAttribute("detectionsSearchForm", form);


		return pageInfo;
	}


}
