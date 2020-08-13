package com.cy.pj.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.dao.SysMenuDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.entity.SysMenu;
@Service
public class SysMenuServiceImp implements SysMenuService{
	@Autowired
	private SysMenuDao smd;
	@Autowired
	private SysRoleMenuDao srmd;
	@Override
	public List<Map<String, Object>> findObjects() {
		List<Map<String, Object>> list = smd.findObjects();
		if (list==null||list.size()==0) 
		throw new ServiceException("找不到你要的菜单");
		return list;
	}
	@Override
	public int deleteObject(Integer id) {
		//分析id有效性
		if (id==null||id<=0)
			throw new IllegalArgumentException("请选择");
		//获取子菜单数量，基于此判断
		int childCount = smd.getChildCount(id);
		if (childCount>0)
			throw new ServiceException("请先删除子菜单");
		//删除菜单信息并返回结果
		int rows = smd.deleteObject(id);
		if (rows==0) 
			throw new ServiceException("此菜单不存在");
		//删除关系表信息
		srmd.deleteObjectsById(id);
		return rows;
	}
	@Override
	public int insertObject(SysMenu sm) {
		if (sm==null)
			throw new ServiceException("请填写正确信息，保存对象不能为空");
		//判断菜单内容
		if(StringUtils.isEmpty(sm.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows=0;
		try {
		rows = smd.insertObject(sm);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("添加失败");
			// TODO: handle exception
		}
		return rows;
	}
	@Override
	public List<Node> findZtreeMenuNodes() {
		List<Node> list = smd.findZtreeMenuNodes();
		return list;
	}
	@Override
	public int updateObject(SysMenu sm) {
		if (sm==null)
			throw new ServiceException("请填写正确信息，保存对象不能为空");
		//判断菜单内容
		if(StringUtils.isEmpty(sm.getName()))
			throw new ServiceException("菜单名不能为空");
		int rows=0;
		try {
		rows = smd.updateObject(sm);
		}catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException("修改失败");
			// TODO: handle exception
		}
		return rows;
	}

}
