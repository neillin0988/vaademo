package com.prototype.vaadin.web.ui.theme;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.prototype.vaadin.core.Context.Message;
import com.prototype.vaadin.core.busniess.facade.SystemFacade;
import com.prototype.vaadin.core.util.ExceptionUtils;
import com.prototype.vaadin.web.model.MenuPojo;
import com.prototype.vaadin.web.ui.component.BasicView;
import com.prototype.vaadin.web.ui.component.window.Dialog;
import com.prototype.vaadin.web.util.UIUtils;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@org.springframework.stereotype.Component
@Scope("prototype")
public class MainView extends BasicView {

	@Autowired
	SystemFacade systemFacade;

    @PostConstruct
    public void postConstruct() {
    	try {
    		getBodyLayout().addComponent(createMenuContent());
    	} catch (Exception e){
    		  // Create a sub-window and set the content
	        String title = Message.system_error.getValue();
	        String context = ExceptionUtils.parseException(e);
	        
	        // Open it in the UI
	        
	        UI.getCurrent().addWindow(new Dialog(title, context));
    	}
	}

	/**
     * body layout
     * 
     * @return
     * @throws Exception
     */
    private Component createMenuContent() throws Exception {
		HorizontalSplitPanel bodyLayout = new HorizontalSplitPanel();
		Tree menu = new Tree();
		Panel menuPanel = new Panel();
		VerticalLayout contentPanel = new VerticalLayout();

		TabSheet tabSheet = new TabSheet();
		tabSheet.addStyleName(Reindeer.TABSHEET_HOVER_CLOSABLE);

		contentPanel.addComponent(tabSheet);

		bodyLayout.setSizeFull();
		bodyLayout.setFirstComponent(menuPanel);
		bodyLayout.setSecondComponent(contentPanel);
		// Set the position of the splitter as percentage
		bodyLayout.setSplitPosition(15, Unit.PERCENTAGE);

		menuPanel.setContent(menu);
		menuPanel.setSizeFull();
		// Have a vertical layout in the Details panel.
		contentPanel.setSizeFull();

		// ////////////////////////////////////////////////////
		// get menu
		List<MenuPojo> menuPojoLs = null;
		menuPojoLs = systemFacade.getMenuPojoLs();
		
		// set menu
		setMenuView(menu, menuPojoLs, null);

		setMenuViewEvent(menu, contentPanel, tabSheet);
		
		return bodyLayout;
	}

	private void setMenuViewEvent(final Tree menu, VerticalLayout contentPanel,
			final TabSheet tabSheet) {
		
		menu.addValueChangeListener(new Property.ValueChangeListener() {
			public void valueChange(ValueChangeEvent event) {
				if (event.getProperty() != null && event.getProperty().getValue() != null) {
					MenuPojo menuPojo = (MenuPojo) event.getProperty().getValue();
					
					Component component = null;
					
					try {
						component = menuPojo.getComponent();	
					} catch (Exception e) {
						e.printStackTrace();
				        UIUtils.showException(e);
					}
					
					if (component != null) {
						Tab tab = tabSheet.getTab(component);

						// 若 tab 不存在則加入
						if (tab == null) {
							tab = tabSheet.addTab(component, menuPojo.getName());
							tab.setClosable(true);
						}
						
						// 選取該 tab
						tabSheet.setSelectedTab(tab);	
					} else {
						// 展開
						menu.expandItem(menuPojo);
					}
				}
			}
		});
		
		menu.setImmediate(true);
	}

	// =================================================================
	/**
	 * set menu
	 * 
	 * @param menu
	 * @param menuPojoLs
	 * @param parent
	 */
	private void setMenuView(Tree menu, List<MenuPojo> menuPojoLs, MenuPojo parent) {
		for (MenuPojo menuPojo : menuPojoLs) {
			menu.addItem(menuPojo);
			
			if (parent != null) {
				// Set it to be a child.
				menu.setParent(menuPojo, parent);
			}

			if (menuPojo.hasSubMenu()) {
				setMenuView(menu, menuPojo.getSubMenuLs(), menuPojo);
			} else {
				// The planet has no moons so make it a leaf.
				menu.setChildrenAllowed(menuPojo, false);
			}
		}
	}

}