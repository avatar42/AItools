package com.dea42.aitools.search;


import com.dea42.aitools.UnitBase;
import com.dea42.aitools.entity.Classes;
import com.dea42.aitools.search.ClassesSearchForm;
import com.dea42.aitools.service.ClassesServices;
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
 * Title: classesSearch Test <br>
 * Description: Does regression tests of classes search from service to DB <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassesSearchTest extends UnitBase {

	@Autowired
	private ClassesServices classesServices;

	private Page<Classes> confirmGotResult(ClassesSearchForm form, Integer expectedID) {
		log.info("form:"+form);
		Page<Classes> list = classesServices.listAll(form);
		assertNotNull("Checking return not null", list);
		assertTrue("Checking at least 1 return", list.toList().size() > 0);
		if (expectedID > 0) {
			boolean found = false;
			for (Classes s2 : list) {
				if (s2.getId().equals(expectedID))
					found = true;
				log.info(s2.toString());
			}

			assertTrue("Looking for record ID " + expectedID + " in results", found);
		}
		return list;
	}

	private Classes getMidRecord(ClassesSearchForm form, Integer expectedID) {
		Page<Classes> list = confirmGotResult(form, expectedID);
		assertNotNull("Checking return not null", list);
		int size = list.toList().size();
		assertTrue("Checking at least 1 return", size > 0);
		int record = 0;
		if (size > 2)
			record = size / 2;
		return list.toList().get(record);


	}

	@Test
	public void testActive() {
		// active Integer 4
		Classes rec = null;
		ClassesSearchForm form = new ClassesSearchForm();
		rec = getMidRecord(form, 0);
		form.setActiveMin(Integer.MIN_VALUE);
		rec = getMidRecord(form, 0);
		log.info("Searching for records with active of " + rec.getActive());

		form = new ClassesSearchForm();
		form.setActiveMin(rec.getActive());
		form.setActiveMax(rec.getActive() + 1);
		confirmGotResult(form, rec.getId());

		form = new ClassesSearchForm();
		form.setActiveMin(rec.getActive() - 1);
		form.setActiveMax(rec.getActive());
		confirmGotResult(form, rec.getId());

		form = new ClassesSearchForm();
		form.setActiveMin(rec.getActive());
		confirmGotResult(form, rec.getId());

		form = new ClassesSearchForm();
		form.setActiveMax(rec.getActive());
		confirmGotResult(form, rec.getId());

		form = new ClassesSearchForm();
		form.setActiveMin(rec.getActive());
		form.setActiveMax(rec.getActive());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testCatagory() {
		// catagory String 12
		Classes rec = null;
		ClassesSearchForm form = new ClassesSearchForm();
		rec = getMidRecord(form, 0);
		form.setCatagory("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with catagory of " + rec.getCatagory());

		form = new ClassesSearchForm();
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
		Classes rec = null;
		ClassesSearchForm form = new ClassesSearchForm();
		rec = getMidRecord(form, 0);
		form.setClassname("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with classname of " + rec.getClassname());

		form = new ClassesSearchForm();
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
	public void testGrp() {
		// grp String 12
		Classes rec = null;
		ClassesSearchForm form = new ClassesSearchForm();
		rec = getMidRecord(form, 0);
		form.setGrp("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with grp of " + rec.getGrp());

		form = new ClassesSearchForm();
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
	public void testReplacewith() {
		// replacewith String 12
		Classes rec = null;
		ClassesSearchForm form = new ClassesSearchForm();
		rec = getMidRecord(form, 0);
		form.setReplacewith("%");
		rec = getMidRecord(form, 0);
		log.info("Searching for records with replacewith of " + rec.getReplacewith());

		form = new ClassesSearchForm();
		String text = rec.getReplacewith();
		if (text.length() < 2) {
			form.setReplacewith(text + "%");
			confirmGotResult(form, rec.getId());

			form.setReplacewith("%" + text);
			confirmGotResult(form, rec.getId());
			form.setReplacewith("%" + text + "%");
			confirmGotResult(form, rec.getId());
		} else {
			int mid = text.length() / 2;
			form.setReplacewith(text.substring(0, mid) + "%");
			confirmGotResult(form, rec.getId());

			form.setReplacewith("%" + text.substring(mid - 1, mid) + "%");
			confirmGotResult(form, rec.getId());
			form.setReplacewith("%" + text.substring(mid, text.length()));
			confirmGotResult(form, rec.getId());
		}
	}
}
