package com.prototype.vaadin.web.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.prototype.vaadin.web.util.SpringContextHelper;
import com.vaadin.ui.Component;

/**
 * 前端 menu 元件
 * 
 * @author neil.lin
 *
 */
@SuppressWarnings("serial")
public class MenuPojo implements Serializable {

	private String key;						// menu id
	private String name;					// menu name
	private String parent;					// menu parent
	private String componentId;				// menu class name
	private Component component;			// menu class instance
	private List<MenuPojo> subMenuLs = new ArrayList<MenuPojo>(); // sub menu

	public MenuPojo() {
		super();
	}

	public MenuPojo(String name) {
		super();
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public boolean hasSubMenu() {
		return subMenuLs != null ? subMenuLs.size() > 0 : false;
	}

	public MenuPojo addSubMenu(MenuPojo subMenu) {
		subMenuLs.add(subMenu);
		return this;
	}
	
	public Component getComponent() {
		if (component == null && !StringUtils.isBlank(componentId)) {
			// get instance from spring 
			component = (Component) SpringContextHelper.getBean(componentId);	
		}
				
		return component;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MenuPojo> getSubMenuLs() {
		return subMenuLs;
	}

	public void setSubMenuLs(List<MenuPojo> subMenu) {
		this.subMenuLs = subMenu;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getComponentId() {
		return componentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

}
