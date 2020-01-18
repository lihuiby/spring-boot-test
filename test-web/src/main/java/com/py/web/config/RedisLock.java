package com.py.web.config;

import com.py.common.lock.LockInterface;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: li
 * @Date: Created in 2019/12/30
 */
@Component
@Slf4j
public class RedisLock implements LockInterface {
	@Override
	public boolean tryLock(String keyName, String keyValue, int exTime) {
		log.info("RedisLock try Lock.");
		return false;
	}

	@Override
	public boolean unLock(String keyName) {
		log.info("RedisLock unLock.");
		return false;
	}

	@Override
	public boolean isLocked(String keyName) {
		log.info("RedisLock isLocked.");

		return false;
	}
}
