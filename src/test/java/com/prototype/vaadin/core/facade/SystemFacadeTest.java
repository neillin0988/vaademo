package com.prototype.vaadin.core.facade;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.prototype.vaadin.SpringTest;
import com.prototype.vaadin.core.busniess.facade.SystemFacade;
import com.prototype.vaadin.core.util.JsonUtils;
import com.prototype.vaadin.web.model.MenuPojo;

public class SystemFacadeTest extends SpringTest {

	@Autowired
	SystemFacade systemFacade;

	@Test
	public void testGetMenuPojoLs() throws Exception {
		List<MenuPojo> list = systemFacade.getMenuPojoLs();
		
		System.out.println(JsonUtils.obj2Json(list));
	}

}
