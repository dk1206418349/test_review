package com.cy.pj.common.web;

import java.io.PrintStream;
import java.util.Date;

import org.apache.shiro.ShiroException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cy.pj.common.vo.JsonResult;
/**全局异常处理类*/
//@ControllerAdvice
@RestControllerAdvice
public class GlobalExceptionHandler {
	//JDK中的自带的日志API
	/**描述方法是异常处理方法*/
	@ExceptionHandler(RuntimeException.class)
	@ResponseBody/**将返回结果转换成json串*/
	public JsonResult doHandleRuntimeException(RuntimeException re) {
		re.printStackTrace();//也可以写日志
		System.out.println("李七夜"+new Date().toLocaleString()+"杀亿人");
		return new JsonResult(re);//封装异常信息
	}
	@ExceptionHandler(ShiroException.class)
	//@ResponseBody
	public JsonResult doHandleShiroException(ShiroException e) {
		JsonResult r=new JsonResult();
		r.setState(0);
		if(e instanceof UnknownAccountException) {
			r.setMessage("账户不存在");
		}else if(e instanceof LockedAccountException) {
			r.setMessage("账户已被禁用");
		}else if(e instanceof IncorrectCredentialsException) {
			r.setMessage("密码不正确");
		}else if(e instanceof AuthorizationException) {
			r.setMessage("没有此操作权限");
		}else {
			r.setMessage("系统维护中");
		}
		e.printStackTrace();
		return r;
	}
}
