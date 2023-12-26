package com.dea42.aitools.controller;
import static org.mockito.BDDMockito.given;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;
import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import com.dea42.aitools.MockBase;
import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.form.DetectionsForm;
import com.dea42.aitools.search.DetectionsSearchForm;

/**
 * Title: DetectionsControllerTest <br>
 * Description: DetectionsController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@WebMvcTest(DetectionsController.class)
public class DetectionsControllerTest extends MockBase {
	private Detections getDetections(Integer id) {
		Detections o = new Detections();
		o.setId(id);
		return o;
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#getAllDetectionss(org.springframework.ui.Model)}.
	 */
	@Test
	public void testGetAllDetectionss() throws Exception {
		List<Detections> list = new ArrayList<>();
		Detections o = getDetections(1);
		list.add(o);

		Page<Detections> p = getPage(list);
		given(detectionsServices.listAll(new DetectionsSearchForm())).willReturn(p);

		ResultActions ra = getAsAdmin("/detectionss");
		contentContainsMarkup(ra,"<h1>" + getMsg("class.Detections") + " " + getMsg("edit.list") + "</h1>");
//		contentContainsMarkup(ra,getMsg("Detections.classid"));
//		contentContainsMarkup(ra,getMsg("Detections.confidence"));
//		contentContainsMarkup(ra,getMsg("Detections.picid"));
//		contentContainsMarkup(ra,getMsg("Detections.xmax"));
//		contentContainsMarkup(ra,getMsg("Detections.xmin"));
//		contentContainsMarkup(ra,getMsg("Detections.ymax"));
//		contentContainsMarkup(ra,getMsg("Detections.ymin"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#showNewDetectionsPage(org.springframework.ui.Model)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowNewDetectionsPage() throws Exception {
		ResultActions ra = getAsAdmin("/detectionss/new");
		contentContainsMarkup(ra,"<legend>" + getMsg("edit.new") + " " + getMsg("class.Detections") + "</legend>");
		contentContainsMarkup(ra,getMsg("Detections.classid"));
		contentContainsMarkup(ra,getMsg("Detections.confidence"));
		// TODO: confirm ignoring Detections.id
		contentContainsMarkup(ra,getMsg("Detections.picid"));
		contentContainsMarkup(ra,getMsg("Detections.xmax"));
		contentContainsMarkup(ra,getMsg("Detections.xmin"));
		contentContainsMarkup(ra,getMsg("Detections.ymax"));
		contentContainsMarkup(ra,getMsg("Detections.ymin"));
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#saveDetections(com.dea42.aitools.entity.Detections, java.lang.String)}.
	 */
	@Test
	public void testSaveDetectionsCancel() throws Exception {
		Detections o = getDetections(1);

		send(SEND_POST, "/detectionss/save", "detections", o, ImmutableMap.of("action", "cancel"), ADMIN_EMAIL,
				"/detectionss");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#saveDetections(com.dea42.aitools.entity.Detections, java.lang.String)}.
	 */
	@Test
	public void testSaveDetectionsSave() throws Exception {
		Detections o = getDetections(0);
		DetectionsForm form = DetectionsForm.getInstance(o);
		log.debug(form.toString());

		send(SEND_POST, "/detectionss/save", "detectionsForm", form, ImmutableMap.of("action", "save"), ADMIN_EMAIL,
				"/detectionss");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#showEditDetectionsPage(java.lang.Integer)}.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testShowEditDetectionsPage() throws Exception {
		Detections o = getDetections(1);

		given(detectionsServices.get(1)).willReturn(o);

		ResultActions ra = getAsAdmin("/detectionss/edit/1");
		contentContainsMarkup(ra,"Classid");
		contentContainsMarkup(ra,"Confidence");
		// TODO: confirm ignoring Detections.id
		contentContainsMarkup(ra,"Picid");
		contentContainsMarkup(ra,"Xmax");
		contentContainsMarkup(ra,"Xmin");
		contentContainsMarkup(ra,"Ymax");
		contentContainsMarkup(ra,"Ymin");
	}

	/**
	 * Test method for
	 * {@link com.dea42.aitools.controller.DetectionsController#deleteDetections(java.lang.Integer)}.
	 */
	@Test
	public void testDeleteDetections() throws Exception {
		getAsAdminRedirectExpected("/detectionss/delete/1","/detectionss");
	}

}

