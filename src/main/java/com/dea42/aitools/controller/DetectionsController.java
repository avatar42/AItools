package com.dea42.aitools.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.form.DetectionsForm;
import com.dea42.aitools.search.DetectionsSearchForm;
import com.dea42.aitools.service.DetectionsServices;
import com.dea42.aitools.utils.Message;
import com.dea42.aitools.utils.MessageHelper;
import com.dea42.aitools.utils.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: DetectionsController <br>
 * Description: DetectionsController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Controller
@RequestMapping("/detectionss")
public class DetectionsController {

	@Autowired
	private DetectionsServices detectionsService;

	private DetectionsSearchForm getForm(HttpServletRequest request) {
		DetectionsSearchForm form = (DetectionsSearchForm) request.getSession().getAttribute("detectionsSearchForm");
		if (log.isDebugEnabled())
			log.debug("pulled from session:" + form);
		if (form == null) {
			form = new DetectionsSearchForm();
		}
		return form;
	}

	private void setForm(HttpServletRequest request, DetectionsSearchForm form) {
		request.getSession().setAttribute("detectionsSearchForm", form);
		if (log.isDebugEnabled())
			log.debug("stored:" + form);
	}


	@GetMapping
	public ModelAndView getAll(HttpServletRequest request) {
		return findPaginated(request, 1, "id", "asc");
	}

	@GetMapping("/new")
	public ModelAndView showNewPage() {
		return showEditPage(0);
	}

	@PostMapping(value = "/search")
	public ModelAndView search(HttpServletRequest request, @ModelAttribute DetectionsSearchForm form, 
			RedirectAttributes ra, @RequestParam(value = "action", required = true) String action) {
		ModelAndView mav;
		if (action.equals("search")) {
			setForm(request, form);
			form.setAdvanced(true);
			mav = new ModelAndView("detectionss");
//			mav = findPaginated(request, 1, "id", "asc");
//			@SuppressWarnings("unchecked")
//			List<Detections> list = (List<Detections>) mav.getModelMap().getAttribute("detectionss");
//			if (list == null || list.isEmpty()) {
//				mav.setViewName("search_detections");
//				mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
//						new Message("search.noResult", Message.Type.WARNING));
//			}
		} else {
			form = new DetectionsSearchForm();
			setForm(request, form);
			mav = new ModelAndView("search_detections");
			mav.addObject("detectionsSearchForm", form);
			mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
					new Message("search.formReset", Message.Type.WARNING));
		}

		return mav;
	}

	@GetMapping("/search/{pageNo}")
	public ModelAndView findPaginated(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		DetectionsSearchForm form = getForm(request);
		if (pageNo < 1)
			pageNo = 1;

		form.setPage(pageNo);
		form.setSortField(sortField);
		form.setSortAsc("asc".equalsIgnoreCase(sortDir));

		if (log.isDebugEnabled())
			log.debug("Searching with:" + form);

		Page<Detections> page = detectionsService.listAll(form);

		form.setTotalPages(page.getTotalPages());
		form.setTotalItems(page.getTotalElements());
		setForm(request, form);

		ModelAndView mav = new ModelAndView("detectionss");
		mav.addObject("detectionss", page.getContent());
		return mav;
	}

	@GetMapping("/search")
	public String showSearchPage(HttpServletRequest request, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(getForm(request));
		if (Utils.isAjaxRequest(requestedWith)) {
			return "search_detections".concat(" :: detectionsSearchForm");
		}

		return "search_detections";
	}

	@PostMapping(value = "/save")
	public String save(@Valid @ModelAttribute DetectionsForm form, Errors errors, RedirectAttributes ra,
			@RequestParam(value = "action", required = true) String action) {
		if (action.equals("save")) {
			if (errors.hasErrors()) {
				return "edit_detections";
			}

			Detections detections = new Detections();
			detections.setClassid(form.getClassid());
			detections.setConfidence(form.getConfidence());
			detections.setId(form.getId());
			detections.setPicid(form.getPicid());
			detections.setXmax(form.getXmax());
			detections.setXmin(form.getXmin());
			detections.setYmax(form.getYmax());
			detections.setYmin(form.getYmin());
			try {
				detections = detectionsService.save(detections);
			} catch (Exception e) {
				log.error("Failed saving:" + form, e);
			}

			if (detections == null) {
				MessageHelper.addErrorAttribute(ra, "db.failed");
			} else {
				MessageHelper.addSuccessAttribute(ra, "save.success");
			}
		} else {
			MessageHelper.addSuccessAttribute(ra, "save.cancelled");
		}

		return "redirect:/detectionss";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditPage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_detections");
		Detections detections = null;
		if (id > 0)
			detections = detectionsService.get(id);
		mav.addObject("detectionsForm", DetectionsForm.getInstance(detections));

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id) {
		detectionsService.delete(id);
		return "redirect:/detectionss";
	}

	@GetMapping("/list")
	String home(Principal principal) {
		return "detectionss";
	}
}
