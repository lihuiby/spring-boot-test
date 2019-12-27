package com.py.web.controller;

import com.py.common.lock.annotatiop.LockType;
import com.py.common.lock.annotatiop.YLock;
import com.py.common.lock.test.LockUserTest;
import com.py.common.model.ResponseModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author: li
 * @Date: Created in 2019/12/27
 */
@RestController
@Slf4j
public class LockAnnotatiopController {

	@RequestMapping("/test/lock/name")
	@YLock(lockType = LockType.TRY_LOCK, lockKey = "name")
	public ResponseModel testLock(String name, int age) {
		ResponseModel rm = new ResponseModel();

		return rm;
	}

	@RequestMapping("/test/lock/error")
	@YLock(lockType = LockType.IS_LOCK, lockKey = "age")
	public ResponseModel testError(String type, LockUserTest lockUser) {
		ResponseModel rm = new ResponseModel();

		int i = 1 / 0;

		return rm;
	}
}
