package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.CheckBox;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.vo.SysRoleMenuVo;

@Mapper
public interface SysRoleDao {
	/**
	 * 
	 * @param name角色名
     * @param startIndex 上一页的结束位置
     * @param pageSize 每页要查询的记录数
     * @return
	 */
	List<SysRole>findPageObjects(@Param("name")String name,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	int getRowCount(@Param("name")String name);
	int deleteObject(Integer id);
	int insertObject(SysRole sr);
	/**
	 * 基于角色id查询角色对象和他的菜单ID
	 * @param id
	 * @return
	 */
	SysRoleMenuVo findObjectById(Integer id);
	int updateObject(SysRole sr);
	List<CheckBox>findObjects();
	
}
