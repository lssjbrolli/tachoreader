package com.thingtrack.workbench.view;

import com.google.common.eventbus.Subscribe;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.workbench.WorkbenchUI;
import com.thingtrack.workbench.component.AbstractI18NCustomComponent;
import com.thingtrack.workbench.component.ProfilePreferencesWindow;
import com.thingtrack.workbench.event.DashboardEventBus;
import com.thingtrack.workbench.event.DashboardEvent.NotificationsCountUpdatedEvent;
import com.thingtrack.workbench.event.DashboardEvent.PostViewChangeEvent;
import com.thingtrack.workbench.event.DashboardEvent.ProfileUpdatedEvent;
import com.thingtrack.workbench.event.DashboardEvent.ReportsCountUpdatedEvent;
import com.thingtrack.workbench.event.DashboardEvent.UserLoggedOutEvent;
import com.vaadin.event.dd.DragAndDropEvent;
import com.vaadin.event.dd.DropHandler;
import com.vaadin.event.dd.acceptcriteria.AcceptCriterion;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractSelect.AcceptItem;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DragAndDropWrapper;
import com.vaadin.ui.DragAndDropWrapper.DragStartMode;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

/**
 * A responsive menu component providing user information and the controls for
 * primary navigation between the views.
 */
@SuppressWarnings({ "serial" })
public final class DashboardMenu extends AbstractI18NCustomComponent {
	
    public static final String ID = "dashboard-menu";
    public static final String REPORTS_BADGE_ID = "dashboard-menu-reports-badge";
    public static final String NOTIFICATIONS_BADGE_ID = "dashboard-menu-notifications-badge";
    private static final String STYLE_VISIBLE = "valo-menu-visible";
    
    private MenuBar settings;
    
    private Label notificationsBadge;
    private Label reportsBadge;
    private MenuItem settingsItem;

	private static final int MENU_USER = 0;
	private static final int MENU_NOTE = 1;
	private static final int MENU_LOGOUT = 3;
	
    public DashboardMenu() {
        setPrimaryStyleName("valo-menu");
        setId(ID);
        setSizeUndefined();

        // There's only one DashboardMenu per UI so this doesn't need to be
        // unregistered from the UI-scoped DashboardEventBus.
        DashboardEventBus.register(this);

        setCompositionRoot(buildContent());
    }

    private Component buildContent() {
        final CssLayout menuContent = new CssLayout();
        menuContent.addStyleName("sidebar");
        menuContent.addStyleName(ValoTheme.MENU_PART);
        menuContent.addStyleName("no-vertical-drag-hints");
        menuContent.addStyleName("no-horizontal-drag-hints");
        menuContent.setWidth(null);
        menuContent.setHeight("100%");

        menuContent.addComponent(buildTitle());
        menuContent.addComponent(buildUserMenu());
        menuContent.addComponent(buildToggleButton());
        menuContent.addComponent(buildMenuItems());

        return menuContent;
    }

    private Component buildTitle() {
        Label logo = new Label("TachoDriver <strong>Workbench</strong>", ContentMode.HTML);
        logo.setSizeUndefined();
        HorizontalLayout logoWrapper = new HorizontalLayout(logo);
        logoWrapper.setComponentAlignment(logo, Alignment.MIDDLE_CENTER);
        logoWrapper.addStyleName("valo-menu-title");
        
        return logoWrapper;
    }

    private User getCurrentUser() {
    	return WorkbenchUI.getCurrent().getUser();
    }

