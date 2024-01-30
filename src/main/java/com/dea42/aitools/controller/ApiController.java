package com.dea42.aitools.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dea42.aitools.entity.Account;
import com.dea42.aitools.entity.Classes;
import com.dea42.aitools.entity.Detections;
import com.dea42.aitools.entity.Pics;
import com.dea42.aitools.entity.Servers;
import com.dea42.aitools.paging.PageInfo;
import com.dea42.aitools.paging.PagingRequest;
import com.dea42.aitools.service.AccountServices;
import com.dea42.aitools.service.ClassesServices;
import com.dea42.aitools.service.DetectionsServices;
import com.dea42.aitools.service.PicsServices;
import com.dea42.aitools.service.ServersServices;


/**
 * Title: ApiController <br>
 * Description: Api REST Controller. <br>
 * Copyright: Copyright (c) 2001-2023<br>
 * Company: RMRR<br>
 *
 * @author Gened by com.dea42.build.GenSpring version 0.7.2<br>
 * @version 0.7.2<br>
 */
@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private AccountServices accountServices;
    @Autowired
    private ServersServices serversServices;
    @Autowired
    private PicsServices picsServices;
    @Autowired
    private DetectionsServices detectionsServices;
    @Autowired
    private ClassesServices classesServices;

    public ApiController(){
    }

    @GetMapping("/accounts")
    public List<Account> getAllAccounts(){
        return this.accountServices.listAll(null).toList();
    }

	@PostMapping(value = "/accounts/list")
	public PageInfo<Account> listAccount(HttpServletRequest request,@RequestBody PagingRequest pagingRequest) {

		return accountServices.getAccounts(request, pagingRequest);
	}

    @GetMapping("/serverss")
    public List<Servers> getAllServerss(){
        return this.serversServices.listAll(null).toList();
    }

	@PostMapping(value = "/serverss/list")
	public PageInfo<Servers> listServers(HttpServletRequest request,@RequestBody PagingRequest pagingRequest) {

		return serversServices.getServerss(request, pagingRequest);
	}

    @GetMapping("/picss")
    public List<Pics> getAllPicss(){
        return this.picsServices.listAll(null).toList();
    }

	@PostMapping(value = "/picss/list")
	public PageInfo<Pics> listPics(HttpServletRequest request,@RequestBody PagingRequest pagingRequest) {

		return picsServices.getPicss(request, pagingRequest);
	}

    @GetMapping("/detectionss")
    public List<Detections> getAllDetectionss(){
        return this.detectionsServices.listAll(null).toList();
    }

	@PostMapping(value = "/detectionss/list")
	public PageInfo<Detections> listDetections(HttpServletRequest request,@RequestBody PagingRequest pagingRequest) {

		return detectionsServices.getDetectionss(request, pagingRequest);
	}

    @GetMapping("/classess")
    public List<Classes> getAllClassess(){
        return this.classesServices.listAll(null).toList();
    }

	@PostMapping(value = "/classess/list")
	public PageInfo<Classes> listClasses(HttpServletRequest request,@RequestBody PagingRequest pagingRequest) {

		return classesServices.getClassess(request, pagingRequest);
	}
}
