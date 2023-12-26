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

import com.dea42.aitools.entity.Account;
import com.dea42.aitools.form.AccountForm;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.search.AccountSearchForm;
import com.dea42.aitools.service.AccountServices;
import com.dea42.aitools.utils.Message;
import com.dea42.aitools.utils.MessageHelper;
import com.dea42.aitools.utils.Utils;
import lombok.extern.slf4j.Slf4j;

/**
 * Title: AccountController <br>
 * Description: AccountController. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@Slf4j
@Controller
@RequestMapping("/accounts")
public class AccountController {

	@Autowired
	private AccountServices accountService;

	private AccountSearchForm getForm(HttpServletRequest request) {
		AccountSearchForm form = (AccountSearchForm) request.getSession().getAttribute("accountSearchForm");
		if (log.isDebugEnabled())
			log.debug("pulled from session:" + form);
		if (form == null) {
			form = new AccountSearchForm();
		}
		return form;
	}

	private void setForm(HttpServletRequest request, AccountSearchForm form) {
		request.getSession().setAttribute("accountSearchForm", form);
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
	public ModelAndView search(HttpServletRequest request, @ModelAttribute AccountSearchForm form, 
			RedirectAttributes ra, @RequestParam(value = "action", required = true) String action) {
		ModelAndView mav;
		if (action.equals("search")) {
			setForm(request, form);
			form.setAdvanced(true);
			mav = new ModelAndView("accounts");
//			mav = findPaginated(request, 1, "id", "asc");
//			@SuppressWarnings("unchecked")
//			List<Account> list = (List<Account>) mav.getModelMap().getAttribute("accounts");
//			if (list == null || list.isEmpty()) {
//				mav.setViewName("search_account");
//				mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
//						new Message("search.noResult", Message.Type.WARNING));
//			}
		} else {
			form = new AccountSearchForm();
			setForm(request, form);
			mav = new ModelAndView("search_account");
			mav.addObject("accountSearchForm", form);
			mav.getModelMap().addAttribute(Message.MESSAGE_ATTRIBUTE,
					new Message("search.formReset", Message.Type.WARNING));
		}

		return mav;
	}

	@GetMapping("/search/{pageNo}")
	public ModelAndView findPaginated(HttpServletRequest request, @PathVariable(value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField, @RequestParam("sortDir") String sortDir) {
		AccountSearchForm form = getForm(request);
		if (pageNo < 1)
			pageNo = 1;

		form.setPage(pageNo);
		form.setSortField(sortField);
		form.setSortAsc("asc".equalsIgnoreCase(sortDir));

		if (log.isDebugEnabled())
			log.debug("Searching with:" + form);

		Page<Account> page = accountService.listAll(form);

		form.setTotalPages(page.getTotalPages());
		form.setTotalItems(page.getTotalElements());
		setForm(request, form);

		ModelAndView mav = new ModelAndView("accounts");
		mav.addObject("accounts", page.getContent());
		return mav;
	}

	@GetMapping("/search")
	public String showSearchPage(HttpServletRequest request, Model model,
			@RequestHeader(value = "X-Requested-With", required = false) String requestedWith) {
		model.addAttribute(getForm(request));
		if (Utils.isAjaxRequest(requestedWith)) {
			return "search_account".concat(" :: accountSearchForm");
		}

		return "search_account";
	}

	@PostMapping(value = "/save")
	public String save(@Valid @ModelAttribute AccountForm form, Errors errors, RedirectAttributes ra,
			@RequestParam(value = "action", required = true) String action) {
		if (action.equals("save")) {
			if (errors.hasErrors()) {
				return "edit_account";
			}

			Account account = new Account();
			account.setEmail(form.getEmail());
			account.setId(form.getId());
			account.setName(form.getName());
			account.setPassword(form.getPassword());
			account.setUserrole(form.getUserrole());
			try {
				account = accountService.save(account);
			} catch (Exception e) {
				log.error("Failed saving:" + form, e);
			}

			if (account == null) {
				MessageHelper.addErrorAttribute(ra, "db.failed");
			} else {
				MessageHelper.addSuccessAttribute(ra, "save.success");
			}
		} else {
			MessageHelper.addSuccessAttribute(ra, "save.cancelled");
		}

		return "redirect:/accounts";
	}

	@GetMapping("/edit/{id}")
	public ModelAndView showEditPage(@PathVariable(name = "id") Integer id) {
		ModelAndView mav = new ModelAndView("edit_account");
		Account account = null;
		if (id > 0)
			account = accountService.get(id);
		mav.addObject("accountForm", AccountForm.getInstance(account));

		return mav;
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable(name = "id") Integer id) {
		accountService.delete(id);
		return "redirect:/accounts";
	}

	@GetMapping("/list")
	String home(Principal principal) {
		return "accounts";
	}
}
