package com.cy.pj.sys.service;

import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysRoleDao;
import com.cy.pj.sys.dao.SysRoleMenuDao;
import com.cy.pj.sys.dao.SysUserRoleDao;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;
@Service
public class SysRoleServiceImp implements SysRoleService{
	//@Autowired
	private SysRoleDao srd;
	//@Autowired
	private SysRoleMenuDao srmd;
	//@Autowired
	private SysUserRoleDao surd;
	/**构造方法注入依赖*/
	@Autowired
	public SysRoleServiceImp(SysRoleDao srd, SysRoleMenuDao srmd, SysUserRoleDao surd) {
		super();
		this.srd = srd;
		this.srmd = srmd;
		this.surd = surd;
	}
	@Override
	public PageObject<SysRole> findPageObjects(String name, Integer pageCurrent) {
		  //1.验证参数合法性
		  //1.1验证pageCurrent的合法性，
		  //不合法抛出IllegalArgumentException异常
		if(pageCurrent==null||pageCurrent<1)
			throw new IllegalArgumentException("当前页码不正确");
		//2.基于条件查询总记录数
		  //2.1) 执行查询
		int rows = srd.getRowCount(name);
		if(rows==0)
			throw new ServiceException("记录不存在");
		//页面大小
		int pageSize=3;
		//总页数
		int pageCount=rows/pageSize;
		//if(pageCount<pageCurrent)
			//throw new ServiceException("页码不能大于总页数");
		int startIndex=(pageCurrent-1)*pageSize;
		List<SysRole> records = srd.findPageObjects(name, startIndex, pageSize);
		  PageObject<SysRole> pageObject=new PageObject<>();
		  //4.2)封装数据
		  pageObject.setPageCurrent(pageCurrent);
		  pageObject.setPageSize(pageSize);
		  pageObject.setRowCount(rows);
		  pageObject.setRecords(records);
          pageObject.setPageCount((rows-1)/pageSize+1);
		return pageObject;
	}
	@Override
	public int deleteObject(Integer id) {
		// 参数校验
		if(id==null||id<1)
			throw new IllegalArgumentException("id值无效");
		srmd.deleteObjectsById(id);
		surd.deleteObjectsByRoleId(id);
		int rows = srd.deleteObject(id);
		if(rows==0)
			throw new ServiceException("数据已经不存在");
		return rows;
	}
	@Override
	public int insertObject(SysRole sr,Integer[]menuIds) {
		//数据合法性验证
		if(sr==null)
			throw new ServiceException("保存数据不能为空");
		if(StringUtils.isEmpty(sr.getName()))
			throw new ServiceException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
			throw new ServiceException("必须为角色赋予权限");
		int insertObject = srd.insertObject(sr);
		srmd.insertObject(sr.getId(), menuIds);
		return insertObject;
	}
	@Override
	public SysRoleMenuVo findObjectById(Integer id) {
		if (id==null||id<0)
			throw new ServiceException("id值不合法");
		SysRoleMenuVo result = srd.findObjectById(id);
		if(result==null)
			throw new ServiceException("此记录已经不存在");
		return result;
	}
	@Override
	public int updateObject(SysRole sr, Integer[] menuIds) {
		if(sr==null)
			throw new ServiceException("更新对象不能为空");
		if(sr.getId()==null)
			throw new ServiceException("id值不能为空");
		if(StringUtils.isEmpty(sr.getName()))
		throw new ServiceException("角色名不能为空");
		if(menuIds==null||menuIds.length==0)
		throw new ServiceException("必须为角色指定一个权限");
		int rows = srd.updateObject(sr);
		if(rows==0)
			throw new ServiceException("对象已经不存在");
		//删除原有菜单关系数据
		srmd.deleteObjectsByRoleId(sr.getId());
		//插入新的关系数据
		srmd.insertObject(sr.getId(), menuIds);
		return rows;
	}
	@Override
	public List<CheckBox> findObjects() {
		return srd.findObjects();
	}

}
