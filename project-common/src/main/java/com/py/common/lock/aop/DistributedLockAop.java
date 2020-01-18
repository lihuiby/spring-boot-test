package com.py.common.lock.aop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.py.common.lock.LockInterface;
import com.py.common.lock.annotatiop.LockType;
import com.py.common.lock.annotatiop.YLock;
import com.py.common.lock.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Description: 分布式锁
 * @author: li
 * @Date: Created in 2019/12/27
 */
@Component
@Aspect
@Slf4j
public class DistributedLockAop {

	@Autowired
	private LockInterface lockInstance;

	@Pointcut("@annotation(com.py.common.lock.annotatiop.YLock)")
	public void annotationPoinCut(){}

	/**
	 * 先考虑正常的, TODO 再处理异常的
	 * @param joinPoint
	 */
	@Around(value = "annotationPoinCut()")
	public void around(ProceedingJoinPoint joinPoint) {
		//获得YLock注解中的值
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
		YLock yLock = methodSignature.getMethod().getAnnotation(YLock.class);
		int paramIndex = yLock.lockParamIndex();
		String keyName = yLock.paramKeyName();
		int exTime = yLock.existence();
		LockType lockType = yLock.lockType();

		try {
			if(!yLock.value()) {
				joinPoint.proceed();
				return;
			}

			//获得请求的参数
			Object[] args = joinPoint.getArgs();

			//TODO 如果没有参数, yonyou设置的需要自定义 tenand_id
			if(args == null || args.length == 0 || args.length - 1 < paramIndex) {
				joinPoint.proceed();
				return;
			}

			String keyValue = null;
			if(args[paramIndex].getClass().isPrimitive()||this.isBasePackage(args[paramIndex].getClass().getName())) {
				//基础类型
				keyValue = args[paramIndex].toString();
			}else if(args[paramIndex] instanceof Map) {
				//map
				Map map = (Map)args[paramIndex];
				keyValue = map.get(keyName).toString();
			}else {
				//自定义类型, 转json, 当前只能一层
				String jsonStr = JSON.toJSONString(args[paramIndex]);
				JSONObject jsonObject = JSON.parseObject(jsonStr);
				keyValue = jsonObject.get(keyName).toString();
			}

			//判断锁类型
			if(LockType.IS_LOCKED == lockType) {
				boolean locked = lockInstance.isLocked(keyName);
				if(locked) {
					throw new LockException("获取锁失败, 请稍后重试.");
				}else {
					joinPoint.proceed();
					return;
				}
			}
			boolean tryLock = lockInstance.tryLock(keyName, keyValue, exTime);
			if(!tryLock) {
				throw new LockException("获取锁失败, 请稍后重试.");
			}
			joinPoint.proceed();
		} catch (Throwable throwable) {
			log.error("Distributed Locked Error. ", throwable);
			throw new RuntimeException(throwable.getMessage());
		}finally {
			log.info("around finally.");
			//释放锁
			if(LockType.TRY_LOCK == lockType) {
				lockInstance.unLock(keyName);
			}
		}
		log.info("around. after");
	}

	/**
	 * 〈是否为从基础包装类型〉
	 * @Param: [type]
	 * @return : boolean
	 */
	private boolean isBasePackage(String type){
		List<String> basePackages = Arrays.asList("java.lang.Integer","java.lang.Double","java.lang.Float","java.lang.Long",
				"java.lang.Short","java.lang.Boolean","java.lang.Char","java.math.BigDecimal","java.math.BigInteger");
		return basePackages.contains(type);
	}



	/*@Before(value = "annotationPoinCut()")
	public void before(JoinPoint joinPoint) {
		log.info("before.");
	}

	@After(value = "annotationPoinCut()")
	public void after(JoinPoint joinPoint) {
		log.info("after.");
	}

	@AfterReturning(value = "annotationPoinCut()")
	public void afterReturning(JoinPoint joinPoint) {
		log.info("afterReturning.");
	}

	@AfterThrowing(value = "annotationPoinCut()")
	public void afterThrowing(JoinPoint joinPoint) {
		log.info("afterThrowing.");
	}*/

}
