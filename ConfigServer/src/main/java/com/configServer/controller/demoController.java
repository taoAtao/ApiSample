package com.configServer.controller;

import com.configServer.model.ZuulRoute;
import com.configServer.service.ApiRefreshService;
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
    ApiRefreshService apiRefreshService;

    @RequestMapping("/all")
    public List<ZuulRoute> getAllZuulRoute() {
        return zuulRouteService.getAllZuulRoute();
    }

    @RequestMapping("/add")
    public String addZuulRoute() {
        ZuulRoute zuulRoute = new ZuulRoute();
        zuulRoute.setId("alarm");
        zuulRoute.setUrl("http://localhost:8002");
        zuulRoute.setPath("/alarm/**");
        zuulRoute.setServiceId("adasd");
        zuulRoute.setApiName("alarm");
        zuulRoute.setEnabled(true);
        zuulRoute.setRetryable(false);
        zuulRoute.setStripPrefix(true);
        return zuulRouteService.addZuulRoute(zuulRoute);
    }

    @RequestMapping("/refresh")
    public String refresh() {
        return apiRefreshService.refresh();
    }
}
