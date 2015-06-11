package com.thingtrack.workbench.component;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.FormLayout;

@SuppressWarnings("serial")
public class EntityForm<BEANTYPE> extends CustomComponent {
	private FormLayout mainLayout;		
	private FieldGroup binder;
		
	public EntityForm(BEANTYPE entity, CustomComponent layout) {		
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		//mainLayout.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
		
		BeanItem<BEANTYPE> item = new BeanItem<BEANTYPE>(entity);
			
		// set form bean to access from custom component layout
		layout.setData(entity);
		
		// bind user Layout		
		layout.setSizeFull();
		mainLayout.addComponent(layout);
        		
        // Now use a binder to bind the members
        binder = new FieldGroup(item);
        
        // Enable buffering (actually enabled by default)       
        binder.bindMemberFields(layout);
	}
	
	public FieldGroup getBinder() {	
		return this.binder;
	}
	
	private FormLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new FormLayout();
		mainLayout.setImmediate(false);
		mainLayout.setMargin(new MarginInfo(false, true, true, false));
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		return mainLayout;
	}
}
