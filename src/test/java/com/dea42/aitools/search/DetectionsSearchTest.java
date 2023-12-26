package com.dea42.aitools.search;


import com.dea42.aitools.UnitBase;
import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.search.DetectionsSearchForm;
import com.dea42.aitools.service.DetectionsServices;
import java.math.BigDecimal;
import javax.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Title: detectionsSearch Test <br>
 * Description: Does regression tests of detections search from service to DB <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DetectionsSearchTest extends UnitBase {

	@Autowired
	private DetectionsServices detectionsServices;

	private Page<Detections> confirmGotResult(DetectionsSearchForm form, Integer expectedID) {
		log.info("form:"+form);
		Page<Detections> list = detectionsServices.listAll(form);
		assertNotNull("Checking return not null", list);
		assertTrue("Checking at least 1 return", list.toList().size() > 0);
		if (expectedID > 0) {
			boolean found = false;
			for (Detections s2 : list) {
				if (s2.getId().equals(expectedID))
					found = true;
				log.info(s2.toString());
			}

			assertTrue("Looking for record ID " + expectedID + " in results", found);
		}
		return list;
	}

	private Detections getMidRecord(DetectionsSearchForm form, Integer expectedID) {
		Page<Detections> list = confirmGotResult(form, expectedID);
		assertNotNull("Checking return not null", list);
		int size = list.toList().size();
		assertTrue("Checking at least 1 return", size > 0);
		int record = 0;
		if (size > 2)
			record = size / 2;
		return list.toList().get(record);


	}

	@Test
	public void testClassid() {
		// classid Integer 4
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setClassidMin(Integer.MIN_VALUE);
		rec = getMidRecord(form, 0);
		log.info("Searching for records with classid of " + rec.getClassid());

		form = new DetectionsSearchForm();
		form.setClassidMin(rec.getClassid());
		form.setClassidMax(rec.getClassid() + 1);
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setClassidMin(rec.getClassid() - 1);
		form.setClassidMax(rec.getClassid());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setClassidMin(rec.getClassid());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setClassidMax(rec.getClassid());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setClassidMin(rec.getClassid());
		form.setClassidMax(rec.getClassid());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testConfidence() {
		// confidence BigDecimal 6
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setConfidenceMin(new BigDecimal(Integer.MIN_VALUE));
		rec = getMidRecord(form, 0);
		log.info("Searching for records with confidence of " + rec.getConfidence());

		form = new DetectionsSearchForm();
		form.setConfidenceMin(rec.getConfidence());
		form.setConfidenceMax(rec.getConfidence().add(new BigDecimal(100)));
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setConfidenceMin(rec.getConfidence().subtract(new BigDecimal(100)));
		form.setConfidenceMax(rec.getConfidence());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setConfidenceMin(rec.getConfidence());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setConfidenceMax(rec.getConfidence());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setConfidenceMin(rec.getConfidence());
		form.setConfidenceMax(rec.getConfidence());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testPicid() {
		// picid Integer 4
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setPicidMin(Integer.MIN_VALUE);
		rec = getMidRecord(form, 0);
		log.info("Searching for records with picid of " + rec.getPicid());

		form = new DetectionsSearchForm();
		form.setPicidMin(rec.getPicid());
		form.setPicidMax(rec.getPicid() + 1);
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setPicidMin(rec.getPicid() - 1);
		form.setPicidMax(rec.getPicid());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setPicidMin(rec.getPicid());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setPicidMax(rec.getPicid());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setPicidMin(rec.getPicid());
		form.setPicidMax(rec.getPicid());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testXmax() {
		// xmax BigDecimal 6
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setXmaxMin(new BigDecimal(Integer.MIN_VALUE));
		rec = getMidRecord(form, 0);
		log.info("Searching for records with xmax of " + rec.getXmax());

		form = new DetectionsSearchForm();
		form.setXmaxMin(rec.getXmax());
		form.setXmaxMax(rec.getXmax().add(new BigDecimal(100)));
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXmaxMin(rec.getXmax().subtract(new BigDecimal(100)));
		form.setXmaxMax(rec.getXmax());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXmaxMin(rec.getXmax());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXmaxMax(rec.getXmax());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXmaxMin(rec.getXmax());
		form.setXmaxMax(rec.getXmax());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testXmin() {
		// xmin BigDecimal 6
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setXminMin(new BigDecimal(Integer.MIN_VALUE));
		rec = getMidRecord(form, 0);
		log.info("Searching for records with xmin of " + rec.getXmin());

		form = new DetectionsSearchForm();
		form.setXminMin(rec.getXmin());
		form.setXminMax(rec.getXmin().add(new BigDecimal(100)));
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXminMin(rec.getXmin().subtract(new BigDecimal(100)));
		form.setXminMax(rec.getXmin());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXminMin(rec.getXmin());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXminMax(rec.getXmin());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setXminMin(rec.getXmin());
		form.setXminMax(rec.getXmin());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testYmax() {
		// ymax BigDecimal 6
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setYmaxMin(new BigDecimal(Integer.MIN_VALUE));
		rec = getMidRecord(form, 0);
		log.info("Searching for records with ymax of " + rec.getYmax());

		form = new DetectionsSearchForm();
		form.setYmaxMin(rec.getYmax());
		form.setYmaxMax(rec.getYmax().add(new BigDecimal(100)));
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYmaxMin(rec.getYmax().subtract(new BigDecimal(100)));
		form.setYmaxMax(rec.getYmax());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYmaxMin(rec.getYmax());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYmaxMax(rec.getYmax());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYmaxMin(rec.getYmax());
		form.setYmaxMax(rec.getYmax());
		confirmGotResult(form, rec.getId());
	}

	@Test
	public void testYmin() {
		// ymin BigDecimal 6
		Detections rec = null;
		DetectionsSearchForm form = new DetectionsSearchForm();
		rec = getMidRecord(form, 0);
		form.setYminMin(new BigDecimal(Integer.MIN_VALUE));
		rec = getMidRecord(form, 0);
		log.info("Searching for records with ymin of " + rec.getYmin());

		form = new DetectionsSearchForm();
		form.setYminMin(rec.getYmin());
		form.setYminMax(rec.getYmin().add(new BigDecimal(100)));
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYminMin(rec.getYmin().subtract(new BigDecimal(100)));
		form.setYminMax(rec.getYmin());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYminMin(rec.getYmin());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYminMax(rec.getYmin());
		confirmGotResult(form, rec.getId());

		form = new DetectionsSearchForm();
		form.setYminMin(rec.getYmin());
		form.setYminMax(rec.getYmin());
		confirmGotResult(form, rec.getId());
	}
}
