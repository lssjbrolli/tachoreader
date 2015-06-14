package com.thingtrack.workbench.event;

import com.thingtrack.tachoreader.domain.Organization;
import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.User;
import com.thingtrack.workbench.view.DashboardViewType;

/*
 * Event bus events used in Dashboard are listed here as inner classes.
 */
public abstract class DashboardEvent {

	public static final class OrganizationIncludeEvent {
        private final Organization organization;
        
        public OrganizationIncludeEvent(final Organization organization) {
            this.organization = organization;
        }
        
        public Organization getOrganization() {
        	return organization;
        }
    }
	
	public static final class OrganizationExcludeEvent {
        private final Organization organization;
        
        public OrganizationExcludeEvent(final Organization organization) {
            this.organization = organization;
        }
        
        public Organization getOrganization() {
        	return organization;
        }
    }
	
	public static final class UserResetRequestedEvent {
        private final boolean rememberMe;
        
        public UserResetRequestedEvent(final boolean rememberMe) {
            this.rememberMe = rememberMe;
        }
        
        public boolean isRemenberMe() {
        	return rememberMe;
        }
    }
	
    public static final class UserLoginRequestedEvent {
        private final String userName, password;
        private final boolean rememberMe;
        
        public UserLoginRequestedEvent(final String userName, final String password, final boolean rememberMe) {
            this.userName = userName;
            this.password = password;
            this.rememberMe = rememberMe;
        }

        public String getUserName() {
            return userName;
        }

        public String getPassword() {
            return password;
        }
        
        public boolean isRemenberMe() {
        	return rememberMe;
        }
    }
    
    public static class UserLoginRequestedEventException extends Exception {
    	private static final long serialVersionUID = 1L;    	
    }
    
    public static class BrowserResizeEvent {

    }

    public static class UserLoggedOutEvent {

    }

    public static class NotificationsCountUpdatedEvent {
    }

    public static final class ReportsCountUpdatedEvent {
        private final int count;

        public ReportsCountUpdatedEvent(final int count) {
            this.count = count;
        }

        public int getCount() {
            return count;
        }

    }

    public static final class PostViewChangeEvent {
        private final DashboardViewType view;

        public PostViewChangeEvent(final DashboardViewType view) {
            this.view = view;
        }

        public DashboardViewType getView() {
            return view;
        }
    }

    public static class CloseOpenWindowsEvent {
    }

    public static class ProfileUpdatedEvent {
    	 private final User user;

         public ProfileUpdatedEvent(final User user) {
             this.user = user;
         }

         public User getUser() {
             return user;
         }
    }
    
    public static class TachoFileEvent {
   	 private final Tacho tacho;

        public TachoFileEvent(final Tacho tacho) {
            this.tacho = tacho;
        }

        public Tacho getTacho() {
            return tacho;
        }
   }
}
