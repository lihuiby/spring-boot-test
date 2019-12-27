package com.py.common.lock.annotatiop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description:
 * @author: li
 * @Date: Created in 2019/12/20
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface YLock {
	/** 是否使用lock */
	boolean value() default true;

	/** 锁的有效时间, 默认 5分钟 单位秒 */
	int existence() default 300;

	/** key的name值 */
	String lockKey();

	/** 参数坐标 */
	LockParameterIndex lockParamIndex() default LockParameterIndex.INDEX_0;

	/** key拼接的值 */
	String[] paramKeyName() default {};

	Class[] paramKeyType() default {};

	/** 拼接符 */
	String splice() default "_";

	/** 锁的类型, tryLock(尝试锁), is_lock(判断锁) */
	LockType lockType();

	/**
	 * 锁的key值前缀
	 * @return int
	 */
	String lockKeyPrefix() default "";
}