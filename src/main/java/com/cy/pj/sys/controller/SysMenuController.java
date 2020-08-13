package com.cy.pj.sys.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;
@RequestMapping("menu/")
@Controller
public class SysMenuController {
	@Autowired
	private SysMenuService sms;
	@RequestMapping("dofindObjects")
	@ResponseBody
	public JsonResult dofindObjects(){
		List<Map<String, Object>> list = sms.findObjects();
		return new JsonResult(list);
	}
	@RequestMapping("doDeleteObject")
	@ResponseBody
	public JsonResult doDeleteObject(Integer id) {
		int rows = sms.deleteObject(id);
		return new JsonResult("成功删除"+rows+"条");
	}
	@RequestMapping("doFindZtreeMenuNodes")
	@ResponseBody
	public JsonResult doFindZtreeMenuNodes() {
		return new JsonResult(sms.findZtreeMenuNodes());
	}
	@RequestMapping("doSaveObject")
	@ResponseBody
	public JsonResult doInsertObject(SysMenu sm) {
		int insertObject = sms.insertObject(sm);
		
		return new JsonResult("成功添加"+insertObject+"条菜单");
	}
	@RequestMapping("doUpdateObject")
	@ResponseBody
	public JsonResult doUpdateObject(SysMenu sm) {
		sms.updateObject(sm);
		return new JsonResult("修改成功");
	}
}
