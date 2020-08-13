package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SysRoleMenuDao {
	/**
	 * 删除中间关系表信息
	 * @param menuId
	 * @return
	 */
	int deleteObjectsById(Integer menuId);
	int deleteObjectsByRoleId(Integer roleId);
	int insertObject(@Param("roleId")Integer roleId,
			@Param("menuIds")Integer[] menuIds);
	List<Integer> findMenuIdsByRoleIds(@Param("roleIds") Integer[] roleIds);
}
			