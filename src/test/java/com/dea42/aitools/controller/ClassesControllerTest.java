package com.dea42.aitools.controller;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import com.dea42.aitools.MockBase;
import com.dea42.aitools.entity.Classes;
import com.dea42.aitools.form.ClassesForm;
import com.dea42.aitools.search.ClassesSearchForm;
import com.google.common.collect.ImmutableMap;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: ClassesControllerTest <br>
 * Description: ClassesController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@WebMvcTest(ClassesController.class)
public class ClassesControllerTest extends MockBase {
	private Classes getClasses(Integer id) {
		Classes o = new Classes();
		o.setId(id);
        o.setCatagory(getTestString(7));
        o.setClassname(getTestString(16));
        o.setGrp(getTestString(12));
        o.setReplacewith(getTestString(8));
		return o;
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#getAllClassess(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllClassess() throws Exception {
		List<Classes> list = new ArrayList<>();
		Classes o = getClasses(1);
		list.add(o);

		Page<Classes> p = getPage(list);
		given(classesServices.listAll(new ClassesSearchForm())).willReturn(p);

		ResultActions ra = getAsAdmin("/classess");
		contentContainsMarkup(ra,"<h1>" + getMsg("class.Classes") + " " + getMsg("edit.list") + "</h1>");
//		contentContainsMarkup(ra,getMsg("Classes.active"));
//		contentContainsMarkup(ra,getTestString(7));
//		contentContainsMarkup(ra,getMsg("Classes.catagory"));
//		contentContainsMarkup(ra,getTestString(16));
//		contentContainsMarkup(ra,getMsg("Classes.classname"));
//		contentContainsMarkup(ra,getTestString(12));
//		contentContainsMarkup(ra,getMsg("Classes.grp"));
//		contentContainsMarkup(ra,getTestString(8));
//		contentContainsMarkup(ra,getMsg("Classes.replacewith"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#showNewClassesPage(org.springframework.ui.Model)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowNewClassesPage() throws Exception {
		ResultActions ra = getAsAdmin("/classess/new");
		contentContainsMarkup(ra,"<legend>" + getMsg("edit.new") + " " + getMsg("class.Classes") + "</legend>");
		contentContainsMarkup(ra,getMsg("Classes.active"));
		contentContainsMarkup(ra,getMsg("Classes.catagory"));
		contentContainsMarkup(ra,getMsg("Classes.classname"));
		contentContainsMarkup(ra,getMsg("Classes.grp"));
		// TODO: confirm ignoring Classes.id
		contentContainsMarkup(ra,getMsg("Classes.replacewith"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#saveClasses(com.dea42.aitools.entity.Classes, java.lang.String)}.
	 */
	@Test
	public void testSaveClassesCancel() throws Exception {
		Classes o = getClasses(1);

		send(SEND_POST, "/classess/save", "classes", o, ImmutableMap.of("action", "cancel"), ADMIN_EMAIL,
				"/classess");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#saveClasses(com.dea42.aitools.entity.Classes, java.lang.String)}.
	 */
	@Test
	public void testSaveClassesSave() throws Exception {
		Classes o = getClasses(0);
		ClassesForm form = ClassesForm.getInstance(o);
		log.debug(form.toString());

		send(SEND_POST, "/classess/save", "classesForm", form, ImmutableMap.of("action", "save"), ADMIN_EMAIL,
				"/classess");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#showEditClassesPage(java.lang.Integer)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowEditClassesPage() throws Exception {
		Classes o = getClasses(1);

		given(classesServices.get(1)).willReturn(o);

		ResultActions ra = getAsAdmin("/classess/edit/1");
		contentContainsMarkup(ra,"Active");
		contentContainsMarkup(ra,o.getCatagory());
		contentContainsMarkup(ra,"Catagory");
		contentContainsMarkup(ra,o.getClassname());
		contentContainsMarkup(ra,"Classname");
		contentContainsMarkup(ra,o.getGrp());
		contentContainsMarkup(ra,"Grp");
		// TODO: confirm ignoring Classes.id
		contentContainsMarkup(ra,o.getReplacewith());
		contentContainsMarkup(ra,"Replacewith");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#deleteClasses(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteClasses() throws Exception {
		getAsAdminRedirectExpected("/classess/delete/1","/classess");
	}

}

