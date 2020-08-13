package com.cy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysRole;
import com.cy.pj.sys.service.SysRoleService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysRoleServiceTests {
	@Autowired
	private SysRoleService srs;
	@Test
	public void test1() {
		PageObject<SysRole> list = srs.findPageObjects("运维经理", 1);
		System.out.println(list);
		
	}
}
