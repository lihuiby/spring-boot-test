package com.py.web.config;

import com.py.web.config.filter.TraceIdFilter;
import com.py.web.config.interceptor.FirstInterceptor;
import com.py.web.config.interceptor.SecondInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description:
 * @Date: Created in 2019/06/18
 */

@Configuration
public class AppConfig implements WebMvcConfigurer {

	@Autowired
	private FirstInterceptor firstInterceptor;

	@Autowired
	private SecondInterceptor secondInterceptor;

	@Autowired
	private TraceIdFilter traceIdFilter;

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(firstInterceptor)
				.order(0)
				.addPathPatterns("/**")
		;

		registry.addInterceptor(secondInterceptor)
				.order(2)
				.addPathPatterns("/order/first")
		;

	}

	@Bean
	public FilterRegistrationBean<TraceIdFilter> getTraceIdFilter() {
		FilterRegistrationBean<TraceIdFilter> filterRegistrationBean = new FilterRegistrationBean<>();
		filterRegistrationBean.setFilter(traceIdFilter);
		System.out.println("traceIdFilter = " + traceIdFilter);
		filterRegistrationBean.setName("traceIdFilter");
		filterRegistrationBean.addUrlPatterns("/*");
		return filterRegistrationBean;
	}
}