package com.py.common.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Date: Created in 2019/06/17
 */
@Component
@PropertySource("classpath:config.properties")
public class ConfigConstant {

	public static String userInfo;
	@Value("${config.test.user}")
	public void setUserInfo(String userInfo) {
		ConfigConstant.userInfo = userInfo;
	}

	public static String privateKey;
	@Value("${config.test.private-key}")
	public void setPrivateKey(String privateKey) {
		ConfigConstant.privateKey = privateKey;
	}

}
