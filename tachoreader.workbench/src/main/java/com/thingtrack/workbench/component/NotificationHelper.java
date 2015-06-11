package com.thingtrack.workbench.component;

import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

public class NotificationHelper {
	public static void sendInformationNotification(String caption, String description) {
		Notification loginSuccess = new Notification(caption);
		loginSuccess.setDescription(description);
		loginSuccess.setDelayMsec(2000);
        loginSuccess.setPosition(Position.TOP_CENTER);
        //success.setStyleName(ValoTheme.NOTIFICATION_BAR + " " + ValoTheme.NOTIFICATION_FAILURE);
        loginSuccess.setStyleName(ValoTheme.NOTIFICATION_SUCCESS);
        loginSuccess.show(Page.getCurrent());
	}
	
	public static void sendWarningNotification(String caption, String description) {
		Notification loginSuccess = new Notification(caption);
		loginSuccess.setDescription(description);
		loginSuccess.setDelayMsec(2000);
        loginSuccess.setPosition(Position.TOP_CENTER);
        loginSuccess.setStyleName(ValoTheme.NOTIFICATION_WARNING);
        loginSuccess.show(Page.getCurrent());
	}
	
	public static void sendErrorNotification(String caption, String description) {
		Notification loginSuccess = new Notification(caption);
		loginSuccess.setDescription(description);
		loginSuccess.setDelayMsec(2000);
        loginSuccess.setPosition(Position.TOP_CENTER);
        loginSuccess.setStyleName(ValoTheme.NOTIFICATION_FAILURE);
        loginSuccess.show(Page.getCurrent());
	}

}