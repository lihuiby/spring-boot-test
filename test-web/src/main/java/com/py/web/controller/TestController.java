package com.py.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @Description:
 * @Author: zhenghui.li
 * @Date: Created in 2019/06/04
 */
@RestController
@Slf4j
public class TestController {

	@RequestMapping("/test/hello")
	public Object test01() {
		RestTemplate restTemplate = new RestTemplate();
//		restTemplate.postForObject()
		return "hello world";
	}

	@RequestMapping(value = "/setName")
	@ResponseBody
	public Object setAdminName(String name) {
		System.out.println("===> name : " + name);
		log.info("name = {}", name);
		log.error("error name = {}", name);

		String traceId = MDC.get("X-B3-TraceId");
		Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

		new Thread(() -> {
//			MDC.put("X-B3-TraceId", traceId);
			MDC.setContextMap(copyOfContextMap);
			log.info("new Thread name = {} , traceId={}, contextMap={}", Thread.currentThread().getName(), traceId, copyOfContextMap);
		}).start();

		return "{'name':'" + name + "'}";
	}

}
