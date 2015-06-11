package com.thingtrack.workbench.event;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.SubscriberExceptionContext;
import com.google.common.eventbus.SubscriberExceptionHandler;
import com.thingtrack.workbench.WorkbenchUI;
import com.thingtrack.workbench.event.DashboardEvent.UserLoginRequestedEventException;

/**
 * A simple wrapper for Guava event bus. Defines static convenience methods for relevant actions.
 */
public class DashboardEventBus implements SubscriberExceptionHandler {

    private final EventBus eventBus = new EventBus(this);

    public static void post(final Object event) {
        WorkbenchUI.getDashboardEventbus().eventBus.post(event);
    }

    public static void register(final Object object) {
        WorkbenchUI.getDashboardEventbus().eventBus.register(object);
    }

    public static void unregister(final Object object) {
        WorkbenchUI.getDashboardEventbus().eventBus.unregister(object);
    }

    @Override
    public final void handleException(final Throwable exception, final SubscriberExceptionContext context) {
    	if (exception instanceof UserLoginRequestedEventException) {
	    	EventBus eventBus = context.getEventBus();
	        eventBus.post(exception);
    	}
    }
}
