package com.cy.pj.common.aspect;

import java.lang.reflect.Method;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cy.pj.common.annotation.Dark;
import com.cy.pj.common.util.IPUtils;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Aspect
@Component
public class SysLogAspect {
	/**
	 * @Pointcut注解用于定义切入点
	 *1 bean表达式为切入点表达式,
	 *2 within(包名.类名)
	 *3 execution(返回值类型 包名.类名.方法名(参数列表))
	 * execution(* com.jt..*.*(..))
	 *4 @annoation(注解路径)
	 * bean表达式内部指定的bean对象中
	 *   所有方法为切入点(进行功能扩展的点)
	 */
	//@Pointcut("bean(sysUserServiceImpl)")
	@Pointcut("@annotation(com.cy.pj.common.annotation.Dark)")
	public void logl() {}
	/**
	 * @Around 描述的方法为环绕通知,用于功能增强
	 *   环绕通知(目标方法执行之前和之后都可以执行)
	 * @param jp 连接点 (封装了要执行的目标方法信息)
	 * @return 目标方法的执行结果
	 * @throws Throwable
	 */
	@Around("logl()")
	public Object around(ProceedingJoinPoint pj) throws Throwable {
		Object proceed=null;
		try {
			long s=System.currentTimeMillis();
			log.info("开始"+s);
			proceed = pj.proceed();
			long e = System.currentTimeMillis();
			log.info("结束"+e);
			insertObject(pj, (e-s));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error(e.getMessage());
			throw e;
		}
		return proceed;
	}
	//	@After("logl()")
	//	public void test2() {
	//		System.out.println("SysLogAspect.test2()");
	//	}
	//	@Before("logl()")
	//	public void test3() {
	//		System.out.println("SysLogAspect.test3()");
	//	}
	//	@AfterReturning("logl()")
	//	public void test4() {
	//		System.out.println("SysLogAspect.test4()");
	//	}
	//	@AfterThrowing("logl()")
	//	public void test5() {
	//		System.out.println("SysLogAspect.test5()");
	//	}
	@Autowired
	private SysLogService sls;

	private void insertObject(ProceedingJoinPoint pj,long time) throws JsonProcessingException, NoSuchMethodException, SecurityException {
		//Object target = pj.getTarget();
		//获取目标类字节码对象
		Class<?> targetClass = pj.getTarget().getClass();
		//获取目标对象类名
		String className = targetClass.getName();
		//获取接口声明方法方法名
		MethodSignature ms = (MethodSignature) pj.getSignature();
		String methodName = ms.getMethod().getName();
		//获取参数数组字节码对象
		Class<?>[] parameterTypes = ms.getMethod().getParameterTypes();
		//通过方法名和参数得到具体方法对象
		Method targetMethod = targetClass.getDeclaredMethod(methodName, parameterTypes);
		//通过方法对象获取注解及内容
		Dark dk = targetMethod.getDeclaredAnnotation(Dark.class);
		String value = dk.value();
		String params=null;
		String operation=value;
		String method=className+"."+methodName;
		params=new ObjectMapper().writeValueAsString(pj.getArgs());
		SysLog log = new SysLog();
		log.setUsername("admin");
		log.setCreatedTime(new Date());
		log.setIp(IPUtils.getIpAddr());;
		log.setMethod(method);
		log.setOperation(operation);
		log.setParams(params);
		log.setTime(time);
		sls.insertObject(log);
		System.out.println("添加日志成功");
	}
}
