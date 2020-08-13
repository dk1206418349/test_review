package com.cy;

import java.util.Map;
import java.util.Properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.junit4.SpringRunner;

import com.cy.pj.common.vo.PageObject;
import com.cy.pj.sys.entity.SysLog;
import com.cy.pj.sys.service.SysLogService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysLogServiceTests {
	@Autowired
	private SysLogService ss;
	@Test
	public void test01() {
//		PageObject<SysLog> list = ss.findPageObject("admin", 4);
//		System.out.println(list);
//		int rows = ss.deleteObjectById(2);
//		System.out.println(rows);
	System.err.append("鸡你太美");
	//System.err.println("666");
	//String property = System.getProperty("evn");
	//System.out.println(property);
	Map<String, String> getenv = System.getenv();
	Properties properties = System.getProperties();
	Object object = properties.getProperty("user.name");
	String object2 = properties.getProperty("user.home");
	Object object3 = properties.getProperty("user.dir");
	System.out.println(object);
	System.out.println(object2);
	System.out.println(object3);
	try {
		Class<?>clazz=Class.forName("com.cy.SysLogServiceTests");
		String split = clazz.getClass().getResource("/").getFile();
		System.out.println(split);
	} catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		System.out.println(System.getProperty("version"));
	}
}
