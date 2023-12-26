package com.dea42.aitools.controller;

import java.util.Date;
import java.security.Principal;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dea42.aitools.entity.Classes;
import com.dea42.aitools.form.ClassesForm;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.search.ClassesSearchForm;
import com.dea42.aitools.service.ClassesServices;
import com.dea42.aitools.utils.Message;
import com.dea42.aitools.utils.MessageHelper;
import com.dea42.aitools.utils.Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * Title: ClassesController <br>
 * Description: ClassesController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Controller
@RequestMapping("/classess")
public class ClassesController {

	@Autowired
	private ClassesServices classesService;

	private ClassesSearchForm getForm(HttpServletRequest request) {
		ClassesSearchForm form = (ClassesSearchForm) request.getSession().getAttribute("classesSearchForm");
		if (log.isDebugEnabled())
			log.debug("pulled from session:" + form);
		if (form == null) {
			form = new ClassesSearchForm();
		}
		return form;
	}

	private void setForm(HttpServletRequest request, ClassesSearchForm form) {
		request.getSession().setAttribute("classesSearchForm", form);
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
	public ModelAndView search(HttpServletRequest request, @ModelAttribute ClassesSearchForm form, 
			RedirectAttributes ra, @RequestParam(value = "action", required = true) String action) {
		ModelAndView mav;
		if (action.equals("search")) {
			setForm(request, form);
			form.setAdvanced(true);
			mav = new ModelAndView("classess");
//			mav = findPaginated(request, 1, "id", "asc");
//			@SuppressWarnings("unchecked")
//			List<Classes> list = (List<Classes>) mav.getModelMap().getAttribute("classess");
//			if (list == null || list.isEmpty()) {
//				mav.setViewName("search_classes");
//				mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
//						new Message("search.noResult", Message.Type.WARNING));
//			}
		} else {
			form = new ClassesSearchForm();
			setForm(request, form);
			mav = new ModelAndView("search_classes");
			mav.addObject("classesSearchForm", form);
			mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
					new Message("search.formReset", Message.Type.WARNING));
		}

		return mav;
	}

	@GetMapping("/search/{pageNo}")
	public ModelAndView findPaginated(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		ClassesSearchForm form = getForm(request);
		if (pageNo < 1)
			pageNo = 1;

		form.setPage(pageNo);
		form.setSortField(sortField);
		form.setSortAsc("asc".equalsIgnoreCase(sortDir));

		if (log.isDebugEnabled())
			log.debug("Searching with:" + form);

		Page<Classes> page = classesService.listAll(form);

		form.setTotalPages(page.getTotalPages());
		form.setTotalItems(page.getTotalElements());
		setForm(request, form);

		ModelAndView mav = new ModelAndView("classess");
		mav.addObject("classess", page.getContent());
		return mav;
	}

	@GetMapping("/search")
	public String showSearchPage(HttpServletRequest request, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(getForm(request));
		if (Utils.isAjaxRequest(requestedWith)) {
			return "search_classes".concat(" :: classesSearchForm");
		}

		return "search_classes";
	}

	@PostMapping(value = "/save")
	public String save(@Valid @ModelAttribute ClassesForm form, Errors errors, RedirectAttributes ra,
			@RequestParam(value = "action", required = true) String action) {
		if (action.equals("save")) {
			if (errors.hasErrors()) {
				return "edit_classes";
			}

			Classes classes = new Classes();
			classes.setActive(form.getActive());
			classes.setCatagory(form.getCatagory());
			classes.setClassname(form.getClassname());
			classes.setGrp(form.getGrp());
			classes.setId(form.getId());
			classes.setReplacewith(form.getReplacewith());
			try {
				classes = classesService.save(classes);
			} catch (Exception e) {
				log.error("Failed saving:" + form, e);
			}

			if (classes == null) {
				MessageHelper.addErrorAttribute(ra, "db.failed");
			} else {
				MessageHelper.addSuccessAttribute(ra, "save.success");
			}
		} else {
			MessageHelper.addSuccessAttribute(ra, "save.cancelled");
		}

		return "redirect:/classess";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditPage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_classes");
		Classes classes = null;
		if (id > 0)
			classes = classesService.get(id);
		mav.addObject("classesForm", ClassesForm.getInstance(classes));

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id) {
		classesService.delete(id);
		return "redirect:/classess";
	}

	@GetMapping("/list")
	String home(Principal principal) {
		return "classess";
	}
}
