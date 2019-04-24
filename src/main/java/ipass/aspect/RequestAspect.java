package ipass.aspect;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import ipass.util.AppUtil;
import ipass.util.HeaderKeys;
import ipass.util.LogBean;
import ipass.util.RequestUtil;

@Aspect
@Component
public class RequestAspect {

	@Value("${app.debug}")
	private boolean debug;

	@Value("${app.mercy.seconds}")
	private int mercy = 600;

	@Value("${app.signkey}")
	private String appSignKey;

	@Pointcut("execution(public * ipass.controller..*.*(..))")
	public void controller() {
	}

	@Before("controller()")
	public void doBefore(JoinPoint joinPoint) throws Throwable {
		LogBean.start();
		LogBean logBean = LogBean.get();
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		logBean.setPath(request.getRequestURI());
		logBean.setIp(RequestUtil.getIp(request));

		MethodSignature joinPointObject = (MethodSignature) joinPoint.getSignature();
		Method method = joinPointObject.getMethod();
		Object[] args = joinPoint.getArgs();
		Parameter[] parameters = method.getParameters();
		List<String> source = new ArrayList<String>();
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < parameters.length; i++) {
			if (parameters[i].getAnnotation(RequestBody.class) != null) {
				logBean.setPayload(args[i]);
			} else {
				params.put("p" + i, args[i]);
				if (args[i] != null) {
					source.add(args[i].toString());
				}
			}
		}
		logBean.setParams(params);

		if (debug) {
			logBean.setHeaders(RequestUtil.getHeaders(request));
			return;
		}
		String curTimeStr = request.getHeader(HeaderKeys.KEY_CUR_TIME);
		String nonce = request.getHeader(HeaderKeys.KEY_NONCE);
		String checksum = request.getHeader(HeaderKeys.KEY_CHECKSUM);

		if (StringUtils.isBlank(curTimeStr) || StringUtils.isBlank(nonce) || StringUtils.isBlank(checksum)) {
			throw new IllegalAccessException("Header Required.");
		}
		// mercy
		try {
			long curTime = Long.parseLong(curTimeStr);
			if (Math.abs(System.currentTimeMillis() - curTime) > mercy * 1000) {
				throw new IllegalAccessException("Sign is Expired.");
			}
		} catch (NumberFormatException ex) {
			logBean.addProp("InvalidCurTime", curTimeStr);
			throw new IllegalAccessException("Invalid CurTime Format.");
		}

		source.add(curTimeStr);
		source.add(nonce);
		source.add(appSignKey);
		String checksum0 = AppUtil.sign(source.toArray(new String[0]));
		if (!checksum.equalsIgnoreCase(checksum0)) {
			logBean.addProp("client.checksum", checksum);
			logBean.addProp("server.checksum", checksum0);
			throw new IllegalAccessException("Sign Invalid.");
		}
	}

	@AfterReturning(returning = "ret", pointcut = "controller()")
	public void doAfterReturning(Object ret) throws Throwable {
		// 处理完请求，返回内容
		log.debug("success");
		LogBean.end();
	}

	@AfterThrowing(throwing = "ex", pointcut = "controller()")
	public void doRecoveryActions(Throwable ex) {
		LogBean.get().setCode(500);
		LogBean.get().setError(ex.getMessage());
		LogBean.end();
	}

	private static final Logger log = LoggerFactory.getLogger(RequestAspect.class);

}