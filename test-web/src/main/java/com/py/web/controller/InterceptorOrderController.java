package com.py.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Date: Created in 2019/06/18
 */
@RestController
public class InterceptorOrderController {

	@RequestMapping("/order/first")
	public String orderFirst() {
		return "first";
	}

	@RequestMapping("/order/second")
	public String orderSecond() {
		return "second";
	}

}
