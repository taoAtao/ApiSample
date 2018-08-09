package com.configServer.service;

import com.configServer.config.ServersConfig;
import com.configServer.model.Server;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by xiangt on 2018/8/8.
 */
@Component
public class ApiRefreshService {
    @Autowired
    ServersConfig serversConfig;

    public static String callInterface(String hostname) {
        HttpClient httpClient = new DefaultHttpClient();
        String url = "http://" + hostname + ":8000/refreshRoute";
        HttpGet httpGet = new HttpGet(url);
        String entityStr = null;
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            entityStr = EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return entityStr;
    }

    public String refresh() {
        List<Server> serverList = serversConfig.getServers();
        if (!serverList.isEmpty()) {
            for (Server server : serverList) {
                callInterface(server.getUrl());
            }
            return "更新完成";
        }
        return "服务器为空";
    }
}
