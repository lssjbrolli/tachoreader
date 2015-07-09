package com.thingtrack.workbench.view;

import com.thingtrack.workbench.WorkbenchNavigator;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;

/*
 * Dashboard MainView is a simple HorizontalLayout that wraps the menu on the
 * left and creates a simple container for the navigator on the right.
 */
@SuppressWarnings("serial")
public class MainView extends HorizontalLayout {

    public MainView() {
        setSizeFull();
        addStyleName("mainview");

        addComponent(new DashboardMenu());

        ComponentContainer content = new CssLayout();
        content.addStyleName("view-content");
        content.addStyleName("v-scrollable");
        content.setSizeFull();        
        addComponent(content);
        setExpandRatio(content, 1.0f);

        new WorkbenchNavigator(content);
    }
}
