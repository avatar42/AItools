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

import com.dea42.aitools.entity.Servers;
import com.dea42.aitools.form.ServersForm;
import com.dea42.aitools.search.ServersSearchForm;
import com.dea42.aitools.service.ServersServices;
import com.dea42.aitools.utils.Message;
import com.dea42.aitools.utils.MessageHelper;
import com.dea42.aitools.utils.Utils;

import lombok.extern.slf4j.Slf4j;

/**
 * Title: ServersController <br>
 * Description: ServersController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Controller
@RequestMapping("/serverss")
public class ServersController {

	@Autowired
	private ServersServices serversService;

	private ServersSearchForm getForm(HttpServletRequest request) {
		ServersSearchForm form = (ServersSearchForm) request.getSession().getAttribute("serversSearchForm");
		if (log.isDebugEnabled())
			log.debug("pulled from session:" + form);
		if (form == null) {
			form = new ServersSearchForm();
		}
		return form;
	}

	private void setForm(HttpServletRequest request, ServersSearchForm form) {
		request.getSession().setAttribute("serversSearchForm", form);
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
	public ModelAndView search(HttpServletRequest request, @ModelAttribute ServersSearchForm form, 
			RedirectAttributes ra, @RequestParam(value = "action", required = true) String action) {
		ModelAndView mav;
		if (action.equals("search")) {
			setForm(request, form);
			form.setAdvanced(true);
			mav = new ModelAndView("serverss");
//			mav = findPaginated(request, 1, "id", "asc");
//			@SuppressWarnings("unchecked")
//			List<Servers> list = (List<Servers>) mav.getModelMap().getAttribute("serverss");
//			if (list == null || list.isEmpty()) {
//				mav.setViewName("search_servers");
//				mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
//						new Message("search.noResult", Message.Type.WARNING));
//			}
		} else {
			form = new ServersSearchForm();
			setForm(request, form);
			mav = new ModelAndView("search_servers");
			mav.addObject("serversSearchForm", form);
			mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
					new Message("search.formReset", Message.Type.WARNING));
		}

		return mav;
	}

	@GetMapping("/search/{pageNo}")
	public ModelAndView findPaginated(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		ServersSearchForm form = getForm(request);
		if (pageNo < 1)
			pageNo = 1;

		form.setPage(pageNo);
		form.setSortField(sortField);
		form.setSortAsc("asc".equalsIgnoreCase(sortDir));

		if (log.isDebugEnabled())
			log.debug("Searching with:" + form);

		Page<Servers> page = serversService.listAll(form);

		form.setTotalPages(page.getTotalPages());
		form.setTotalItems(page.getTotalElements());
		setForm(request, form);

		ModelAndView mav = new ModelAndView("serverss");
		mav.addObject("serverss", page.getContent());
		return mav;
	}

	@GetMapping("/search")
	public String showSearchPage(HttpServletRequest request, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(getForm(request));
		if (Utils.isAjaxRequest(requestedWith)) {
			return "search_servers".concat(" :: serversSearchForm");
		}

		return "search_servers";
	}

	@PostMapping(value = "/save")
	public String save(@Valid @ModelAttribute ServersForm form, Errors errors, RedirectAttributes ra,
			@RequestParam(value = "action", required = true) String action) {
		if (action.equals("save")) {
			if (errors.hasErrors()) {
				return "edit_servers";
			}

			Servers servers = new Servers();
			servers.setId(form.getId());
			servers.setModel(form.getModel());
			servers.setUrl(form.getUrl());
			try {
				servers = serversService.save(servers);
			} catch (Exception e) {
				log.error("Failed saving:" + form, e);
			}

			if (servers == null) {
				MessageHelper.addErrorAttribute(ra, "db.failed");
			} else {
				MessageHelper.addSuccessAttribute(ra, "save.success");
			}
		} else {
			MessageHelper.addSuccessAttribute(ra, "save.cancelled");
		}

		return "redirect:/serverss";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditPage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_servers");
		Servers servers = null;
		if (id > 0)
			servers = serversService.get(id);
		mav.addObject("serversForm", ServersForm.getInstance(servers));

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id) {
		serversService.delete(id);
		return "redirect:/serverss";
	}

	@GetMapping("/list")
	String home(Principal principal) {
		return "serverss";
	}
}
