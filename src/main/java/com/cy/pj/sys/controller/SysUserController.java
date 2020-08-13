package com.cy.pj.sys.controller;

import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.service.SysUserService;
import com.cy.pj.sys.vo.SysUserDeptVo;
@RequestMapping("user/")
@RestController
public class SysUserController {
	@Autowired
	private SysUserService sus;
	//@RequestMapping("doFindPageObjects")
	@GetMapping("doFindPageObjects")
	public JsonResult doFindPageObjects(String username,Integer pageCurrent) {
		PageObject<SysUserDeptVo> pageObject = sus.findPageObjects(username, pageCurrent);
		return new JsonResult(pageObject);
	}
	@RequestMapping("doValidById")
	public JsonResult doValidById(Integer id,Integer valid) {
		sus.validById(id, valid, "admin");
		return new JsonResult("修改成功");
	}
	@RequestMapping("doSaveObject")
	public JsonResult saveObject(SysUser entity,Integer[]roleIds) {
		int rows = sus.saveObject(entity, roleIds);
		return new JsonResult("成功保存"+rows+"条信息");
	}
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		Map<String, Object> map = sus.findObjectById(id);
		return new JsonResult(map);
	}
	@RequestMapping("doUpdateObject")
	public JsonResult updateObject(SysUser entity,Integer[]roleIds) {
		int rows = sus.updateObject(entity, roleIds);
		return new JsonResult("成功修改"+rows+"条信息");
	}
	@RequestMapping("doLogin")
	   public JsonResult doLogin(String username,String password,
			   boolean isRememberMe){
		   //1.获取Subject对象
		   Subject subject=SecurityUtils.getSubject();
		   //2.通过Subject提交用户信息,交给shiro框架进行认证操作
		   //2.1对用户进行封装
		   UsernamePasswordToken token=
		   new UsernamePasswordToken(
				   username,//身份信息
				   password);//凭证信息
		   if(isRememberMe) {
			   token.setRememberMe(true);
		   }
		   System.out.println(token);
		   //2.2对用户信息进行身份认证
		   subject.login(token);
		   //分析:
		   //1)token会传给shiro的SecurityManager
		   //2)SecurityManager将token传递给认证管理器
		   //3)认证管理器会将token传递给realm
		   return new JsonResult("login ok");
	   }
}
