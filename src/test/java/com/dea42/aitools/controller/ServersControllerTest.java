package com.dea42.aitools.controller;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import com.dea42.aitools.MockBase;
import com.dea42.aitools.entity.Servers;
import com.dea42.aitools.form.ServersForm;
import com.dea42.aitools.search.ServersSearchForm;
import com.google.common.collect.ImmutableMap;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: ServersControllerTest <br>
 * Description: ServersController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@WebMvcTest(ServersController.class)
public class ServersControllerTest extends MockBase {
	private Servers getServers(Integer id) {
		Servers o = new Servers();
		o.setId(id);
        o.setModel(getTestString(13));
        o.setUrl(getTestString(39));
		return o;
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ServersController#getAllServerss(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllServerss() throws Exception {
		List<Servers> list = new ArrayList<>();
		Servers o = getServers(1);
		list.add(o);

		Page<Servers> p = getPage(list);
		given(serversServices.listAll(new ServersSearchForm())).willReturn(p);

		ResultActions ra = getAsAdmin("/serverss");
		contentContainsMarkup(ra,"<h1>" + getMsg("class.Servers") + " " + getMsg("edit.list") + "</h1>");
//		contentContainsMarkup(ra,getTestString(13));
//		contentContainsMarkup(ra,getMsg("Servers.model"));
//		contentContainsMarkup(ra,getTestString(39));
//		contentContainsMarkup(ra,getMsg("Servers.url"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ServersController#showNewServersPage(org.springframework.ui.Model)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowNewServersPage() throws Exception {
		ResultActions ra = getAsAdmin("/serverss/new");
		contentContainsMarkup(ra,"<legend>" + getMsg("edit.new") + " " + getMsg("class.Servers") + "</legend>");
		// TODO: confirm ignoring Servers.id
		contentContainsMarkup(ra,getMsg("Servers.model"));
		contentContainsMarkup(ra,getMsg("Servers.url"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ServersController#saveServers(com.dea42.aitools.entity.Servers, java.lang.String)}.
	 */
	@Test
	public void testSaveServersCancel() throws Exception {
		Servers o = getServers(1);

		send(SEND_POST, "/serverss/save", "servers", o, ImmutableMap.of("action", "cancel"), ADMIN_EMAIL,
				"/serverss");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ServersController#saveServers(com.dea42.aitools.entity.Servers, java.lang.String)}.
	 */
	@Test
	public void testSaveServersSave() throws Exception {
		Servers o = getServers(0);
		ServersForm form = ServersForm.getInstance(o);
		log.debug(form.toString());

		send(SEND_POST, "/serverss/save", "serversForm", form, ImmutableMap.of("action", "save"), ADMIN_EMAIL,
				"/serverss");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ServersController#showEditServersPage(java.lang.Integer)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowEditServersPage() throws Exception {
		Servers o = getServers(1);

		given(serversServices.get(1)).willReturn(o);

		ResultActions ra = getAsAdmin("/serverss/edit/1");
		// TODO: confirm ignoring Servers.id
		contentContainsMarkup(ra,o.getModel());
		contentContainsMarkup(ra,"Model");
		contentContainsMarkup(ra,o.getUrl());
		contentContainsMarkup(ra,"Url");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ServersController#deleteServers(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteServers() throws Exception {
		getAsAdminRedirectExpected("/serverss/delete/1","/serverss");
	}

}

