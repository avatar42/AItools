package com.dea42.aitools.search;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.dea42.aitools.UnitBase;
import com.dea42.aitools.entity.Servers;
import com.dea42.aitools.service.ServersServices;

import lombok.extern.slf4j.Slf4j;


/**
 * Title: serversSearch Test <br>
 * Description: Does regression tests of servers search from service to DB <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ServersSearchTest extends UnitBase {

	@Autowired
	private ServersServices serversServices;

	private Page<Servers> confirmGotResult(ServersSearchForm form, Integer expectedID) {
		log.info("form:"+form);
		Page<Servers> list = serversServices.listAll(form);
		assertNotNull("Checking return not null", list);
		assertTrue("Checking at least 1 return", list.toList().size() > 0);
		if (expectedID > 0) {
			boolean found = false;
			for (Servers s2 : list) {
				if (s2.getId().equals(expectedID))
					found = true;
				log.info(s2.toString());
			}

			assertTrue("Looking for record ID " + expectedID + " in results", found);
		}
		return list;
	}

	private Servers getMidRecord(ServersSearchForm form, Integer expectedID) {
		Page<Servers> list = confirmGotResult(form, expectedID);
		assertNotNull("Checking return not null", list);
		int size = list.toList().size();
		assertTrue("Checking at least 1 return", size > 0);
		int record = 0;
		if (size > 2)
			record = size / 2;
		return list.toList().get(record);


	}

	@Test
	public void testModel() {
		// model String 12
		Servers rec = null;
		ServersSearchForm form = new ServersSearchForm();
		rec = getMidRecord(form, 0);
		form.setModel("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with model of " + rec.getModel());

		form = new ServersSearchForm();
		String text = rec.getModel();
		if (text.length() < 2) {
			form.setModel(text + "%");
			confirmGotResult(form, rec.getId());

			form.setModel("%" + text);
			confirmGotResult(form, rec.getId());
			form.setModel("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setModel(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setModel("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setModel("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}

	@Test
	public void testUrl() {
		// url String 12
		Servers rec = null;
		ServersSearchForm form = new ServersSearchForm();
		rec = getMidRecord(form, 0);
		form.setUrl("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with url of " + rec.getUrl());

		form = new ServersSearchForm();
		String text = rec.getUrl();
		if (text.length() < 2) {
			form.setUrl(text + "%");
			confirmGotResult(form, rec.getId());

			form.setUrl("%" + text);
			confirmGotResult(form, rec.getId());
			form.setUrl("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setUrl(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setUrl("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setUrl("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}
}
