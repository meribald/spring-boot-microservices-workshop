package io.javabrains.movieinfoservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "consulexample")
public class MyConfig {

	private String myproperty;

	public String getMyproperty() {
		return myproperty;
	}

	public void setMyproperty(String myproperty) {
		this.myproperty = myproperty;
	}
}
