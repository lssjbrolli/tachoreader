package com.thingtrack.workbench.component;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.support.I18NWindow;
import com.thingtrack.tachoreader.domain.Administrator;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.AdministratorService;
import com.thingtrack.tachoreader.service.api.DriverService;
import com.thingtrack.workbench.WorkbenchUI;
import com.thingtrack.workbench.event.DashboardEventBus;
import com.thingtrack.workbench.event.DashboardEvent.CloseOpenWindowsEvent;
import com.thingtrack.workbench.event.DashboardEvent.ProfileUpdatedEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.fieldgroup.PropertyId;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.Position;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ProfilePreferencesWindow extends I18NWindow {	
    /*
     * Fields for editing the User object are defined here as class members.
     * They are later bound to a FieldGroup by calling
     * fieldGroup.bindMemberFields(this). The Fields' values don't need to be
     * explicitly set, calling fieldGroup.setItemDataSource(user) synchronizes
     * the fields with the user object.
     */
    @PropertyId("agent.name")
    private TextField nameField;
    @PropertyId("agent.email")
    private TextField emailField; 
    @PropertyId("organizationDefault")
    private ComboBox organizationDefaultField;    
    @PropertyId("cardNumber")
    private TextField cardNumber;
    @PropertyId("cardExpiryDate")
    private PopupDateField cardExpiryDate;
    @PropertyId("cardHolderBirthDate")
    private PopupDateField cardHolderBirthDate;
    @PropertyId("username")
    private TextField usernameField;
    @PropertyId("password")
    private PasswordField passwordField;
    
    public static final String ID = "profilepreferenceswindow";

    private final BeanFieldGroup<User> fieldGroup;
    	
	private BeanItemContainer<Organization> organizationContainer = new BeanItemContainer<Organization>(Organization.class);
	
	private AdministratorService administratorService;
	private DriverService driverService;
	
    private ProfilePreferencesWindow(String caption, I18N i18n, final User user, final boolean preferencesTabOpen) {
    	super(caption, i18n);
    	
       	// get Services
    	getServices();
    	
        addStyleName("profile-window");
        setId(ID);
        Responsive.makeResponsive(this);
		
        setModal(true);
        setCloseShortcut(KeyCode.ESCAPE, null);
        setResizable(false);
        setClosable(false);
        setHeight(90.0f, Unit.PERCENTAGE);

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setMargin(new MarginInfo(true, false, false, false));
        setContent(content);

        TabSheet detailsWrapper = new TabSheet();
        detailsWrapper.setSizeFull();
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_PADDED_TABBAR);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_ICONS_ON_TOP);
        detailsWrapper.addStyleName(ValoTheme.TABSHEET_CENTERED_TABS);
        content.addComponent(detailsWrapper);
        content.setExpandRatio(detailsWrapper, 1f);

        detailsWrapper.addComponent(buildProfileTab(user));
        detailsWrapper.addComponent(buildPreferencesTab(user));

        if (preferencesTabOpen) {
            detailsWrapper.setSelectedTab(1);
        }

        content.addComponent(buildFooter(user));

        fieldGroup = new BeanFieldGroup<User>(User.class);
        fieldGroup.bindMemberFields(this);
        fieldGroup.setItemDataSource(user);
    }

	private void getServices() {
		this.administratorService = (AdministratorService) WorkbenchUI.getCurrent().getApplicationContext().getBean("administratorService");
		this.driverService = (DriverService) WorkbenchUI.getCurrent().getApplicationContext().getBean("driverService");
	}
	
    private Component buildPreferencesTab(final User user) {
        VerticalLayout root = new VerticalLayout();
        root.setCaption(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.tabPreferences.caption"));
        root.setIcon(FontAwesome.COGS);
        root.setSpacing(true);
        root.setMargin(true);
        root.setSizeFull();

        Label message = new Label("Not implemented in this demo");
        message.setSizeUndefined();
        message.addStyleName(ValoTheme.LABEL_LIGHT);
        root.addComponent(message);
        root.setComponentAlignment(message, Alignment.MIDDLE_CENTER);

        return root;
    }

    private Component buildProfileTab(final User user) {
        HorizontalLayout root = new HorizontalLayout();
        root.setCaption(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.root.caption"));
        root.setIcon(FontAwesome.USER);
        root.setWidth(100.0f, Unit.PERCENTAGE);
        root.setSpacing(true);
        root.setMargin(true);
        root.addStyleName("profile-form");

        VerticalLayout pic = new VerticalLayout();
        pic.setSizeUndefined();
        pic.setSpacing(true);
        Image profilePic = new Image(null, new ThemeResource("img/profile-pic-300px.jpg"));
        profilePic.setWidth(100.0f, Unit.PIXELS);
        pic.addComponent(profilePic);

        Button upload = new Button("Change…", new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                Notification.show("Not implemented in this demo");
            }
        });
        upload.addStyleName(ValoTheme.BUTTON_TINY);
        pic.addComponent(upload);
        
        root.addComponent(pic);

        FormLayout details = new FormLayout();
        details.addStyleName(ValoTheme.FORMLAYOUT_LIGHT);
        root.addComponent(details);
        root.setExpandRatio(details, 1);
                
        nameField = new TextField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.nameField.caption"));
        nameField.setRequired(true);
        details.addComponent(nameField);    

        organizationContainer.removeAllItems();
        if (user.getAgent() instanceof Administrator)
        	organizationContainer.addAll(((Administrator)user.getAgent()).getOrganizations());
        else
        	organizationContainer.addItem(((Driver)user.getAgent()).getOrganization());
                        
        organizationDefaultField = new ComboBox(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.organizationDefaultField.caption"));
        organizationDefaultField.setRequired(true);
        organizationDefaultField.setContainerDataSource(organizationContainer);
        organizationDefaultField.setItemCaptionMode(ItemCaptionMode.PROPERTY);
        organizationDefaultField.setItemCaptionPropertyId("name");
        organizationDefaultField.setNullSelectionAllowed(false);        
        details.addComponent(organizationDefaultField);
        
        Label sectionContact = new Label(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.sectionContact.caption"));
        sectionContact.addStyleName(ValoTheme.LABEL_H4);
        sectionContact.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(sectionContact);

        emailField = new TextField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.emailField.caption"));
        emailField.setWidth("100%");
        emailField.setRequired(true);
        emailField.setNullRepresentation("");
        details.addComponent(emailField);    
        
        if (user.getAgent() instanceof Driver) {
	        Label sectionAdditional = new Label(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.sectionAdditional.caption"));
	        sectionAdditional.addStyleName(ValoTheme.LABEL_H4);
	        sectionAdditional.addStyleName(ValoTheme.LABEL_COLORED);
	        details.addComponent(sectionAdditional);
	
	        cardNumber = new TextField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.cardNumber.caption"));
	        cardNumber.setWidth("100%");
	        cardNumber.setRequired(true);
	        cardNumber.setNullRepresentation("");
	        details.addComponent(cardNumber);
	        
	        cardExpiryDate = new PopupDateField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.cardExpiryDate.caption"));
	        cardExpiryDate.setWidth("100%");
	        cardExpiryDate.setRequired(true);
	        details.addComponent(cardExpiryDate);
	
	        cardHolderBirthDate = new PopupDateField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.cardHolderBirthDate.caption"));
	        cardHolderBirthDate.setWidth("100%");
	        cardHolderBirthDate.setRequired(true);
	        details.addComponent(cardHolderBirthDate);
        }
        
        Label sectionSecurity = new Label(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.sectionSecurity.caption"));
        sectionSecurity.addStyleName(ValoTheme.LABEL_H4);
        sectionSecurity.addStyleName(ValoTheme.LABEL_COLORED);
        details.addComponent(sectionSecurity);
        
        usernameField = new TextField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.usernameField.caption"));
        usernameField.setWidth("100%");
        usernameField.setRequired(true);
        usernameField.setNullRepresentation("");
        details.addComponent(usernameField);
        
        passwordField = new PasswordField(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.passwordField.caption"));
        passwordField.setWidth("100%");
        passwordField.setRequired(true);
        passwordField.setNullRepresentation("");
        details.addComponent(passwordField);
        
        nameField.focus();
        
        return root;
    }

    private Component buildFooter(final User user) {
        HorizontalLayout footer = new HorizontalLayout();
        footer.setSpacing(true);
        footer.addStyleName(ValoTheme.WINDOW_BOTTOM_TOOLBAR);
        footer.setWidth(100.0f, Unit.PERCENTAGE);
        
        Button cancel = new Button(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.cancel.caption"));
        cancel.setClickShortcut(KeyCode.ESCAPE, null);        
        cancel.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	fieldGroup.discard();
            	
            	close();
            }
        });
        footer.addComponent(cancel);
        footer.setExpandRatio(cancel, 1);
        footer.setComponentAlignment(cancel, Alignment.TOP_RIGHT);
        
        Button ok = new Button(getI18N().getMessage("com.thingtrack.workbench.component.ProfilePreferencesWindow.ok.caption"));        
        ok.setClickShortcut(KeyCode.ENTER, null);
        ok.addStyleName(ValoTheme.BUTTON_PRIMARY);
        ok.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                try {
                    fieldGroup.commit();
                    // Updated user should also be persisted to database. But not in this demo.

                    Notification success = new Notification( "Profile updated successfully");
                    success.setDelayMsec(2000);
                    success.setStyleName("bar success small");
                    success.setPosition(Position.BOTTOM_CENTER);
                    success.show(Page.getCurrent());

                    if (user.getAgent() instanceof Administrator) {
                    	try {
							administratorService.save((Administrator)user.getAgent());
						} catch (Exception e) {
							NotificationHelper.sendErrorNotification("User Profile", e.getMessage());
						}
                    }
                    else {
                    	try {
							driverService.save((Driver)user.getAgent());
						} catch (Exception e) {
							NotificationHelper.sendErrorNotification("User Profile", e.getMessage());
						}
                    }
                    	
                    DashboardEventBus.post(new ProfileUpdatedEvent(user));
                    
                    close();
                } catch (CommitException e) {
                    Notification.show("Error while updating profile", Type.ERROR_MESSAGE);
                }

            }
        });                
        
        footer.addComponent(ok);
        footer.setComponentAlignment(ok, Alignment.TOP_RIGHT);
        
        return footer;
    }

    public static void open(String caption, I18N i18n, final User user, final boolean preferencesTabActive) {
        DashboardEventBus.post(new CloseOpenWindowsEvent());
        Window w = new ProfilePreferencesWindow(caption, i18n, user, preferencesTabActive);
        UI.getCurrent().addWindow(w);
        w.focus();
    }
}
