package com.py.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description:
 * @Date: Created in 2019/06/17
 */
@Configuration
public class ConfigConstants {

	public static String userInfo;
	public void setUserInfo(String userInfo) {
		ConfigConstants.userInfo = userInfo;
	}

	public static String privateKey;
	@Value("${test.config.private-key}")
	public void setPrivateKey(String privateKey) {
		ConfigConstants.privateKey = privateKey;
	}

}
