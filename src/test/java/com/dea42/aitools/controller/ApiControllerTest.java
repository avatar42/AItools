package com.dea42.aitools.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dea42.aitools.MockBase;
import com.dea42.aitools.entity.Account;
import com.dea42.aitools.entity.Pics;
import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.entity.Classes;

/**
 * Title: ApiControllerTest <br>
 * Description: REST Api Controller Test. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(ApiController.class)
public class ApiControllerTest extends MockBase {


	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.AccountController#getAllAccounts(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllAccounts() throws Exception {
		List<Account> list = new ArrayList<>();
		Account o = new Account();
        o.setEmail(getTestEmailString(254));
		o.setId(1);
        o.setName(getTestString(254));
        o.setPassword(getTestPasswordString(30));
        o.setUserrole(getTestString(25));
		list.add(o);
		Page<Account> p = getPage(list);
		given(accountServices.listAll(null)).willReturn(p);

		this.mockMvc.perform(get("/api/accounts").with(user("user").roles("ADMIN"))).andExpect(status().isOk())
				.andExpect(content().string(containsString(o.getEmail())))
				.andExpect(content().string(containsString("email")))				.andExpect(content().string(containsString("id")))				.andExpect(content().string(containsString(o.getName())))
				.andExpect(content().string(containsString("name")))				.andExpect(content().string(containsString(o.getUserrole())))
				.andExpect(content().string(containsString("userrole")));
	}


	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.PicsController#getAllPicss(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllPicss() throws Exception {
		List<Pics> list = new ArrayList<>();
		Pics o = new Pics();
        o.setCatagory(getTestString(6));
        o.setClassname(getTestString(12));
        o.setFilename(getTestString(55));
        o.setGrp(getTestString(7));
		o.setId(1);
        o.setPath(getTestString(26));
		list.add(o);
		Page<Pics> p = getPage(list);
		given(picsServices.listAll(null)).willReturn(p);

		this.mockMvc.perform(get("/api/picss").with(user("user").roles("ADMIN"))).andExpect(status().isOk())
				.andExpect(content().string(containsString(o.getCatagory())))
				.andExpect(content().string(containsString("catagory")))				.andExpect(content().string(containsString(o.getClassname())))
				.andExpect(content().string(containsString("classname")))				.andExpect(content().string(containsString(o.getFilename())))
				.andExpect(content().string(containsString("filename")))				.andExpect(content().string(containsString(o.getGrp())))
				.andExpect(content().string(containsString("grp")))				.andExpect(content().string(containsString("id")))				.andExpect(content().string(containsString("night")))				.andExpect(content().string(containsString(o.getPath())))
				.andExpect(content().string(containsString("path")));
	}


	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#getAllDetectionss(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllDetectionss() throws Exception {
		List<Detections> list = new ArrayList<>();
		Detections o = new Detections();
		o.setId(1);
		list.add(o);
		Page<Detections> p = getPage(list);
		given(detectionsServices.listAll(null)).willReturn(p);

		this.mockMvc.perform(get("/api/detectionss").with(user("user").roles("ADMIN"))).andExpect(status().isOk())
				.andExpect(content().string(containsString("classid")))				.andExpect(content().string(containsString("confidence")))				.andExpect(content().string(containsString("id")))				.andExpect(content().string(containsString("picid")))				.andExpect(content().string(containsString("xmax")))				.andExpect(content().string(containsString("xmin")))				.andExpect(content().string(containsString("ymax")))				.andExpect(content().string(containsString("ymin")));
	}


	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.ClassesController#getAllClassess(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllClassess() throws Exception {
		List<Classes> list = new ArrayList<>();
		Classes o = new Classes();
        o.setCatagory(getTestString(7));
        o.setClassname(getTestString(16));
        o.setGrp(getTestString(12));
		o.setId(1);
        o.setReplacewith(getTestString(8));
		list.add(o);
		Page<Classes> p = getPage(list);
		given(classesServices.listAll(null)).willReturn(p);

		this.mockMvc.perform(get("/api/classess").with(user("user").roles("ADMIN"))).andExpect(status().isOk())
				.andExpect(content().string(containsString("active")))				.andExpect(content().string(containsString(o.getCatagory())))
				.andExpect(content().string(containsString("catagory")))				.andExpect(content().string(containsString(o.getClassname())))
				.andExpect(content().string(containsString("classname")))				.andExpect(content().string(containsString(o.getGrp())))
				.andExpect(content().string(containsString("grp")))				.andExpect(content().string(containsString("id")))				.andExpect(content().string(containsString(o.getReplacewith())))
				.andExpect(content().string(containsString("replacewith")));
	}

}
