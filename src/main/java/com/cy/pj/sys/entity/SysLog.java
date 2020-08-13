package com.cy.pj.sys.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
/**
 * pojo：简单java对象
 * po：持久化对象：要求表中字段有映射关系
 * vo： 值对象：负责每层之间传递数据
 * dto：数据之间传递（序列化）
 * @author Administrator
 *
 */
public class SysLog implements Serializable{
	private static final long serialVersionUID = -8799081241453681134L;
	private Integer id;
	private String username;
	private String operation;
	private String method;
	private String params;
	private long time;
	private String ip;
	private Date createdTime;
	
}
