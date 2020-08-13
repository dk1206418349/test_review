package com.cy.pj.sys.dao;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.common.vo.Node;
import com.cy.pj.sys.entity.SysMenu;

/**
 * @mapper注解
 * 对象的实现类由系统底层自动创建，
 * 并且会在此接口的实现类注入sqlsessionfactory对象
 */
@Mapper
public interface SysMenuDao {
	/**
	 * 一行记录一个map
	 * 在很多产品级应用不推荐使用map，：
	 * 1可读性差，
	 * 2值的类型不可控
	 * 但为了提高开发速度，会用到map
	 * @return
	 */
	List<Map<String, Object>>findObjects();
	/**
	 * 基于菜单id查询子菜单数量
	 * @param id
	 * @return
	 */
	int getChildCount(Integer id);
	/**
	 * 基于菜单id删除菜单
	 * @param id
	 * @return
	 */
	int deleteObject(Integer id);
	int insertObject(SysMenu sm);
	/**
	 *显示树结构
	 * @return
	 */
	List<Node>findZtreeMenuNodes();
	int updateObject(SysMenu sm);
	List<String>findPermissions(@Param("menuIds")Integer[] menuIds);
}
