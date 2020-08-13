package com.cy.pj.sys.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cy.pj.common.vo.JsonResult;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;
import com.cy.pj.sys.vo.SysRoleMenuVo;

@RestController
@RequestMapping("role/")
public class SysRoleController {
	@Autowired
	private SysRoleService srs;
	@RequestMapping("doFindPageObjects")
	public JsonResult dofindPageObjects(String name,Integer pageCurrent) {
		PageObject<SysRole> list = srs.findPageObjects(name, pageCurrent);
		return new JsonResult(list);
	}
	@RequestMapping("doDeleteObject")
	public JsonResult dodeleteObject(Integer id) {
		int rows = srs.deleteObject(id);
		return new JsonResult("成功删除"+rows+"条数据");
	}
	@RequestMapping("doInsertObject")
	public JsonResult doInsertObject(SysRole sr,Integer[]menuIds) {
		int rows = srs.insertObject(sr, menuIds);
		return new JsonResult("成功添加"+rows+"条数据");
	}
	@RequestMapping("doFindObjectById")
	public JsonResult doFindObjectById(Integer id) {
		SysRoleMenuVo findObjectById = srs.findObjectById(id);
		return new JsonResult(findObjectById);
	}
	@RequestMapping("doUpdateObject")
	public JsonResult doUpdateObject(SysRole sr,Integer[]menuIds) {
		srs.updateObject(sr, menuIds);
		return new JsonResult("修改成功");
	}
	@RequestMapping("doFindRoles")
	public JsonResult doFindObjects() {
		return new JsonResult(srs.findObjects());
	}
}
