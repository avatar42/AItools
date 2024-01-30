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

import com.dea42.aitools.entity.Pics;
import com.dea42.aitools.form.PicsForm;
import com.dea42.aitools.search.PicsSearchForm;
import com.dea42.aitools.service.PicsServices;
import com.dea42.aitools.utils.Message;
import com.dea42.aitools.utils.MessageHelper;
import com.dea42.aitools.utils.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: PicsController <br>
 * Description: PicsController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Controller
@RequestMapping("/picss")
public class PicsController {

	@Autowired
	private PicsServices picsService;

	private PicsSearchForm getForm(HttpServletRequest request) {
		PicsSearchForm form = (PicsSearchForm) request.getSession().getAttribute("picsSearchForm");
		if (log.isDebugEnabled())
			log.debug("pulled from session:" + form);
		if (form == null) {
			form = new PicsSearchForm();
		}
		return form;
	}

	private void setForm(HttpServletRequest request, PicsSearchForm form) {
		request.getSession().setAttribute("picsSearchForm", form);
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
	public ModelAndView search(HttpServletRequest request, @ModelAttribute PicsSearchForm form, RedirectAttributes ra,
			@RequestParam(value = "action", required = true) String action) {
		ModelAndView mav;
		if (action.equals("search")) {
			setForm(request, form);
			form.setAdvanced(true);
			mav = new ModelAndView("picss");
//			mav = findPaginated(request, 1, "id", "asc");
//			@SuppressWarnings("unchecked")
//			List<Pics> list = (List<Pics>) mav.getModelMap().getAttribute("picss");
//			if (list == null || list.isEmpty()) {
//				mav.setViewName("search_pics");
//				mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
//						new Message("search.noResult", Message.Type.WARNING));
//			}
		} else {
			form = new PicsSearchForm();
			setForm(request, form);
			mav = new ModelAndView("search_pics");
			mav.addObject("picsSearchForm", form);
			mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
					new Message("search.formReset", Message.Type.WARNING));
		}

		return mav;
	}

	@GetMapping("/search/{pageNo}")
	public ModelAndView findPaginated(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		PicsSearchForm form = getForm(request);
		if (pageNo < 1)
			pageNo = 1;

		form.setPage(pageNo);
		form.setSortField(sortField);
		form.setSortAsc("asc".equalsIgnoreCase(sortDir));

		if (log.isDebugEnabled())
			log.debug("Searching with:" + form);

		Page<Pics> page = picsService.listAll(form);

		form.setTotalPages(page.getTotalPages());
		form.setTotalItems(page.getTotalElements());
		setForm(request, form);

		ModelAndView mav = new ModelAndView("picss");
		mav.addObject("picss", page.getContent());
		return mav;
	}

	@GetMapping("/search")
	public String showSearchPage(HttpServletRequest request, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(getForm(request));
		if (Utils.isAjaxRequest(requestedWith)) {
			return "search_pics".concat(" :: picsSearchForm");
		}

		return "search_pics";
	}

	@PostMapping(value = "/save")
	public String save(@Valid @ModelAttribute PicsForm form, Errors errors, RedirectAttributes ra,
			@RequestParam(value = "action", required = true) String action) {
		if (action.equals("save")) {
			if (errors.hasErrors()) {
				return "edit_pics";
			}

			Pics pics = new Pics();
			pics.setCatagory(form.getCatagory());
			pics.setClassname(form.getClassname());
			pics.setFilename(form.getFilename());
			pics.setGrp(form.getGrp());
			pics.setId(form.getId());
			pics.setNight(form.getNight());
			pics.setPath(form.getPath());
			try {
				pics = picsService.save(pics);
			} catch (Exception e) {
				log.error("Failed saving:" + form, e);
			}

			if (pics == null) {
				MessageHelper.addErrorAttribute(ra, "db.failed");
			} else {
				MessageHelper.addSuccessAttribute(ra, "save.success");
			}
		} else {
			MessageHelper.addSuccessAttribute(ra, "save.cancelled");
		}

		return "redirect:/picss";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditPage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_pics");
		Pics pics = null;
		if (id > 0)
			pics = picsService.get(id);
		mav.addObject("picsForm", PicsForm.getInstance(pics));

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id) {
		picsService.delete(id);
		return "redirect:/picss";
	}

	@GetMapping("/list")
	String home(Principal principal) {
		return "picss";
	}
}
