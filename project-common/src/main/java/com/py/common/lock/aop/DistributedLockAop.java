package com.py.common.lock.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @Description: 分布式锁
 * @author: li
 * @Date: Created in 2019/12/27
 */
@Component
@Aspect
@Slf4j
public class DistributedLockAop {

	@Pointcut("@annotation(com.py.common.lock.annotatiop.YLock)")
//	@Pointcut("execution(* com.py.web.controller.*.*(..))")
	public void annotationPoinCut(){}

	@Before(value = "annotationPoinCut()")
	public void before(JoinPoint joinPoint) {
		log.info("lockAop before.");
	}

	@After(value = "annotationPoinCut()")
	public void after(JoinPoint joinPoint) {
		log.info("lockAop after.");
	}

	@AfterReturning(value = "annotationPoinCut()")
	public void afterReturning(JoinPoint joinPoint) {
		log.info("lockAop afterReturning.");
	}

	@AfterThrowing(value = "annotationPoinCut()")
	public void afterThrowing(JoinPoint joinPoint) {
		log.info("lockAop afterThrowing.");
	}

	@Around(value = "annotationPoinCut()")
	public void around(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("lockAop around. befor");
		joinPoint.proceed();

		/*try {
			joinPoint.proceed();
		} catch (Throwable throwable) {
			log.error("around error.");
			throwable.printStackTrace();
		}finally {
			log.info("around finally.");
		}*/

		log.info("lockAop around. after");
	}

}
