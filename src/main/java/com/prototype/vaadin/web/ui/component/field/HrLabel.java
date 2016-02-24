package com.prototype.vaadin.web.ui.component.field;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

@SuppressWarnings("serial")
public class HrLabel extends Label {

	public HrLabel() {
		super("<hr/>", ContentMode.HTML);
	}
}
