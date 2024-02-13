package com.dea42.aitools.controller;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import com.dea42.aitools.MockBase;
import com.dea42.aitools.entity.Pics;
import com.dea42.aitools.form.PicsForm;
import com.dea42.aitools.search.PicsSearchForm;
import com.google.common.collect.ImmutableMap;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: PicsControllerTest <br>
 * Description: PicsController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@WebMvcTest(PicsController.class)
public class PicsControllerTest extends MockBase {
	private Pics getPics(Integer id) {
		Pics o = new Pics();
		o.setId(id);
        o.setCatagory(getTestString(6));
        o.setClassname(getTestString(12));
        o.setFilename(getTestString(55));
        o.setGrp(getTestString(7));
        o.setPath(getTestString(26));
		return o;
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#getAllPicss(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllPicss() throws Exception {
		List<Pics> list = new ArrayList<>();
		Pics o = getPics(1);
		list.add(o);

		Page<Pics> p = getPage(list);
		given(picsServices.listAll(new PicsSearchForm())).willReturn(p);

		ResultActions ra = getAsAdmin("/picss");
		contentContainsMarkup(ra,"<h1>" + getMsg("class.Pics") + " " + getMsg("edit.list") + "</h1>");
//		contentContainsMarkup(ra,getTestString(6));
//		contentContainsMarkup(ra,getMsg("Pics.catagory"));
//		contentContainsMarkup(ra,getTestString(12));
//		contentContainsMarkup(ra,getMsg("Pics.classname"));
//		contentContainsMarkup(ra,getTestString(55));
//		contentContainsMarkup(ra,getMsg("Pics.filename"));
//		contentContainsMarkup(ra,getTestString(7));
//		contentContainsMarkup(ra,getMsg("Pics.grp"));
//		contentContainsMarkup(ra,getMsg("Pics.night"));
//		contentContainsMarkup(ra,getTestString(26));
//		contentContainsMarkup(ra,getMsg("Pics.path"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#showNewPicsPage(org.springframework.ui.Model)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowNewPicsPage() throws Exception {
		ResultActions ra = getAsAdmin("/picss/new");
		contentContainsMarkup(ra,"<legend>" + getMsg("edit.new") + " " + getMsg("class.Pics") + "</legend>");
		contentContainsMarkup(ra,getMsg("Pics.catagory"));
		contentContainsMarkup(ra,getMsg("Pics.classname"));
		contentContainsMarkup(ra,getMsg("Pics.filename"));
		contentContainsMarkup(ra,getMsg("Pics.grp"));
		// TODO: confirm ignoring Pics.id
		contentContainsMarkup(ra,getMsg("Pics.night"));
		contentContainsMarkup(ra,getMsg("Pics.path"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#savePics(com.dea42.aitools.entity.Pics, java.lang.String)}.
	 */
	@Test
	public void testSavePicsCancel() throws Exception {
		Pics o = getPics(1);

		send(SEND_POST, "/picss/save", "pics", o, ImmutableMap.of("action", "cancel"), ADMIN_EMAIL,
				"/picss");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#savePics(com.dea42.aitools.entity.Pics, java.lang.String)}.
	 */
	@Test
	public void testSavePicsSave() throws Exception {
		Pics o = getPics(0);
		PicsForm form = PicsForm.getInstance(o);
		log.debug(form.toString());

		send(SEND_POST, "/picss/save", "picsForm", form, ImmutableMap.of("action", "save"), ADMIN_EMAIL,
				"/picss");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#showEditPicsPage(java.lang.Integer)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowEditPicsPage() throws Exception {
		Pics o = getPics(1);

		given(picsServices.get(1)).willReturn(o);

		ResultActions ra = getAsAdmin("/picss/edit/1");
		contentContainsMarkup(ra,o.getCatagory());
		contentContainsMarkup(ra,"Catagory");
		contentContainsMarkup(ra,o.getClassname());
		contentContainsMarkup(ra,"Classname");
		contentContainsMarkup(ra,o.getFilename());
		contentContainsMarkup(ra,"Filename");
		contentContainsMarkup(ra,o.getGrp());
		contentContainsMarkup(ra,"Grp");
		// TODO: confirm ignoring Pics.id
		contentContainsMarkup(ra,"Night");
		contentContainsMarkup(ra,o.getPath());
		contentContainsMarkup(ra,"Path");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#deletePics(java.lang.Integer)}.
	 */
	@Test
	public void testDeletePics() throws Exception {
		getAsAdminRedirectExpected("/picss/delete/1","/picss");
	}

}

