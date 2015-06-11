package com.thingtrack.workbench.component;

import java.io.Serializable;

import com.github.peholmst.i18n4vaadin.I18N;
import com.github.peholmst.i18n4vaadin.support.I18NWindow;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@SuppressWarnings("serial")
public class WindowForm<BEANTYPE> extends I18NWindow {
	private DialogResult dialogResult;
	
	public enum DialogResult { 
		OK,
		CANCEL
	}
	  	
	public WindowForm(String caption, I18N i18n, final BEANTYPE entity, final CustomComponent layout, final CloseWindowDialogListener<BEANTYPE> listener) throws Exception {
		super(caption, i18n);
					
		// initialize form view		
		setModal(true);
    	setClosable(false);
    	setResizable(false);
    	setWidth("-1px");
		setHeight("-1px");
		setCloseShortcut(KeyCode.ESCAPE, null);
    	 
    	if (entity == null)
    		throw new Exception("Entity must be not null");
    		
    	// design form view
    	setContent(new VerticalLayout() {{
    		
	    	final EntityForm<BEANTYPE> entityForm = new EntityForm<BEANTYPE>(entity, layout);
	        addComponent(entityForm);
	
	        addComponent(new HorizontalLayout() {{
	            //setMargin(true);
	            setMargin(new MarginInfo(false, true, true, false));
	            setSpacing(true);
	            //addStyleName("footer");
	            setWidth("100%");
	
	            Button cancel = new Button(getI18N().getMessage("com.thingtrack.workbench.component.WindowForm.cancel"));
	            cancel.setClickShortcut(KeyCode.ESCAPE, null);
	            cancel.addClickListener(new ClickListener() {
	                @Override
	                public void buttonClick(ClickEvent event) {
	                	// set result form click
	                	dialogResult = DialogResult.CANCEL;
	                	
	                	// discard form
	                	entityForm.getBinder().discard();
	      			  	
	                	// close form
	                    close();
	                }
	            });
	            addComponent(cancel);
	            setExpandRatio(cancel, 1);
	            setComponentAlignment(cancel, Alignment.TOP_RIGHT);
		            
	            Button ok = new Button(getI18N().getMessage("com.thingtrack.workbench.component.WindowForm.ok"));
	            ok.setClickShortcut(KeyCode.ENTER, null);
	            ok.addStyleName("primary");
	            ok.addStyleName("wide");
	            ok.addStyleName("default");
	            ok.addClickListener(new ClickListener() {
	                @Override
	                public void buttonClick(ClickEvent event) {
	                	// set result form click
	                	dialogResult = DialogResult.OK;
	                	
	                	try {	  
	                		// validate form
	                		if (layout instanceof AbstractI18NValidableCustomComponent && 
	                			((AbstractI18NValidableCustomComponent)layout).isValidate())	                				                			
	                				return;	                		
	                        else {	  
	                        	// commit form
		                		entityForm.getBinder().commit();
								
		                		// close form
								close();
	                        }
						} catch (CommitException ex) {
							// TODO Auto-generated catch block
							ex.printStackTrace();
							
							close();
						}
	                }
	            });
	            
	            addComponent(ok);
	        }});
        }});
    	
    	// show form centered
    	center();
    	
    	addCloseListener(new Window.CloseListener() {	  
			  public void windowClose(Window.CloseEvent  e) {
				  if (listener != null) {					  
					  listener.windowDialogClose(new CloseWindowDialogEvent<BEANTYPE>(WindowForm.this, dialogResult, entity));
				  }				  
		      }
		});
    	    	
    	// show window
    	UI.getCurrent().addWindow(this);
	}
	
	public interface CloseWindowDialogListener<BEANTYPE> extends Serializable {
		public void windowDialogClose(WindowForm<BEANTYPE>.CloseWindowDialogEvent<BEANTYPE> event);
	}
	
	@SuppressWarnings("hiding")
	public class CloseWindowDialogEvent<BEANTYPE> extends CloseEvent {
		private final DialogResult dialogResult;
		private BEANTYPE domainEntity;
			  
		public CloseWindowDialogEvent(Component source) {
			super(source);
			
			this.dialogResult = DialogResult.CANCEL;
		}
		
		public CloseWindowDialogEvent(Component source, DialogResult dialogResult, BEANTYPE domainEntity) {
			super(source);
			
			this.dialogResult = dialogResult;
			this.domainEntity = domainEntity;			
		}
		
		public DialogResult getDialogResult() {
			return this.dialogResult;
		}
		
		public BEANTYPE getDomainEntity() {
			return this.domainEntity;			
		}		
	}
}
