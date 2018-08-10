package com.gateway.filters;

import com.gateway.models.User;
import com.gateway.services.CryptoService;
import com.gateway.services.UserService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by daip on 2018/7/6.
 */
@Component
public class VerifyIdentityFilter extends ZuulFilter {

    private final Logger logger = LoggerFactory.getLogger(VerifyIdentityFilter.class);

    @Autowired
    private UserService userService;

    @Autowired
    private CryptoService cryptoService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response = ctx.getResponse();
        if (request.getMethod().equals("OPTIONS")) {
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Token,DateHeader");
            response.setHeader("Content-Type", "Origin, X-Requested-With, Content-Type, Accept");
            ctx.setSendZuulResponse(false);
            ctx.set("enableFilter",false);
            return null;
        }
        String dateStr = request.getHeader("DateHeader");
        if (dateStr == null || dateStr.length() <= 0) {
            sendError(ctx, response, HttpServletResponse.SC_BAD_REQUEST, "Request Header Must Contain Date");
            return null;
        }
        try {
            long date = Long.parseLong(dateStr);
            if (Math.abs(System.currentTimeMillis() - date) > 900000) {
                sendError(ctx, response, HttpServletResponse.SC_BAD_REQUEST, "Request Time Error");
                return null;
            }
        } catch (NumberFormatException e) {
            sendError(ctx, response, HttpServletResponse.SC_BAD_REQUEST, "Date Error");
            return null;
        }
        String token = request.getHeader("Token");
        if (token == null || token.length() <= 0) {
            sendError(ctx, response, HttpServletResponse.SC_BAD_REQUEST, "Request Header Must Contain Token");
            return null;
        }
        if (!verifyToken(token, dateStr)) {
            sendError(ctx, response, HttpServletResponse.SC_BAD_REQUEST, "Verify Error");
        }
        return null;
    }

    private void sendError(RequestContext ctx, HttpServletResponse response, int responseStatu, String message) {
        try {
            response.sendError(responseStatu, message);
            ctx.set("enableFilter",false);
            ctx.setSendZuulResponse(false);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private boolean verifyToken(String token, String dateStr) {
        String username = null;
        String decodeInfoString = null;
        String[] tokenDetail = null;
        if (cryptoService != null) {
            try {
                decodeInfoString = cryptoService.base64Decode(token);
                if (decodeInfoString != null && decodeInfoString.indexOf(":") > 0) {
                    tokenDetail = decodeInfoString.split(":");
                    if (tokenDetail.length != 2) {
                        return false;
                    }
                } else {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
        }
        User user = null;
        List userlist = null;
        if (userService != null) {
            userlist = userService.getUserByName(tokenDetail[0]);
            if (userlist == null || userlist.size() != 1) {
                return false;
            }
            try {
                user = (User) userlist.get(0);
            } catch (ClassCastException e) {
                return false;
            }
        }
        if (user == null) {
            return false;
        }
        try {
            if (cryptoService.base64Encode(cryptoService.hmacsha1Encode(dateStr, user.getPassword())).equals(tokenDetail[1])) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

}
