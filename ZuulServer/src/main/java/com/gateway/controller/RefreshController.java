package com.gateway.controller;

import com.gateway.routeLocator.RefreshRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.web.ZuulHandlerMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by xiangt on 2018/8/7.
 */
@RestController
public class RefreshController {

    @Autowired
    RefreshRouteService refreshRouteService;

    @RequestMapping("/refreshRoute")
    public String refreshRoute() {
        refreshRouteService.refreshRoute();
        return "refreshRoute";
    }

    @Autowired
    ZuulHandlerMapping zuulHandlerMapping;

    @RequestMapping("/watchNowRoute")
    public String watchNowRoute() {
        //可以用debug模式看里面具体是什么
        Map<String, Object> handlerMap = zuulHandlerMapping.getHandlerMap();
        return String.valueOf(handlerMap);
    }
}
