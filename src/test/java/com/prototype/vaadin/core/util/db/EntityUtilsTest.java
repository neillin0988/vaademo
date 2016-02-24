package com.prototype.vaadin.core.util.db;

import static org.junit.Assert.fail;

import java.util.Map;

import org.junit.Test;

import com.prototype.vaadin.core.entity.BlrsFlowSetting;
import com.prototype.vaadin.core.util.db.model.ColumnInfo;

public class EntityUtilsTest {

	@Test
	public void testGetTableShema() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColumns() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetColumnsType() {
		Map<String, ColumnInfo> columnInfoMp = EntityUtils.getColumnsInfoMp(BlrsFlowSetting.class);
		
		for (ColumnInfo info : columnInfoMp.values()) {
			System.out.print(info.getName() + " ");
			System.out.println(info.getType());
		}
	}

	@Test
	public void testGetValues() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetValuesWithoutId() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetId() {
		fail("Not yet implemented");
	}

}
