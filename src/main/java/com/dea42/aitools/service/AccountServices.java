package com.dea42.aitools.service;


import com.dea42.aitools.entity.Account;
import com.dea42.aitools.paging.Column;
import com.dea42.aitools.paging.Direction;
import com.dea42.aitools.paging.Order;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.repo.AccountRepository;
import com.dea42.aitools.search.AccountSearchForm;
import com.dea42.aitools.search.SearchCriteria;
import com.dea42.aitools.search.SearchOperation;
import com.dea42.aitools.search.SearchSpecification;
import com.dea42.aitools.search.SearchType;
import com.dea42.aitools.utils.Utils;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Title: AccountServices <br>
 * Description: AccountServices. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Service
public class AccountServices extends UserServices<Account> {
    @Autowired
    private AccountRepository accountRepository;

	public static final String ROLE_PREFIX = "ROLE_";

	/**
	 * reset default users. Comment out once done testing
	 */
	@PostConstruct
	protected void initialize() {
		ResourceBundle bundle = ResourceBundle.getBundle("app");
		boolean doinit = Utils.getProp(bundle, "init.default.users", true);
		if (doinit) {
			log.warn("Resetting default users");
			String email = Utils.getProp(bundle, "default.email", null);
			String user = Utils.getProp(bundle, "default.user", null);
			if (!StringUtils.isBlank(user)) {
				Integer id = Utils.getProp(bundle, "default.userid", 1);
				String userpass = Utils.getProp(bundle, "default.userpass", null);
				String userrole = ROLE_PREFIX + Utils.getProp(bundle, "default.userrole", null);
				Account a = new Account();
				a.setEmail(email);
				a.setName(user);
				a.setPassword(userpass);
				a.setUserrole(userrole);
				a.setId(id);
				save(a);
			}

			email = Utils.getProp(bundle, "default.adminEmail", null);
			user = Utils.getProp(bundle, "default.admin", null);
			if (!StringUtils.isBlank(user)) {
				Integer id = Utils.getProp(bundle, "default.adminid", 2);
				String userpass = Utils.getProp(bundle, "default.adminpass", null);
				String userrole = ROLE_PREFIX + Utils.getProp(bundle, "default.adminrole", null);
				Account a = new Account();
				a.setEmail(email);
				a.setName(user);
				a.setPassword(userpass);
				a.setUserrole(userrole);
				a.setId(id);
				save(a);
			}
		}
	}

	public Page<Account> listAll(AccountSearchForm form) {
		SearchSpecification<Account> searchSpec = new SearchSpecification<Account>();
		if (form != null) {
			log.debug(form.toString());
			searchSpec.setDoOr(form.getDoOr());
			if (!StringUtils.isBlank(form.getEmail())) {
				searchSpec.add(new SearchCriteria<String>(null,"email", form.getEmail().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getName())) {
				searchSpec.add(new SearchCriteria<String>(null,"name", form.getName().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getPassword())) {
				searchSpec.add(new SearchCriteria<String>(null,"password", form.getPassword().toLowerCase(),
					SearchOperation.LIKE));
			}
			if (!StringUtils.isBlank(form.getUserrole())) {
				searchSpec.add(new SearchCriteria<String>(null,"userrole", form.getUserrole().toLowerCase(),
					SearchOperation.LIKE));
			}

		} else {
			form = new AccountSearchForm();
		}

		// OR queries assume at least one SearchCriteria or return nothing
		if (searchSpec.getList().isEmpty()) {
			searchSpec.setDoOr(SearchType.ADD);
		}
		Pageable pageable = PageRequest.of(form.getPage() - 1, form.getPageSize(), form.getSort());

		if (log.isInfoEnabled())
			log.info("searchSpec:" + searchSpec);
		return accountRepository.findAll(searchSpec, pageable);
	}

	public Account save(Account account) {
		Optional<Account> o = null;
		if (account.getId() > 0) {
			o = accountRepository.findById(account.getId());
		}

		if (o != null && StringUtils.isBlank(account.getPassword())) {
			account.setPassword(o.get().getPassword());
		} else {
			account.setPassword(encrypt(account.getPassword()));
		}

		return accountRepository.save(account);
	}
	
	public Account get(Integer id) {
		return accountRepository.findById(id).get();
	}
	
	public void delete(Integer id) {
		accountRepository.deleteById(id);
	}

	public PageInfo<Account> getAccounts(HttpServletRequest request, PagingRequest pagingRequest) {

		AccountSearchForm form =  (AccountSearchForm) request.getSession().getAttribute("accountSearchForm");

		if (form == null ) {
			form = new AccountSearchForm();
		} else if (StringUtils.isNotBlank(pagingRequest.getSearch().getValue())) {

			String value = pagingRequest.getSearch().getValue();
			log.info("Searching for:" + value);
			form.setEmail(value);
			form.setName(value);
			form.setUserrole(value);
			form.setDoOr(SearchType.OR);
			form.setAdvanced(false);
		} else if (!form.isAdvanced() && StringUtils.isBlank(pagingRequest.getSearch().getValue())) {
			form = new AccountSearchForm();

		}
		form.setPage((pagingRequest.getStart() / pagingRequest.getLength()) + 1);
		form.setPageSize(pagingRequest.getLength());
		Order order = pagingRequest.getOrder().get(0);
		int columnIndex = order.getColumn();
		Column column = pagingRequest.getColumns().get(columnIndex);
		form.setSortField(column.getData());
		form.setSortAsc(order.getDir().equals(Direction.asc));

		Page<Account> filtered = listAll(form);
		int count = (int) filtered.getTotalElements();

		PageInfo<Account> pageInfo = new PageInfo<Account>(filtered);
		pageInfo.setRecordsFiltered(count);
		pageInfo.setRecordsTotal(count);
		pageInfo.setDraw(pagingRequest.getDraw());

		request.getSession().setAttribute("accountSearchForm", form);


		return pageInfo;
	}


}
