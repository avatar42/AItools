package com.dea42.aitools.controller;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import com.dea42.aitools.MockBase;
import com.dea42.aitools.entity.Account;
import com.dea42.aitools.form.AccountForm;
import com.dea42.aitools.search.AccountSearchForm;
import com.google.common.collect.ImmutableMap;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: AccountControllerTest <br>
 * Description: AccountController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@WebMvcTest(AccountController.class)
public class AccountControllerTest extends MockBase {
	private Account getAccount(Integer id) {
		Account o = new Account();
		o.setId(id);
        o.setEmail(getTestEmailString(254));
        o.setName(getTestString(254));
//        o.setPassword(getTestPasswordString(30));
        o.setUserrole(getTestString(25));
		return o;
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#getAllAccounts(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllAccounts() throws Exception {
		List<Account> list = new ArrayList<>();
		Account o = getAccount(1);
		list.add(o);

		Page<Account> p = getPage(list);
		given(accountServices.listAll(new AccountSearchForm())).willReturn(p);

		ResultActions ra = getAsAdmin("/accounts");
		contentContainsMarkup(ra,"<h1>" + getMsg("class.Account") + " " + getMsg("edit.list") + "</h1>");
//        contentContainsMarkup(ra,getTestEmailString(254));
//		contentContainsMarkup(ra,getMsg("Account.email"));
//		contentContainsMarkup(ra,getTestString(254));
//		contentContainsMarkup(ra,getMsg("Account.name"));
		/* TODO: confirm ignoring ColInfo(fNum=4, colName=Password, msgKey=Account.password, vName=password, type=String, jtype=null, stype=12, gsName=Password, length=30, pk=false, defaultVal=, constraint=null, required=true, list=false, jsonIgnore=true, unique=false, hidden=true, password=true, email=false, created=false, lastMod=false, adminOnly=false, foreignTable=null, foreignCol=null, colScale=0, colPrecision=0, comment=null) */
//		contentContainsMarkup(ra,getTestString(25));
//		contentContainsMarkup(ra,getMsg("Account.userrole"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#showNewAccountPage(org.springframework.ui.Model)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowNewAccountPage() throws Exception {
		ResultActions ra = getAsAdmin("/accounts/new");
		contentContainsMarkup(ra,"<legend>" + getMsg("edit.new") + " " + getMsg("class.Account") + "</legend>");
		contentContainsMarkup(ra,getMsg("Account.email"));
		// TODO: confirm ignoring Account.id
		contentContainsMarkup(ra,getMsg("Account.name"));
		// TODO: confirm ignoring Account.password
		contentContainsMarkup(ra,getMsg("Account.userrole"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#saveAccount(com.dea42.aitools.entity.Account, java.lang.String)}.
	 */
	@Test
	public void testSaveAccountCancel() throws Exception {
		Account o = getAccount(1);

		send(SEND_POST, "/accounts/save", "account", o, ImmutableMap.of("action", "cancel"), ADMIN_EMAIL,
				"/accounts");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#saveAccount(com.dea42.aitools.entity.Account, java.lang.String)}.
	 */
	@Test
	public void testSaveAccountSave() throws Exception {
		Account o = getAccount(0);
		AccountForm form = AccountForm.getInstance(o);
		form.setPassword(getTestPasswordString(30));
		form.setPasswordConfirm(form.getPassword());
		log.debug(form.toString());

		send(SEND_POST, "/accounts/save", "accountForm", form, ImmutableMap.of("action", "save"), ADMIN_EMAIL,
				"/accounts");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#showEditAccountPage(java.lang.Integer)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowEditAccountPage() throws Exception {
		Account o = getAccount(1);

		given(accountServices.get(1)).willReturn(o);

		ResultActions ra = getAsAdmin("/accounts/edit/1");
		contentContainsMarkup(ra,o.getEmail());
		contentContainsMarkup(ra,"Email");
		// TODO: confirm ignoring Account.id
		contentContainsMarkup(ra,o.getName());
		contentContainsMarkup(ra,"Name");
		// TODO: confirm ignoring Account.password
		contentContainsMarkup(ra,o.getUserrole());
		contentContainsMarkup(ra,"Userrole");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#deleteAccount(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteAccount() throws Exception {
		getAsAdminRedirectExpected("/accounts/delete/1","/accounts");
	}

}

