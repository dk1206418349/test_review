package com.cy.pj.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cy.pj.sys.entity.SysLog;
@Mapper/**告诉底层该接口是MyBatis映射器的标记接口*/
public interface SysLogDao {
	/*
	 * @param 注解可以描述数据层方法参数，尤其是当方法参数多个时或参数用在动态
	 * SQL语句
	 */
	List<SysLog>findPageObject(@Param("username") String username,
			@Param("startIndex")Integer startIndex,
			@Param("pageSize")Integer pageSize);
	 /**基于条件查询总记录数
	 @param username 查询条件(例如查询哪个用户的日志信息)
	 * @return 总记录数(基于这个结果可以计算总页数)
	 * 说明：假如如下方法没有使用注解修饰，在基于名字进行查询
	 * 时候会出现There is no getter for property named
	 * 'username' in 'class java.lang.String'*/
	int getRowCount(@Param("username")String username);
	int deleteObjectById(@Param("ids")Integer ...ids);
	int insertObject(SysLog sl);
}
