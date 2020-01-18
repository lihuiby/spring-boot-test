package com.py.common.lock;

/**
 * @Description:
 * @author: li
 * @Date: Created in 2019/12/30
 */
public interface LockInterface {

	/** 加锁 */
	boolean tryLock(String keyName, String keyValue, int exTime);

	/** 释放锁 */
	boolean unLock(String keyName);

	/** 是否可用 true 可以用, false 不可用 */
	boolean isLocked(String keyName);
}
