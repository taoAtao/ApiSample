package com.configServer.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by xiangt on 2018/8/8. 
 */
@Entity
@Table(name="gateway_api_define")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Component
@Scope("prototype")

public class ZuulRoute {

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
	@Column(name="service_id")
	private String serviceId;

	/**
	 * url.
	 */
	private String url;

	@Column(name="api_name")
	private String apiName;

	@Column(name="enabled")
	private int enabled;

	private int retryable = 0;

	@Column(name="strip_prefix")
	private int stripPrefix = 1;
	public String getApiName() {
		return apiName;
	}

	public void setApiName(String apiName) {
		this.apiName = apiName;
	}

	public int getEnabled() {
		return enabled;
	}

	public int getRetryable() {
		return retryable;
	}

	public int getStripPrefix() {
		return stripPrefix;
	}

	public void setEnabled(int enabled) {
		this.enabled = enabled;
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



	@Override public String toString() {
		return "ZuulRoute{" + "id='" + id + '\'' + ", path='" + path + '\'' + ", serviceId='" + serviceId + '\''
				+ ", url='" + url + '\'' + ", apiName='" + apiName + '\'' + ", enabled=" + enabled + '}';
	}
}