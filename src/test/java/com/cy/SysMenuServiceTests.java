package com.cy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.entity.SysMenu;
import com.cy.pj.sys.service.SysMenuService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuServiceTests {
	@Autowired
	private SysMenuService sms;
	@Test
	public void test1() {
		SysMenu sm = new SysMenu();
		sm.setName("角色管理");
		sm.setNote("题都是");
		int rows = sms.insertObject(sm);
		System.out.println(rows);
	}
}
