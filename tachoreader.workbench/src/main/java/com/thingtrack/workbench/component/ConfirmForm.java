package com.thingtrack.workbench.component;

import java.io.Serializable;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class ConfirmForm extends Window {
	private DialogResult dialogResult;
	
	public enum DialogResult { 
		YES,
		NO
	}
	  	
	@SuppressWarnings("rawtypes")
	public ConfirmForm(String caption, final String message, final CloseConfirmFormListener listener) {
		super(caption, null);
					
		setModal(true);
    	setClosable(false);
    	setResizable(false);
    	setWidth("-1px");
		setHeight("-1px");
		
    	addStyleName("edit-sample");
    	     				    	
    	setContent(new VerticalLayout() {{
    		// Message Layout	    	
	    	VerticalLayout messageLayout = new VerticalLayout();
	    	messageLayout.setImmediate(false);
	    	messageLayout.setWidth("-1px");
	    	messageLayout.setHeight("-1px");
	    	messageLayout.setMargin(true);

	    	Label text = new Label(message);
	    	text.setImmediate(false);
	    	text.setWidth("-1px");
	    	text.setHeight("-1px");
	    	messageLayout.addComponent(text);
	    	messageLayout.setExpandRatio(text, 1.0f);
			
	    	addComponent(messageLayout);
	    	
	        // form layout buttons footer
	        addComponent(new HorizontalLayout() {{
	            setMargin(true);
	            setSpacing(true);
	            addStyleName("footer");
	            setWidth("100%");
	
	            Button no = new Button("No");
	            no.addClickListener(new ClickListener() {
	                @Override
	                public void buttonClick(ClickEvent event) {
	                	dialogResult = DialogResult.NO;
	      			  	
	                    close();
	                }
	            });
	            no.setClickShortcut(KeyCode.ESCAPE, null);
	            addComponent(no);
	            setExpandRatio(no, 1);
	            setComponentAlignment(no, Alignment.TOP_RIGHT);
	
	            Button yes = new Button("Yes");
	            yes.addStyleName("wide");
	            yes.addStyleName("default");
	            yes.addClickListener(new ClickListener() {
	                @Override
	                public void buttonClick(ClickEvent event) {
                		dialogResult = DialogResult.YES;
                		
                		close();	                		                	
	                }
	            });
	            
	            yes.setClickShortcut(KeyCode.ENTER, null);
	            addComponent(yes);
	        }});
        }});
    	
    	center();
    	
    	addCloseListener(new Window.CloseListener() {	  
			  public void windowClose(Window.CloseEvent  e) {
				  if (listener != null) {					  
					  listener.windowDialogClose(new CloseWindowDialogEvent(ConfirmForm.this, dialogResult));
				  }				  
		      }
		});
    	
    	UI.getCurrent().addWindow(this);
	}
	
	public interface CloseConfirmFormListener<BEANTYPE> extends Serializable {
		public void windowDialogClose(ConfirmForm.CloseWindowDialogEvent event);
	}
	
	public class CloseWindowDialogEvent extends CloseEvent {
		private final DialogResult dialogResult;
			  		
		public CloseWindowDialogEvent(Component source, DialogResult dialogResult) {
			super(source);
			
			this.dialogResult = dialogResult;		
		}
		
		public DialogResult getDialogResult() {
			return this.dialogResult;
		}		
	}
}
