package com.prototype.vaadin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class SpringTest extends BasicTest {
	
	public ApplicationContext ctx;
	
	@Before
	public void before() {
		ctx = new FileSystemXmlApplicationContext("classpath*:spring/applicationContext.xml");
		super.before();
		// 載入 spring 設定
	}
	
	@Test
	public void test() {
		System.out.println(ctx.getBean("postgres"));
	}
	
	@After
	public void after() {
		super.after();
	}
	
}
