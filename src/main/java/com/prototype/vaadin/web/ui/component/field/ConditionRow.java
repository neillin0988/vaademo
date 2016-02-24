package com.prototype.vaadin.web.ui.component.field;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;

import com.prototype.vaadin.core.Context.DateFormat;
import com.prototype.vaadin.core.util.db.Condition;
import com.prototype.vaadin.core.util.db.Condition.ConditionOper;
import com.prototype.vaadin.core.util.db.EntityUtils;
import com.prototype.vaadin.core.util.db.Restriction;
import com.prototype.vaadin.core.util.db.Restriction.RestrictionType;
import com.prototype.vaadin.core.util.db.model.ColumnInfo;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class ConditionRow extends HorizontalLayout {
	
	private Class<?> entity;
	private AbstractOrderedLayout conditionDiv;
	private ConditionRow self;
	private ComboBox andOr;
	private ComboBox column;
	private ComboBox restriction;
	private HorizontalLayout valueDiv;
	private Button inBtn;

	private Map<String, ColumnInfo> columnInfoMp;
	
	@SuppressWarnings("rawtypes")
	public Restriction getRestriction() {
		if (restriction.getValue() == null) {
			return null;
		}
		
		RestrictionType restrictionType = (RestrictionType) restriction.getValue();
		String columnName = String.valueOf(column.getValue());
		
		// add value
		int cnt = valueDiv.getComponentCount();
		Object[] valueArr = new Object[cnt]; 
		for (int i = 0; i < cnt; i++) {
			AbstractField field = (AbstractField) valueDiv.getComponent(i);
			
			// convert String to type
			Object value = field.getValue();
			ColumnInfo columnInfo = (ColumnInfo) column.getValue();
			Class<?> type = columnInfo.getType();
			
			if (value == null || value.toString().isEmpty()) {
				return null;
			}
			
			valueArr[i] = ConvertUtils.convert(value, type);;
		}
		
		return Restriction.create(restrictionType, columnName, valueArr);
	}
	
	public Condition.ConditionOper getConditionOper() {
		ConditionOper conditionOper = Condition.ConditionOper.valueOf(String.valueOf(andOr.getValue()));
		return conditionOper;
	}

	public HorizontalLayout create() {
		self = this;
		columnInfoMp = EntityUtils.getColumnsInfoMp(entity);
		
		valueDiv = new HorizontalLayout();
		
		andOr = createAndOr();
		column = createColumn();
		restriction = createRestriction();
		inBtn = new Button("", FontAwesome.PLUS);
		Button cancelBtn = new Button("cancel");
		
		self.addComponent(andOr);
		self.addComponent(column);
		self.addComponent(restriction);
		self.addComponent(inBtn);
		self.addComponent(valueDiv);
		self.addComponent(cancelBtn);
		
		inBtn.setVisible(false);
		
		cancelBtn.setIcon(FontAwesome.TIMES);
		cancelBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				conditionDiv.removeComponent(self);
			}
		});
		
		inBtn.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				addValueField();
			}
		});
		
		column.addValueChangeListener(valuechange());
		restriction.addValueChangeListener(valuechange());
		
		return self;
	}

	private ValueChangeListener valuechange() {
		return new ValueChangeListener() {
			@Override
			public void valueChange(ValueChangeEvent event) {
				valueDiv.removeAllComponents();
				inBtn.setVisible(false);
				
				RestrictionType restrictionType = null;
				if (column.getValue() == null || restriction.getValue() == null) {
					return ;
				}
				
				restrictionType = (RestrictionType) restriction.getValue();
				
				switch (restrictionType) {
				case BETWEEN:
					addValueField();
					addValueField();
					break;
				case IN:
					inBtn.setVisible(true);
					addValueField();
					break;
				case IS_NULL:
				case NOT_NULL:
					break;
				default :
					addValueField();
					break;
				}
			}
		};
	}
	
	protected void addValueField() {
		ColumnInfo columnInfo = null;
		columnInfo = (ColumnInfo) column.getValue();
		
		Class<?> type = columnInfo.getType();
		if (type == String.class) {
			TextField valueField = new TextField();
			valueDiv.addComponent(valueField);
		} else if (type == Date.class) {
			DateField valueField = new DateField();
			valueField.setDateFormat(DateFormat.DATE_ONLY.getFormat());
			valueDiv.addComponent(valueField);
		} else {
			// 數字
			TextField valueField = new TextField();
			valueDiv.addComponent(valueField);
		}
	}
	
	/**
	 * 
	 * @return
	 */
	private ComboBox createRestriction() {
		ComboBox restriction = createComboBox();
		
		for (RestrictionType type : Arrays.asList(Restriction.RestrictionType.values())) {
			restriction.addItem(type);
		}

		restriction.setPageLength(restriction.size());
		
		return restriction;
	}

	/**
	 * 
	 * @return
	 */
	private ComboBox createColumn() {
		ComboBox column = createComboBox();
		
		for (ColumnInfo info : columnInfoMp.values()) {
			column.addItem(info);
		}
		
		return column;
	}

	/**
	 * 
	 * @return
	 */
	private ComboBox createAndOr() {
		ComboBox andOr = createComboBox();
		andOr.addItem(ConditionOper.AND.getValue());
		andOr.addItem(ConditionOper.OR.getValue());		
		andOr.setValue(ConditionOper.AND.getValue());
		andOr.setWidth("60");
		
		return andOr;
	}
	
	private ComboBox createComboBox() {
		ComboBox comboBox = new ComboBox();
		comboBox.setInvalidAllowed(false);
		comboBox.setNullSelectionAllowed(false);
		
		return comboBox;
	}

	public Class<?> getEntity() {
		return entity;
	}

	public void setEntity(Class<?> entity) {
		this.entity = entity;
	}

	public AbstractOrderedLayout getConditionDiv() {
		return conditionDiv;
	}

	public void setConditionDiv(AbstractOrderedLayout conditionDiv) {
		this.conditionDiv = conditionDiv;
	}

}
