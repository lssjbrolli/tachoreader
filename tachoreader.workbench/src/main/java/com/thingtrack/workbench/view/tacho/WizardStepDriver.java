package com.thingtrack.workbench.view.tacho;

import java.util.Date;
import java.util.List;

import org.vaadin.teemu.wizards.WizardStep;

import com.thingtrack.tachoreader.domain.Tacho;
import com.thingtrack.tachoreader.domain.Vehicle;
import com.thingtrack.tachoreader.service.api.TachoService;
import com.thingtrack.workbench.WorkbenchUI;
import com.thingtrack.workbench.component.AbstractI18NCustomComponent;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class WizardStepDriver extends AbstractI18NCustomComponent implements WizardStep {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Table driverTable;
	@AutoGenerated
	private Panel pnHelpStepTachoDriver;
	@AutoGenerated
	private VerticalLayout helpTachoDriverLayout;
	@AutoGenerated
	private Label lblHelpTachoDriverStep;
	
	private BeanItemContainer<Tacho> tachoContainer = new BeanItemContainer<Tacho>(Tacho.class);
	
	private TachoService tachoService;
	
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public WizardStepDriver() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		pnHelpStepTachoDriver.setCaption("Help Upload your Tachos");
		pnHelpStepTachoDriver.setIcon(FontAwesome.INFO_CIRCLE);
		pnHelpStepTachoDriver.addStyleName("color3");	
		
		StringBuffer helpTachosUpload = new StringBuffer("Desde esta pantalla usted podrá consultar la lista de conductores que han utilizado los vehículos anteriores en el intervalod de fechas seleccionado previamente. ");
		helpTachosUpload.append("Para continuar pulse el botón \"Siguiente\".");
				
		lblHelpTachoDriverStep.setContentMode(ContentMode.HTML);
		lblHelpTachoDriverStep.setValue(helpTachosUpload.toString());
		
		getServices();
	}

	private void getServices() {
		this.tachoService = (TachoService) WorkbenchUI.getCurrent().getApplicationContext().getBean("tachoService");
	}
	
	public void loadDatasource(List<Vehicle> vehicles, Date startActivityDate, Date endActivityDate) {
		try {
			tachoContainer.removeAllItems();
			tachoContainer.addAll(tachoService.getAll(vehicles, startActivityDate, endActivityDate));
			
			driverTable.setContainerDataSource(tachoContainer);    		
			
			/*driverTable.setVisibleColumns((Object[]) new String[] { "id", "driver.name", "driver.cardNumber" } );
			driverTable.setColumnHeaders(new String[] { "ID", "Name", "Card Number" } );
			
			driverTable.setColumnCollapsed("id", true);*/
			
			if (tachoContainer.size() > 0)
				driverTable.select(tachoContainer.getIdByIndex(0));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public String getCaption() {
		 return "Driver List Step"; 
	}
	 
	@Override
	public Component getContent() {
		return this;
	}

	@Override
	public boolean onAdvance() {
		return true;
	}

	@Override
	public boolean onBack() {
		return true;
	}
	
	@Override
	protected void updateLabels() {
		// TODO Auto-generated method stub
		
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("100%");
		mainLayout.setMargin(true);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("100.0%");
		
		// pnHelpStepTachoDriver
		pnHelpStepTachoDriver = buildPnHelpStepTachoDriver();
		mainLayout.addComponent(pnHelpStepTachoDriver);
		
		// tbDrivers
		driverTable = new Table();
		driverTable.setImmediate(false);
		driverTable.setWidth("100.0%");
		driverTable.setHeight("100.0%");
		mainLayout.addComponent(driverTable);
		mainLayout.setExpandRatio(driverTable, 1.0f);
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildPnHelpStepTachoDriver() {
		// common part: create layout
		pnHelpStepTachoDriver = new Panel();
		pnHelpStepTachoDriver.setImmediate(false);
		pnHelpStepTachoDriver.setWidth("100.0%");
		pnHelpStepTachoDriver.setHeight("-1px");
		
		// helpTachoDriverLayout
		helpTachoDriverLayout = buildHelpTachoDriverLayout();
		pnHelpStepTachoDriver.setContent(helpTachoDriverLayout);
		
		return pnHelpStepTachoDriver;
	}

	@AutoGenerated
	private VerticalLayout buildHelpTachoDriverLayout() {
		// common part: create layout
		helpTachoDriverLayout = new VerticalLayout();
		helpTachoDriverLayout.setImmediate(false);
		helpTachoDriverLayout.setWidth("100.0%");
		helpTachoDriverLayout.setHeight("100.0%");
		helpTachoDriverLayout.setMargin(true);
		
		// lblHelpTachoDriverStep
		lblHelpTachoDriverStep = new Label();
		lblHelpTachoDriverStep.setImmediate(false);
		lblHelpTachoDriverStep.setWidth("100.0%");
		lblHelpTachoDriverStep.setHeight("-1px");
		lblHelpTachoDriverStep.setValue("Label");
		helpTachoDriverLayout.addComponent(lblHelpTachoDriverStep);
		
		return helpTachoDriverLayout;
	}

}
