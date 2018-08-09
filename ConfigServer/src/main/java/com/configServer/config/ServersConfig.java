package com.configServer.config;

import com.configServer.model.Server;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangt on 2018/8/8.
 */
@Component
@ConfigurationProperties(prefix = "front")
public class ServersConfig {

    List<Server> servers = new ArrayList<>();

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }
}

