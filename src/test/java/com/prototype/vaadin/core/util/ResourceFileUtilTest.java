package com.prototype.vaadin.core.util;

import org.junit.Test;

import com.prototype.vaadin.BasicTest;

public class ResourceFileUtilTest extends BasicTest {

	@Test
	public void getResource() {
		System.out.println(ResourceFileUtil.SQL.getResource("TEST"));
	}

}
