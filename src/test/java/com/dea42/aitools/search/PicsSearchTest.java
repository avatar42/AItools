package com.dea42.aitools.search;


import com.dea42.aitools.UnitBase;
import com.dea42.aitools.entity.Pics;
import com.dea42.aitools.search.PicsSearchForm;
import com.dea42.aitools.service.PicsServices;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Length;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Title: picsSearch Test <br>
 * Description: Does regression tests of pics search from service to DB <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class PicsSearchTest extends UnitBase {

	@Autowired
	private PicsServices picsServices;

	private Page<Pics> confirmGotResult(PicsSearchForm form, Integer expectedID) {
		log.info("form:"+form);
		Page<Pics> list = picsServices.listAll(form);
		assertNotNull("Checking return not null", list);
		assertTrue("Checking at least 1 return", list.toList().size() > 0);
		if (expectedID > 0) {
			boolean found = false;
			for (Pics s2 : list) {
				if (s2.getId().equals(expectedID))
					found = true;
				log.info(s2.toString());
			}

			assertTrue("Looking for record ID " + expectedID + " in results", found);
		}
		return list;
	}

	private Pics getMidRecord(PicsSearchForm form, Integer expectedID) {
		Page<Pics> list = confirmGotResult(form, expectedID);
		assertNotNull("Checking return not null", list);
		int size = list.toList().size();
		assertTrue("Checking at least 1 return", size > 0);
		int record = 0;
		if (size > 2)
			record = size / 2;
		return list.toList().get(record);


	}

	@Test
	public void testCatagory() {
		// catagory String 12
		Pics rec = null;
		PicsSearchForm form = new PicsSearchForm();
		rec = getMidRecord(form, 0);
		form.setCatagory("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with catagory of " + rec.getCatagory());

		form = new PicsSearchForm();
		String text = rec.getCatagory();
		if (text.length() < 2) {
			form.setCatagory(text + "%");
			confirmGotResult(form, rec.getId());

			form.setCatagory("%" + text);
			confirmGotResult(form, rec.getId());
			form.setCatagory("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setCatagory(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setCatagory("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setCatagory("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}

	@Test
	public void testClassname() {
		// classname String 12
		Pics rec = null;
		PicsSearchForm form = new PicsSearchForm();
		rec = getMidRecord(form, 0);
		form.setClassname("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with classname of " + rec.getClassname());

		form = new PicsSearchForm();
		String text = rec.getClassname();
		if (text.length() < 2) {
			form.setClassname(text + "%");
			confirmGotResult(form, rec.getId());

			form.setClassname("%" + text);
			confirmGotResult(form, rec.getId());
			form.setClassname("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setClassname(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setClassname("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setClassname("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}

	@Test
	public void testFilename() {
		// filename String 12
		Pics rec = null;
		PicsSearchForm form = new PicsSearchForm();
		rec = getMidRecord(form, 0);
		form.setFilename("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with filename of " + rec.getFilename());

		form = new PicsSearchForm();
		String text = rec.getFilename();
		if (text.length() < 2) {
			form.setFilename(text + "%");
			confirmGotResult(form, rec.getId());

			form.setFilename("%" + text);
			confirmGotResult(form, rec.getId());
			form.setFilename("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setFilename(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setFilename("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setFilename("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}

	@Test
	public void testGrp() {
		// grp String 12
		Pics rec = null;
		PicsSearchForm form = new PicsSearchForm();
		rec = getMidRecord(form, 0);
		form.setGrp("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with grp of " + rec.getGrp());

		form = new PicsSearchForm();
		String text = rec.getGrp();
		if (text.length() < 2) {
			form.setGrp(text + "%");
			confirmGotResult(form, rec.getId());

			form.setGrp("%" + text);
			confirmGotResult(form, rec.getId());
			form.setGrp("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setGrp(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setGrp("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setGrp("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}

	@Test
	public void testNight() {
		// night Integer 4
		Pics rec = null;
		PicsSearchForm form = new PicsSearchForm();
		rec = getMidRecord(form, 0);
		form.setNightMin(Integer.MIN_VALUE);
		rec = getMidRecord(form, 0);
		log.info("Searching for records with night of " + rec.getNight());

		form = new PicsSearchForm();
		form.setNightMin(rec.getNight());
		form.setNightMax(rec.getNight() + 1);
		confirmGotResult(form, rec.getId());

		form = new PicsSearchForm();
		form.setNightMin(rec.getNight() - 1);
		form.setNightMax(rec.getNight());
		confirmGotResult(form, rec.getId());

		form = new PicsSearchForm();
		form.setNightMin(rec.getNight());
		confirmGotResult(form, rec.getId());

		form = new PicsSearchForm();
		form.setNightMax(rec.getNight());
		confirmGotResult(form, rec.getId());

		form = new PicsSearchForm();
		form.setNightMin(rec.getNight());
		form.setNightMax(rec.getNight());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testPath() {
		// path String 12
		Pics rec = null;
		PicsSearchForm form = new PicsSearchForm();
		rec = getMidRecord(form, 0);
		form.setPath("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with path of " + rec.getPath());

		form = new PicsSearchForm();
		String text = rec.getPath();
		if (text.length() < 2) {
			form.setPath(text + "%");
			confirmGotResult(form, rec.getId());

			form.setPath("%" + text);
			confirmGotResult(form, rec.getId());
			form.setPath("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setPath(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setPath("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setPath("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}
}
