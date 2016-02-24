package com.prototype.vaadin.web.ui.component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public abstract class FunctionBasicView extends Panel {
	
	@SuppressWarnings("rawtypes")
	private Map<String, AbstractField> bindItemMp = new HashMap<String, AbstractField>();

    protected VerticalLayout layout = new VerticalLayout();
    
	@PostConstruct
	public void init() {
		layout.setSizeFull();
		layout.setMargin(true);
		
    	setContent(layout);

    	createFunctionLayout();
	}

	protected abstract void createFunctionLayout();
	
	@SuppressWarnings("rawtypes")
	public void addComponent(String key, AbstractField item) {
		bindItemMp.put(key, item);
	}

	public Map<String, Object> getValueMp() {
		Map<String, Object> result = new HashMap<String, Object>();
		for (String name : bindItemMp.keySet()) {
			result.put(name, bindItemMp.get(name).getValue());
		}
		return result;
	}
	
	public Object getValue(String bindName) {
		return bindItemMp.get(bindName).getValue();
	}
	
}