package com.py.web.controller;

import org.hashids.Hashids;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zhenghui.li
 * @Date: Created in 2019/06/04
 */
@RestController
public class TestController {

	@RequestMapping("/test/hello")
	public Object test01() {
		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.postForObject()
		return "hello world";
	}

}
