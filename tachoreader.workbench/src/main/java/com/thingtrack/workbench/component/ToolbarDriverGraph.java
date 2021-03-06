package com.thingtrack.workbench.component;

import java.io.Serializable;
import java.util.Date;

import com.google.common.eventbus.Subscribe;
import com.thingtrack.tachoreader.domain.Driver;
import com.thingtrack.tachoreader.service.api.DriverService;
import com.thingtrack.workbench.WorkbenchUI;
import com.thingtrack.workbench.event.DashboardEvent.ProfileUpdatedEvent;
import com.thingtrack.workbench.event.DashboardEventBus;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.AbstractSelect.ItemCaptionMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

@SuppressWarnings("serial")
public class ToolbarDriverGraph extends AbstractI18NCustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private HorizontalLayout toolbarLayout;
	@AutoGenerated
	private Button btnQuery;
	@AutoGenerated
	private HorizontalLayout separatorToolbar_02;
	@AutoGenerated
	private PopupDateField dtRegisterDate;
	@AutoGenerated
	private Label lblRegisterDate;
	@AutoGenerated
	private ComboBox cmbDriverField;
	@AutoGenerated
	private Label lblDriver;
	private DriverService driverService;
	private BeanItemContainer<Driver> driverContainer = new BeanItemContainer<Driver>(Driver.class);
	
	// toolbar button listeners
	private ClickDriverActivityQueryListener clickDriverActivityQueryListener = null;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public ToolbarDriverGraph() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		// get Services
    	getServices();
    	
    	// initialize and configure filters    	
		dtRegisterDate.setResolution(Resolution.DAY);
		dtRegisterDate.setDateFormat("dd/MM/yyyy");
		dtRegisterDate.setValue(new Date());
		
		// configure query button
		btnQuery.setIcon(FontAwesome.BOLT);
		btnQuery.addStyleName(ValoTheme.BUTTON_ICON_ONLY);
		btnQuery.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
            	Driver driver = (Driver) cmbDriverField.getValue();
            	            	
				if (driver != null && clickDriverActivityQueryListener != null)					
					clickDriverActivityQueryListener.queryDriveActivityClick(new ClickQueryEvent(driver, dtRegisterDate.getValue()));
            }
        });
		
		// define product Types list
    	try {
    		driverContainer = new BeanItemContainer<Driver>(Driver.class);
    		driverContainer.addAll(driverService.getAll(WorkbenchUI.getCurrent().getUser().getOrganizationDefault()));
			
    		cmbDriverField.setContainerDataSource(driverContainer);
			cmbDriverField.setItemCaptionMode(ItemCaptionMode.PROPERTY);
			cmbDriverField.setItemCaptionPropertyId("name");
			cmbDriverField.setNullSelectionAllowed(false);
			
			if (driverContainer.size() > 0)
				cmbDriverField.setValue(driverContainer.getIdByIndex(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	DashboardEventBus.register(this);
	}
	
	private void getServices() {
		this.driverService = (DriverService) WorkbenchUI.getCurrent().getApplicationContext().getBean("driverService");
	}
	
	public interface ClickDriverActivityQueryListener extends Serializable {
        public void queryDriveActivityClick(ClickQueryEvent event);
    }
	
	// navigation add listener toolbar	
	public void addClickDriverActivityQueryListener(ClickDriverActivityQueryListener listener) {
		this.clickDriverActivityQueryListener = listener;		
	}
	
	@Subscribe
    public void updateUserName(final ProfileUpdatedEvent event) {
		if (event != null) {			
			try {
				driverContainer.removeAllItems();
				driverContainer.addAll(driverService.getAll(event.getUser().getOrganizationDefault()));
				
				if (driverContainer.size() > 0)
					cmbDriverField.setValue(driverContainer.getIdByIndex(0));
				
				Driver driver = (Driver) cmbDriverField.getValue();
				
				if (driver != null && clickDriverActivityQueryListener != null)					
					clickDriverActivityQueryListener.queryDriveActivityClick(new ClickQueryEvent(driver, dtRegisterDate.getValue()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
    }
	
	public class ClickQueryEvent {
		private Date registerDate;
		private Driver driver;	
		
		public ClickQueryEvent(Driver driver, Date registerDate) {			
			this.driver = driver;
			this.registerDate =registerDate;
		}

		public Date getRegisterDate() {
			return this.registerDate;			
		}
		
		public Driver getDriver() {
			return this.driver;			
		}		
	}
	
	@Override
	protected void updateLabels() {
	}
	
	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(false);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// toolbarLayout
		toolbarLayout = buildToolbarLayout();
		mainLayout.addComponent(toolbarLayout);
		
		return mainLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildToolbarLayout() {
		// common part: create layout
		toolbarLayout = new HorizontalLayout();
		toolbarLayout.setImmediate(false);
		toolbarLayout.setWidth("-1px");
		toolbarLayout.setHeight("-1px");
		toolbarLayout.setMargin(false);
		toolbarLayout.setSpacing(true);
		
		DateFilter dateFilter = new DateFilter();
		dateFilter.setWidth("-1px");
		dateFilter.setHeight("-1px");
		toolbarLayout.addComponent(dateFilter);
		
		// lblDriver
		lblDriver = new Label();
		lblDriver.setImmediate(false);
		lblDriver.setWidth("-1px");
		lblDriver.setHeight("-1px");
		lblDriver.setValue("Driver:");
		toolbarLayout.addComponent(lblDriver);
		
		// cmbDriverField
		cmbDriverField = new ComboBox();
		cmbDriverField.setImmediate(false);
		cmbDriverField.setWidth("350px");
		cmbDriverField.setHeight("-1px");
		toolbarLayout.addComponent(cmbDriverField);
		
		// lblRegisterDate
		lblRegisterDate = new Label();
		lblRegisterDate.setImmediate(false);
		lblRegisterDate.setWidth("-1px");
		lblRegisterDate.setHeight("-1px");
		lblRegisterDate.setValue("Register Date:");
		toolbarLayout.addComponent(lblRegisterDate);
		
		// dtRegisterDate
		dtRegisterDate = new PopupDateField();
		dtRegisterDate.setImmediate(false);
		dtRegisterDate.setWidth("150px");
		dtRegisterDate.setHeight("-1px");
		toolbarLayout.addComponent(dtRegisterDate);
		
		// separatorToolbar_02
		separatorToolbar_02 = new HorizontalLayout();
		separatorToolbar_02.setImmediate(false);
		separatorToolbar_02.setWidth("20px");
		separatorToolbar_02.setHeight("-1px");
		separatorToolbar_02.setMargin(false);
		toolbarLayout.addComponent(separatorToolbar_02);
		
		// btnQuery
		btnQuery = new Button();
		btnQuery.setCaption("Button");
		btnQuery.setImmediate(true);
		btnQuery.setWidth("-1px");
		btnQuery.setHeight("-1px");
		toolbarLayout.addComponent(btnQuery);
		
		return toolbarLayout;
	}
}
