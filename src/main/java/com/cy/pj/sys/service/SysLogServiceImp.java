package com.cy.pj.sys.service;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cy.pj.common.exception.ServiceException;
import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.dao.SysLogDao;
import com.cy.pj.sys.entity.SysLog;

@Service
public class SysLogServiceImp implements SysLogService{
	@Autowired
	private SysLogDao sd;
	@Override
	public PageObject<SysLog> findPageObject(String name, Integer pageCurrent) {
		 //1.验证参数合法性
		  //1.1验证pageCurrent的合法性，
		  //不合法抛出IllegalArgumentException异常（无效参数异常）
		  if(pageCurrent==null||pageCurrent<1)
		  throw new IllegalArgumentException("当前页码不正确");
		  //2.基于条件查询总记录数
		  //2.1) 执行查询
		  int rowCount=sd.getRowCount(name);
		  //2.2) 验证查询结果，假如结果为0不再执行如下操作
		  if(rowCount==0)
          throw new ServiceException("系统没有查到对应记录");
		  //3.基于条件查询当前页记录(pageSize定义为2)
		  //3.1)定义pageSize
		  int pageSize=3;
		  //3.2)计算startIndex
		  int startIndex=(pageCurrent-1)*pageSize;
		  //3.3)执行当前数据的查询操作
		  List<SysLog> records=
		  sd.findPageObject(name, startIndex, pageSize);
		  //4.对分页信息以及当前页记录进行封装
		  //4.1)构建PageObject对象
		  PageObject<SysLog> pageObject=new PageObject<>();
		  //4.2)封装数据
		  pageObject.setPageCurrent(pageCurrent);
		  pageObject.setPageSize(pageSize);
		  pageObject.setRowCount(rowCount);
		  pageObject.setRecords(records);
          pageObject.setPageCount((rowCount-1)/pageSize+1);
		return pageObject;
	}
	@Override
	public int deleteObjectById(Integer... ids) {
		if (ids==null||ids.length==0)
			throw new IllegalArgumentException("输入错误，请重输");
		int rows;
		try {
			rows = sd.deleteObjectById(ids);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServiceException("系统被李七夜杀了，正在恢复");
		}
		if (rows==0)
			throw new ServiceException("记录不在了");
		return rows;
	}
	@Async
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@Override
	public void insertObject(SysLog sl) {
		try {
			System.out.println("线程名："+Thread.currentThread().getName());
			sd.insertObject(sl);
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
