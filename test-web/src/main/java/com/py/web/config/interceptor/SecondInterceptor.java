package com.py.web.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 二级拦截器;
 * @Date: Created in 2019/06/18
 */
@Component
@Slf4j
public class SecondInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
		log.info("uri = {}", request.getRequestURI());
		System.out.println("Interceptor = 第二级拦截器 order = 2");
		return true;
	}

}
