package com.cy.pj.sys.service;

import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
//import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.annotation.Dark;
import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysUserDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysUser;
import com.cy.pj.sys.vo.SysUserDeptVo;
//@Transactional(//isolation = Isolation.READ_COMMITTED,
//timeout = 30,propagation =Propagation.REQUIRED,readOnly = false )
@Service
public class SysUserServiceImp implements SysUserService{
	@Autowired
	private SysUserDao sud;
	@Autowired
	private SysUserRoleDao surd;
	@Override
	public int updatePassword(String password, String newPassword, String cfgPassword) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public Map<String, Object> findObjectById(Integer id) {
		if(id==null||id<=0)
			throw new ServiceException("参数不合法");
		SysUserDeptVo user = sud.findObjectById(id);
		if(user==null)
			throw new ServiceException("此用户不存在");
		List<Integer> roleIds = surd.findRoleIdsByUserId(id);
		Map<String, Object> map = new HashMap<>();
		map.put("user", user);
		map.put("roleIds", roleIds);
		return map;
	}
	//@CacheEvict(value = "userCache",key = "#entity")
	@Dark("修改用户")
	@Override
	public int updateObject(SysUser entity, Integer[] roleIds) {
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new ServiceException("至少为用户分配角色");
		int rows = sud.updateObject(entity);
		surd.deleteObjectByUserId(entity.getId());
		surd.insertObjects(entity.getId(), roleIds);
		if(rows==0)
			throw new ServiceException("失败");
		return rows;
	}
	@Dark("添加用户")
	@Override
	public int saveObject(SysUser entity, Integer[] roleIds) {
		if(entity==null)
			throw new ServiceException("保存对象不能为空");
		if(StringUtils.isEmpty(entity.getUsername()))
			throw new ServiceException("用户名不能为空");
		if(StringUtils.isEmpty(entity.getPassword()))
			throw new ServiceException("密码不能为空");
		if(roleIds==null||roleIds.length==0)
			throw new ServiceException("至少为用户分配角色");
		//加密
		String salt = UUID.randomUUID().toString();
		//shiro框架方法
		//algorithmName 算法         原密码，盐值，加密次数
		SimpleHash sHash = new SimpleHash("MD5", entity.getPassword(), salt, 1);
		entity.setPassword(sHash.toHex());
		entity.setSalt(salt);
		int rows=0;
		rows=sud.insertObject(entity);
		surd.insertObjects(entity.getId(), roleIds);
		return rows;
	}
	@RequiresPermissions("sys:list")
	//@Transactional
	@Dark("禁用启用设置")
	@Override
	public int validById(Integer id, Integer valid, String modifiedUser) {
		if(id==null||id<=0)
			throw new ServiceException("参数不合法");
		if(valid!=1&&valid!=0)
			throw new ServiceException("参数不合法");
		if(StringUtils.isEmpty(modifiedUser))
			throw new ServiceException("修改用户不能为空");
		int rows=0;
		try {
			rows = sud.validById(id, valid, modifiedUser);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ServiceException("底层正在维护");
		}
		if(rows==0)
			throw new ServiceException("此记录已经不存在");
		return rows;
	}
	@Dark("分页查询")
	@Override
	public PageObject<SysUserDeptVo> findPageObjects(String username, Integer pageCurrent) {
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("页码不正确");
		int rows = sud.getRowCount(username);
		if (rows==0) 
			throw new ServiceException("记录不存在");
		int pageSize=3;
		int startIndex=pageSize*(pageCurrent-1);
		List<SysUserDeptVo> list = sud.findPageObjects(username, startIndex, pageSize);
		PageObject<SysUserDeptVo> pageObject = new PageObject<>();
		pageObject.setRecords(list);
		pageObject.setPageCount((rows-1)/pageSize+1);
		pageObject.setPageCurrent(pageCurrent);
		pageObject.setPageSize(pageSize);
		pageObject.setRowCount(rows);
		return pageObject;
	}

}
