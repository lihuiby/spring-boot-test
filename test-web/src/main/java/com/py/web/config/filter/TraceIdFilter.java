package com.py.web.config.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

/**
 * @Description:
 * @author: li
 * @Date: Created in 2019/08/26
 */
@Slf4j
@Component
public class TraceIdFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.info("Befor tranceIdFilter.");
		String traceId = initTraceId();
		MDC.put("TraceId", traceId);
		log.info("tranceIdFilter : {}", traceId);
		chain.doFilter(request, response);
		MDC.clear();
		log.info("After tranceIdFilter : {}", traceId);
	}

	private String initTraceId() {
		return UUID.randomUUID().toString().replace("-", "");
	}
}
