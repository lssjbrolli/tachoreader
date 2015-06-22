package com.thingtrack.workbench.view;

import com.thingtrack.workbench.view.administrator.AdministratorView;
import com.thingtrack.workbench.view.dashboard.DashboardView;
import com.thingtrack.workbench.view.drivers.DriverView;
import com.thingtrack.workbench.view.graph.GraphView;
import com.thingtrack.workbench.view.reports.ReportsView;
import com.thingtrack.workbench.view.tacho.TachoView;
import com.thingtrack.workbench.view.vehicle.VehicleView;
import com.vaadin.navigator.View;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;

public enum DashboardViewType {
    DASHBOARD("dashboard", DashboardView.class, FontAwesome.HOME, true),
    ADMINISTRATOR("administrators", AdministratorView.class, FontAwesome.BUILDING_O, false),
    DRIVERS("drivers", DriverView.class, FontAwesome.USERS, false),
    VEHICLES("vehicles", VehicleView.class, FontAwesome.TRUCK, false),
    TACHOMETERS("tachos", TachoView.class, FontAwesome.TACHOMETER, false),
    GRAPHS("graphs", GraphView.class, FontAwesome.BAR_CHART_O, false),
    REPORTS("reports", ReportsView.class, FontAwesome.FILE_TEXT_O, true);

    private final String viewName;
    private final Class<? extends View> viewClass;
    private final Resource icon;
    private final boolean stateful;

    private DashboardViewType(final String viewName,
            final Class<? extends View> viewClass, final Resource icon,
            final boolean stateful) {
        this.viewName = viewName;
        this.viewClass = viewClass;
        this.icon = icon;
        this.stateful = stateful;
    }

    public boolean isStateful() {
        return stateful;
    }

    public String getViewName() {
        return viewName;
    }

    public Class<? extends View> getViewClass() {
        return viewClass;
    }

    public Resource getIcon() {
        return icon;
    }

    public static DashboardViewType getByViewName(final String viewName) {
        DashboardViewType result = null;
        for (DashboardViewType viewType : values()) {
            if (viewType.getViewName().equals(viewName)) {
                result = viewType;
                break;
            }
        }
        return result;
    }

}