    private Component buildUserMenu() {
        settings = new MenuBar();
        settings.setHtmlContentAllowed(true);
        settings.addStyleName("user-menu");
        settingsItem = settings.addItem("", new ThemeResource("img/profile-pic-300px.jpg"), null);
        updateUserName(null);
        settingsItem.addItem("Edit Profile", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(null, getI18N(), WorkbenchUI.getCurrent().getUser(), false);
            }
        });
        settingsItem.addItem("Preferences", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                ProfilePreferencesWindow.open(null, getI18N(), WorkbenchUI.getCurrent().getUser(), true);
            }
        });
        settingsItem.addSeparator();
        settingsItem.addItem("Sign Out", new Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                DashboardEventBus.post(new UserLoggedOutEvent());
            }
        });
        
        return settings;
    }

    private Component buildToggleButton() {
        Button valoMenuToggleButton = new Button("Menu", new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (getCompositionRoot().getStyleName().contains(STYLE_VISIBLE)) {
                    getCompositionRoot().removeStyleName(STYLE_VISIBLE);
                } else {
                    getCompositionRoot().addStyleName(STYLE_VISIBLE);
                }
            }
        });
        
        valoMenuToggleButton.setIcon(FontAwesome.LIST);
        valoMenuToggleButton.addStyleName("valo-menu-toggle");
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_BORDERLESS);
        valoMenuToggleButton.addStyleName(ValoTheme.BUTTON_SMALL);
        
        return valoMenuToggleButton;
    }

    private Component buildMenuItems() {
        CssLayout menuItemsLayout = new CssLayout();
        menuItemsLayout.addStyleName("valo-menuitems");
        menuItemsLayout.setHeight(100.0f, Unit.PERCENTAGE);

        for (final DashboardViewType view : DashboardViewType.values()) {
            Component menuItemComponent = new ValoMenuItemButton(view);

            if (view == DashboardViewType.REPORTS) {
                // Add drop target to reports button
                DragAndDropWrapper reports = new DragAndDropWrapper(menuItemComponent);
                reports.setSizeUndefined();
                reports.setDragStartMode(DragStartMode.NONE);
                reports.setDropHandler(new DropHandler() {

                    @Override
                    public void drop(final DragAndDropEvent event) {
                        UI.getCurrent().getNavigator().navigateTo(DashboardViewType.REPORTS.getViewName());
                    }

                    @Override
                    public AcceptCriterion getAcceptCriterion() {
                        return AcceptItem.ALL;
                    }

                });
                
                menuItemComponent = reports;
            }

            if (view == DashboardViewType.DASHBOARD) {
                notificationsBadge = new Label();
                notificationsBadge.setId(NOTIFICATIONS_BADGE_ID);
                menuItemComponent = buildBadgeWrapper(menuItemComponent, notificationsBadge);
            }
            if (view == DashboardViewType.REPORTS) {
                reportsBadge = new Label();
                reportsBadge.setId(REPORTS_BADGE_ID);
                menuItemComponent = buildBadgeWrapper(menuItemComponent, reportsBadge);
            }

            menuItemsLayout.addComponent(menuItemComponent);
        }
        return menuItemsLayout;

    }

    private Component buildBadgeWrapper(final Component menuItemButton, final Component badgeLabel) {
        CssLayout dashboardWrapper = new CssLayout(menuItemButton);
        dashboardWrapper.addStyleName("badgewrapper");
        dashboardWrapper.addStyleName(ValoTheme.MENU_ITEM);
        badgeLabel.addStyleName(ValoTheme.MENU_BADGE);
        badgeLabel.setWidthUndefined();
        badgeLabel.setVisible(false);
        dashboardWrapper.addComponent(badgeLabel);
        
        return dashboardWrapper;
    }

    @Override
    public void attach() {
        super.attach();
        updateNotificationsCount(null);
    }

    @Subscribe
    public void postViewChange(final PostViewChangeEvent event) {
        // After a successful view change the menu can be hidden in mobile view.
        getCompositionRoot().removeStyleName(STYLE_VISIBLE);
    }

    @Subscribe
    public void updateNotificationsCount(final NotificationsCountUpdatedEvent event) {
        /*int unreadNotificationsCount = WorkbenchUI.getDataProvider().getUnreadNotificationsCount();
        notificationsBadge.setValue(String.valueOf(unreadNotificationsCount));
        notificationsBadge.setVisible(unreadNotificationsCount > 0);*/
    }

    @Subscribe
    public void updateReportsCount(final ReportsCountUpdatedEvent event) {
        reportsBadge.setValue(String.valueOf(event.getCount()));
        reportsBadge.setVisible(event.getCount() > 0);
    }

    @Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) {
        if (event != null)
        	settingsItem.setText(event.getUser().getAgent().getName() + "<BR>[" + event.getUser().getOrganizationDefault().getName() + "]");
        else
        	settingsItem.setText(getCurrentUser().getAgent().getName() + "<BR>[" + getCurrentUser().getOrganizationDefault().getName() + "]");
    }

    public final class ValoMenuItemButton extends Button {

        private static final String STYLE_SELECTED = "selected";

        private final DashboardViewType view;

        public ValoMenuItemButton(final DashboardViewType view) {
            this.view = view;
            
            setPrimaryStyleName("valo-menu-item");
            setIcon(view.getIcon());
            //setCaption(view.getViewName().substring(0, 1).toUpperCase() + view.getViewName().substring(1));
            setCaption(getI18N().getMessage("com.thingtrack.workbench.view.DashboardMenu.menu." + view.getViewName()));
            
            DashboardEventBus.register(this);
            
            addClickListener(new ClickListener() {
                @Override
                public void buttonClick(final ClickEvent event) {
                    UI.getCurrent().getNavigator().navigateTo(view.getViewName());
                }
            });

        }

        @Subscribe
        public void postViewChange(final PostViewChangeEvent event) {
            removeStyleName(STYLE_SELECTED);
            
            if (event.getView() == view)
                addStyleName(STYLE_SELECTED);            
        }
    }

	@Override
	protected void updateLabels() {
		settingsItem.getChildren().get(MENU_USER).setText(getI18N().getMessage("com.thingtrack.workbench.view.DashboardMenu.menu.profile.user.text"));
		settingsItem.getChildren().get(MENU_NOTE).setText(getI18N().getMessage("com.thingtrack.workbench.view.DashboardMenu.menu.profile.note.text"));
		settingsItem.getChildren().get(MENU_LOGOUT).setText(getI18N().getMessage("com.thingtrack.workbench.view.DashboardMenu.menu.profile.logout.text"));
	}
}
