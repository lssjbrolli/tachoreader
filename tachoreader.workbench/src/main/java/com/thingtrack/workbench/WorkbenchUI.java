package com.thingtrack.workbench;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.stereotype.Component;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.I18NListener;
import com.github.peholmst.i18n4vaadin.ResourceBundleI18N;
import com.google.common.eventbus.Subscribe;
import com.thingtrack.workbench.component.NotificationHelper;
import com.thingtrack.workbench.event.DashboardEvent.UserResetRequestedEvent;
import com.thingtrack.workbench.event.DashboardEventBus;
import com.thingtrack.workbench.event.DashboardEvent.BrowserResizeEvent;
import com.thingtrack.workbench.event.DashboardEvent.CloseOpenWindowsEvent;
import com.thingtrack.workbench.event.DashboardEvent.UserLoggedOutEvent;
import com.thingtrack.workbench.event.DashboardEvent.UserLoginRequestedEvent;
import com.thingtrack.workbench.event.DashboardEvent.UserLoginRequestedEventException;
import com.thingtrack.workbench.view.LoginView;
import com.thingtrack.workbench.view.MainView;
import com.thingtrack.workbench.component.Broadcaster;
import com.thingtrack.workbench.domain.DashboardNotification;
import com.thingtrack.tachoreader.domain.Administrator;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.tachoreader.service.api.UserService;
import com.thingtrack.workbench.event.DashboardEvent.TachoFileEvent;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.Page;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServletRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

@Theme("dashboard")
@Widgetset("com.thingtrack.workbench.WorkbenchWidgetSet")
@Title("TachoReader Workbench")
@SuppressWarnings("serial")
@Component
@Scope("prototype")
@Push
public final class WorkbenchUI extends UI implements I18NListener, Broadcaster.BroadcastListener {

    /*
     * This field stores an access to the dummy backend layer. In real
     * applications you most likely gain access to your beans trough lookup or
     * injection; and not in the UI but somewhere closer to where they're
     * actually accessed.
     */
    private final DashboardEventBus dashboardEventbus = new DashboardEventBus();

    private List<Tacho> tachoNotifications = new ArrayList<Tacho>();
    
    private User user;
    
	private I18N i18n;
    
	private Locale esLocale = new Locale("es");
	private Locale enLocale = new Locale("en");
	private Locale frLocale = new Locale("fr");
	
	private String usernameRememberMe;
	private String passwordRememberMe;
	
    @Autowired
    private transient ApplicationContext applicationContext;
    
    @Autowired
    private UserService userService;
    
    public static WorkbenchUI getCurrent() {
        return (WorkbenchUI) UI.getCurrent();
    }
    
    public ApplicationContext getApplicationContext() {
    	return getCurrent().applicationContext;    	
    }
    
    public User getUser() {    	
    	return getCurrent().user;    	
    }
    
    public List<Tacho> getNotifications() {    	
    	return getCurrent().tachoNotifications;    	
    }
    
    public I18N getI18N() {
    	return getCurrent().i18n;    	
    }
        
	private I18N configureI18n() {						
		// configure locale sources
		i18n = new ResourceBundleI18N("com/thingtrack/workbench/i18n/messages", esLocale, enLocale, frLocale);
		
		i18n.addListener(this);
				
		return i18n;
	}
	
	public String getTachoRepository() {    	
		PropertySourcesPlaceholderConfigurer appConfig = (PropertySourcesPlaceholderConfigurer)getCurrent().getApplicationContext().getBean("appConfig");				
		
    	return appConfig.getAppliedPropertySources().get("localProperties").getProperty("tacho.repository").toString();    	
    }
	
	private void setDefaultLocale() {
		Locale webBrowserLocale = Page.getCurrent().getWebBrowser().getLocale();
		
		// set default locale from browser configuration
		if (webBrowserLocale.toString().contains("es"))
			i18n.setCurrentLocale(esLocale);
		else if (webBrowserLocale.toString().contains("en"))
			i18n.setCurrentLocale(enLocale);
		else if (webBrowserLocale.toString().contains("fr"))
			i18n.setCurrentLocale(frLocale);
		else
			i18n.setCurrentLocale(esLocale);
	}
	
	private void setUserLocale(String language) {
		if (language.equals(User.LANGUAGE.Español.getValue()))
			i18n.setCurrentLocale(esLocale);
		else if (language.equals(User.LANGUAGE.English.getValue()))
			i18n.setCurrentLocale(enLocale);
		else if (language.equals(User.LANGUAGE.Français.getValue()))
			i18n.setCurrentLocale(frLocale);
		else
			i18n.setCurrentLocale(esLocale);
	}
	
