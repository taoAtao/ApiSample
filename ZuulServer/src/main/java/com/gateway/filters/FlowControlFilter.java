package com.gateway.filters;

import com.gateway.services.CryptoService;
import com.gateway.services.UserService;
import com.gateway.utils.JedisPoolUtil;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.ShardedJedisPool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by pengns on 2018/8/7.
 */
@Component
public class FlowControlFilter extends ZuulFilter {

    private static final Logger logger= LoggerFactory.getLogger(FlowControlFilter.class);

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
        return 2;
    }

    public boolean shouldFilter() {
        return true;
    }

    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;

    private ShardedJedisPool pool = JedisPoolUtil.getShardedJedisPool();

    /**
     * 对某个键的值自增
     * @param key 键
     * @param cacheSeconds 超时时间，0为不超时
     * @return
     */
    private  long setIncr(String key, int cacheSeconds) {
        long result = 0;
        try {
            result = redisTemplate.opsForValue().increment(key, 1);
            if(result == 1){
                redisTemplate.expire(key, cacheSeconds, TimeUnit.SECONDS);
            }
            //System.out.println("当前计数:" + jedis.get(key).toString());
            logger.debug("set "+ key + " = " + result);
        } catch (Exception e) {
            logger.warn("set "+ key + " = " + result);
        }
        return result;
    }

    /**
     * 是否拒绝服务
     * @param key
     * @param cacheSeconds key存活时间
     * @param num 阈值，超过该值拒绝服务
     * @return
     */
    private boolean denialOfService(String key, int cacheSeconds, int num){
        long count = 0;
        count = setIncr(key, cacheSeconds);
        if(count <= num){
            return false;
        }
        return true;
    }

    /**
     * 根据key返回配置信息
     * @param key
     * @return
     */
    private Map<Object, Object> getConfFromRedis(String key){
        Map<Object, Object> map = new HashMap<>();
        try {
            map = redisTemplate.opsForHash().entries(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    public Object run(){
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        HttpServletResponse response=ctx.getResponse();

        if(request.getMethod().equals("OPTIONS")){
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Token,DateHeader");
            response.setHeader("Content-Type","Origin, X-Requested-With, Content-Type, Accept");
            ctx.setSendZuulResponse(false);
            return null;
        }
        String token=request.getHeader("Token");
        if(token==null||token.length()<=0){
            sendError(ctx,response,HttpServletResponse.SC_BAD_REQUEST,"Request Header Must Contain Token");
            return null;
        }
        String userName = cryptoService.getUsernameByToken(token);
        if(userName == null){
            sendError(ctx,response,HttpServletResponse.SC_BAD_REQUEST,"Request must contain user");
            return null;
        }
        String uri = request.getRequestURI();
        uri = uri.trim();
        Map<Object, Object> apiConf = getConfFromRedis(uri+"@flowcontrol");
        Boolean isDenialApi = true;
        if(apiConf.size() < 2){
            //isDenialApi = denialOfService(uri, 300, 50);
            sendError(ctx,response,HttpServletResponse.SC_BAD_REQUEST,"No configuration information for API");
            return null;
        }else{
            String time = apiConf.get("time").toString();
            String threshold = apiConf.get("threshold").toString();
            isDenialApi = denialOfService(uri, Integer.parseInt(time), Integer.parseInt(threshold));
        }
        userName = userName.trim();
        Map<Object, Object> userConf = getConfFromRedis(userName+"@flowcontrol");
        Boolean isDenialUser = true;
        if(userConf.size() < 2){
            //isDenialApi = denialOfService(userName, 300, 50);
            sendError(ctx,response,HttpServletResponse.SC_BAD_REQUEST,"No configuration information for API");
            return null;
        }else{
            String time = userConf.get("time").toString();
            String threshold = userConf.get("threshold").toString();
            isDenialUser = denialOfService(userName, Integer.parseInt(time), Integer.parseInt(threshold));
        }
        if(isDenialUser || isDenialApi){
            sendError(ctx,response,HttpServletResponse.SC_BAD_REQUEST,"flow control");
            return null;
        }
        return true;
    }


    private void sendError(RequestContext ctx,HttpServletResponse response,int responseStatu,String message){
        try{
            response.sendError(responseStatu,message);
            ctx.setSendZuulResponse(false);
        }
        catch (IOException e){
            logger.error(e.getMessage());
        }
    }



}
