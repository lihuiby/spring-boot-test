package com.py.web.controller;

import com.py.common.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @author: li
 * @Date: Created in 2020/01/08
 */
@RestController
@Slf4j
public class AsyncController {

	@RequestMapping("/async/test")
	public ResponseModel testAsync(String param) {
		log.info("Test Async Begin.");

		CompletableFuture.supplyAsync(() -> {
			log.info("test02 -> {}", param);
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if("error".equals(param)) {
				int i = 0/0;
			}
			return param + " hello .";
		}).thenAcceptAsync((res) -> {
			log.info("Async--autoDailySettle--result:" + res);
		}).exceptionally((e) -> {
			log.error("Async--autoDailySettle--excepiton:", e);
			return null;
		});

		log.info("Test Async End.");
		return new ResponseModel(param);
	}
}
