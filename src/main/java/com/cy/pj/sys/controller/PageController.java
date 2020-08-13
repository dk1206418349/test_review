package com.cy.pj.sys.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;
/**
 * 定义项目的页面
 * @author Administrator
 *
 */
@RequestMapping("/")
@Controller
public class PageController {
	@Autowired
	private SysLogDao sd;
	@RequestMapping("index")
	public String doIndexUI() {
		return "starter";
	}
	@RequestMapping("page")
	public String doPageUI() {
		return "common/page";
	}
	@RequestMapping("doLoginUI")
	public String doLoginUI() {
		return "login";
	}
//	@RequestMapping("log/log_list")
//	public String doLogUI() {
//		return "sys/log_list";
//	}
//	@RequestMapping("menu/menu_list")
//	public String doMenuUI() {
//		return "sys/menu_list";
//	}
	/**
	 * @PathVariable("xxx")
通过 @PathVariable 可以将URL中占位符参数{xxx}绑定到处理器类的方法形参中@PathVariable(“xxx“) 
	 * @param mUI
	 * @return
	 */
	@RequestMapping("{module}/{moduleUI}")
	public String doObjectUI(@PathVariable String moduleUI) {
		return "sys/"+moduleUI;
	}
}
