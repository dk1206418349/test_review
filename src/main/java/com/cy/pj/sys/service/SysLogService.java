package com.cy.pj.sys.service;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;

public interface SysLogService {
	PageObject<SysLog> findPageObject(String name,Integer pageCurrent);
	int deleteObjectById(Integer ...ids);
	void insertObject(SysLog sl);
}
