package com.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by xiangt on 2018/8/9.
 */
@Component
public class RouteExcFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(RouteExcFilter.class);
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        Throwable cause = ctx.getThrowable();
        HttpServletResponse response = ctx.getResponse();
        HttpServletRequest request = ctx.getRequest();
        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Token,DateHeader");
            response.setHeader("Content-Type", "Origin, X-Requested-With, Content-Type, Accept");
            ctx.setSendZuulResponse(false);
            return null;
        }
        if (cause != null){
            sendError(ctx, response, HttpServletResponse.SC_BAD_GATEWAY, "该服务不可用");
        }
        return null;
    }
    private void sendError(RequestContext ctx, HttpServletResponse response, int responseStatu, String message) {
        try {
            response.sendError(responseStatu, message);
            ctx.setSendZuulResponse(false);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}