    @Override
    protected void init(final VaadinRequest request) {
        DashboardEventBus.register(this);
        Responsive.makeResponsive(this);
        addStyleName(ValoTheme.UI_WITH_MENU);

        // initialize current  user
        VaadinSession.getCurrent().setAttribute(User.class.getName(), null);
        
        // configure I18n
        configureI18n();
       
        // set default Vaadin browser locale
    	setLocale(Locale.getDefault());
    	
        // set default App browser locale
        setDefaultLocale();

        // recover cookies remember me
        rememberMe();
        
        // set UI
        updateContent();       
        
        // Some views need to be aware of browser resize events so a
        // BrowserResizeEvent gets fired to the event bus on every occasion.
        Page.getCurrent().addBrowserWindowResizeListener(new BrowserWindowResizeListener() {
            @Override
            public void browserWindowResized(final BrowserWindowResizeEvent event) {
                DashboardEventBus.post(new BrowserResizeEvent());
            }
        });
        
        // Register to receive broadcasts
        Broadcaster.register(this);
    }

    private void rememberMe() {
    	Cookie[] cookies = ((VaadinServletRequest) VaadinService.getCurrentRequest()).getCookies();
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("username")) {
            	if (cookie.getValue() != null) {
            		String[] vals = cookie.getValue().split("#");
            		
            		if (vals.length > 1) 
            			usernameRememberMe = vals[0] + "@" + vals[1];
            		else
            			usernameRememberMe = cookie.getValue();
            	}
            }
        
	        if (cookie.getName().equals("password"))
	        	passwordRememberMe = cookie.getValue();
        }
    }
    
    /**
     * Updates the correct content for this UI based on the current user status.
     * If the user is logged in with appropriate privileges, main view is shown.
     * Otherwise login view is shown.
     */
    private void updateContent() {
        User user = (User) VaadinSession.getCurrent().getAttribute(User.class.getName());
        
        // Authenticated admin user
        if (user != null)
        {
        	if (user.getAgent() instanceof Administrator) {
	            setContent(new MainView());
	            removeStyleName("loginview");
	            //getNavigator().navigateTo(getNavigator().getState());
	            getNavigator().navigateTo("dashboard");	            
        	}
        	else
        		NotificationHelper.sendWarningNotification("Login View", "You do not have privileges to connect");
        } else {
            setContent(new LoginView(usernameRememberMe, passwordRememberMe));
            addStyleName("loginview");
        }
    }

    @Subscribe
    public void userLoginRequested(final UserResetRequestedEvent event) throws Exception { 
    	Page.getCurrent().getJavaScript().execute(String.format("document.cookie = '%s=%s;';", "username", ""));
    	Page.getCurrent().getJavaScript().execute(String.format("document.cookie = '%s=%s;';", "password", ""));

    }
    
    @Subscribe
    public void userLoginRequested(final UserLoginRequestedEvent event) throws Exception {    	
    	if (userService != null)
    	{
    		// set default admin user
    		try {
    			user = userService.getByUsername(event.getUserName());		
				
    			if (!user.getPassword().equals(event.getPassword()))    			
    				throw new UserLoginRequestedEventException();    			
    			
				// set language
				setUserLocale(user.getLanguage());
				
				// set current user in the vaadin context
	            VaadinSession.getCurrent().setAttribute(User.class.getName(), user);
	            
	            // set cookies remember me
	            if (event.isRemenberMe()) {
	            	String[] vals = event.getUserName().split("@");
	            	String username = null;
	            	if (vals.length > 1)
	            		username = vals[0] + "#" + vals[1];
	            	else
	            		username = event.getUserName();
	            	
	            	Page.getCurrent().getJavaScript().execute(String.format("document.cookie = '%s=%s;';", "username", username));
	            	Page.getCurrent().getJavaScript().execute(String.format("document.cookie = '%s=%s;';", "password", event.getPassword()));
	            }
	            else {
	            	Page.getCurrent().getJavaScript().execute(String.format("document.cookie = '%s=%s;';", "username", ""));
	            	Page.getCurrent().getJavaScript().execute(String.format("document.cookie = '%s=%s;';", "password", ""));
	            }
	            
	            updateContent();
			} catch (Exception e) {				
				throw new UserLoginRequestedEventException();
			}    	
    	}    	      
    }

    @Subscribe
    public void userLoggedOut(final UserLoggedOutEvent event) {
        // When the user logs out, current VaadinSession gets closed and the
        // page gets reloaded on the login screen. Do notice the this doesn't
        // invalidate the current HttpSession.
        VaadinSession.getCurrent().close();
        Page.getCurrent().reload();
    }

    @Subscribe
    public void closeOpenWindows(final CloseOpenWindowsEvent event) {
        for (Window window : getWindows()) {
            window.close();
        }
    }

    public static DashboardEventBus getDashboardEventbus() {
        return ((WorkbenchUI) getCurrent()).dashboardEventbus;
    }

	@Override
	public void localeChanged(I18N sender, Locale oldLocale, Locale newLocale) {		
		
	}

	// Must also unregister when the UI expires    
    @Override
    public void detach() {
    	Broadcaster.unregister(this);
        super.detach();
    }
    
	@Override
	public void receiveBroadcast(final Tacho tacho) {
		// Must lock the session to execute logic safely
        access(new Runnable() {
            @Override
            public void run() {
            	// post tacho register to show in dashboard events
            	DashboardEventBus.post(new TachoFileEvent(tacho));
            }
        });
		
	}
}
