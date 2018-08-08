package com.configServer.model;

import javax.persistence.Entity;

/**
 * Created by xiangt on 2018/8/8. 
 */

public class Server{
		private String name;
		private String url;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
