package com.cy.pj.sys.controller;

import java.util.Date;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;
/**
 * JS排错，console.log()
 */
@RequestMapping("/log/")
@Controller
//@RestController/**该注解有被controller和responsebody修饰*/
public class SysLogController {
	@Autowired
	private SysLogService ss;
	@RequestMapping("doFindPageObject")
	@ResponseBody
	public JsonResult doFindPageObject(String username,Integer pageCurrent) {
		PageObject<SysLog> pp = ss.findPageObject(username, pageCurrent);
		return new JsonResult(pp);
	}
	@RequestMapping("doDeleteObjectById")
	@ResponseBody
	public JsonResult doDeleteObject(Integer... ids) {
		int rows = ss.deleteObjectById(ids);
		return new JsonResult("成功删除"+rows+"条");
	}
	/**如果出现异常每个类都要写异常方法，很麻烦*/
//	@ExceptionHandler(RuntimeException.class)
//	@ResponseBody/**将返回结果转换成json串*/
//	public JsonResult doHandleRuntimeException(RuntimeException re) {
//		re.printStackTrace();//也可以写日志
//		System.out.println("李七夜"+new Date().toLocaleString()+"杀亿人");
//		return new JsonResult(re);//封装异常信息
//	}
}
