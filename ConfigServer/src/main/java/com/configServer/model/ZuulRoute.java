package com.configServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by xiangt on 2018/8/8.
 */
@Entity
@Table(name = "gateway_api_define")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Component
@Scope("prototype")

public class ZuulRoute implements Serializable {

    /**
     * 路由id.
     */
    @Id
    private String id;

    /**
     * 路由路径.
     */
    private String path;

    /**
     * 服务名称
     */
    @Column(name = "service_id")
    private String serviceId;

    /**
     * url.
     */
    private String url;

    @Column(name = "api_name")
    private String apiName;

    @Column(name = "enabled")
    private boolean enabled;

    private boolean retryable ;

    @Column(name = "strip_prefix")
    private boolean stripPrefix ;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isRetryable() {
        return retryable;
    }

    public void setRetryable(boolean retryable) {
        this.retryable = retryable;
    }

    public boolean isStripPrefix() {
        return stripPrefix;
    }

    public void setStripPrefix(boolean stripPrefix) {
        this.stripPrefix = stripPrefix;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ZuulRoute{" + "id='" + id + '\'' + ", path='" + path + '\'' + ", serviceId='" + serviceId + '\''
                + ", url='" + url + '\'' + ", apiName='" + apiName + '\'' + ", enabled=" + enabled + '}';
    }
}