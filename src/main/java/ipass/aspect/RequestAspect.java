package ipass.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RequestAspect {

	private static final Logger log = LoggerFactory.getLogger(RequestAspect.class);

	ThreadLocal<Long> startTime = new ThreadLocal<>();

	@Pointcut("execution(public * ipass.controller..*.*(..))")
	public void controller() {
	}

	@Before("controller()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		startTime.set(System.currentTimeMillis());
		// 省略日志记录内容
	}

	@AfterReturning(returning = "ret", pointcut = "controller()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.info("END. SPEND TIME : " + (System.currentTimeMillis() - startTime.get()));
	}

}