package com.cy;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.sys.dao.SysMenuDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysMenuDaoTests {
	@Autowired
	private SysMenuDao smd;
	
	@Test
	public void test1() {
		List<Map<String, Object>> list = smd.findObjects();
		for (Map<String, Object> map : list) {			
			System.out.println(map);
		}
	}
}
