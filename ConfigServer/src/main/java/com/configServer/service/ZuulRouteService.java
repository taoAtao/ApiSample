package com.configServer.service;

import com.configServer.model.ZuulRoute;
import com.configServer.repository.ZuulRouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiangt on 2018/8/8. 
 */

@Service
public class ZuulRouteService {
	@Autowired
	private ZuulRouteRepository zuulRouteRepository;

	public List<ZuulRoute> getAllZuulRoute() {
		return zuulRouteRepository.findAll();
	}

	public String addZuulRoute(ZuulRoute zuulRoute){
		zuulRouteRepository.save(zuulRoute);
		return "true";
	}
}
