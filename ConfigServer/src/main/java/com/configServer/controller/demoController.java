package com.configServer.controller;

import com.configServer.model.ZuulRoute;
import com.configServer.service.ApiRefresh;
import com.configServer.service.ZuulRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by xiangt on 2018/8/8. 
 */
@RestController
public class demoController {

	@Autowired
	ZuulRouteService zuulRouteService;

	@Autowired
	ApiRefresh apiRefresh;


	@RequestMapping("/all")
	public List<ZuulRoute> getAllZuulRoute(){
		return 	zuulRouteService.getAllZuulRoute();
	}

//	@RequestMapping("/add")
//	public String addZuulRoute(){
//		ZuulRoute zuulRoute = new ZuulRoute("/aa","loacl","ad");
//		return zuulRouteService.addZuulRoute(zuulRoute);

	@RequestMapping("/refresh")
	public String refresh(){
		return apiRefresh.refresh();
	}
}
