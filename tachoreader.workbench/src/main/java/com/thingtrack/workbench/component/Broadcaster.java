package com.thingtrack.workbench.component;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.workbench.WorkbenchUI;

@SuppressWarnings("serial")
public class Broadcaster implements Serializable {
    static ExecutorService executorService = Executors.newSingleThreadExecutor();

    public interface BroadcastListener {
        void receiveBroadcast(Tacho tacho);
    }
    
    private static LinkedList<BroadcastListener> listeners = new LinkedList<BroadcastListener>();
    
    public static synchronized void register(BroadcastListener listener) {
        listeners.add(listener);
    }
    
    public static synchronized void unregister(BroadcastListener listener) {
        listeners.remove(listener);
    }
    
    public static synchronized void broadcast(final Tacho tacho) {
        for (final BroadcastListener listener: listeners)
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                	listener.receiveBroadcast(tacho);
                }
            });
    }
}
