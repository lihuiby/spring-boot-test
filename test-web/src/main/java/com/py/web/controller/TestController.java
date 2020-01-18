package com.py.web.controller;

import com.py.common.lock.test.LockUserTest;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
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
	public Object setAdminName(@RequestBody Map<String,Object> params) {
		/*System.out.println("===> name : " + name);
		log.info("name = {}, age = {}", name, age);
		log.error("error name = {}", name);*/
		log.info("params = {}", params);

		String traceId = MDC.get("X-B3-TraceId");
		Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

		new Thread(() -> {
//			MDC.put("X-B3-TraceId", traceId);
			MDC.setContextMap(copyOfContextMap);
			log.info("new Thread name = {} , traceId={}, contextMap={}", Thread.currentThread().getName(), traceId, copyOfContextMap);
		}).start();

		return "{'name':'" + params.get("name") + "'}";
	}

	@PostMapping(value = "/setUser")
	@ResponseBody
	public Object setUser(@RequestBody LockUserTest list) {
		log.info("user = {}", list);

		return "{'name':'" + list.getName() + "'}";
	}

}
