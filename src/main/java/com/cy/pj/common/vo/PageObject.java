package com.cy.pj.common.vo;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
/**
 * 值对象
 * @author Administrator
 *
 * @param <T>
 */
@Data
public class PageObject<T>implements Serializable {
	private static final long serialVersionUID = 1536631092048817285L;
	//当前页的页码值
	private Integer pageCurrent=1;
	//页面显示记录数
	private Integer pageSize=0;
	//总行数
	private Integer rowCount=0;
	//当前页记录，封装syslog对象
	private List<T> records;
	//总页数
	private Integer pageCount;
	//pagecount=(rowcount-1)/pagesize+1
}
