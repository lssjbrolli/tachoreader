package com.thingtrack.workbench.component;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.thingtrack.tachoreader.domain.CardActivityDailyChange;
import com.thingtrack.tachoreader.domain.CardActivityDailyChange.TYPE;
import com.vaadin.annotations.AutoGenerated;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class DriverActivityDailyChart extends CustomComponent {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private HorizontalLayout mainLayout;
	@AutoGenerated
	private VerticalLayout legendlLayout;
	@AutoGenerated
	private HorizontalLayout fillLayout;
	@AutoGenerated
	private HorizontalLayout unknownContainrLayout;
	@AutoGenerated
	private Label lblUnkown;
	@AutoGenerated
	private VerticalLayout unknownLayout;
	@AutoGenerated
	private HorizontalLayout restContainerLayout;
	@AutoGenerated
	private Label lblRest;
	@AutoGenerated
	private VerticalLayout restLayout;
	@AutoGenerated
	private HorizontalLayout shortBreackContainerLayout;
	@AutoGenerated
	private Label lblShortBrack;
	@AutoGenerated
	private VerticalLayout shortBreackLayout;
	@AutoGenerated
	private HorizontalLayout workingContainerLayout;
	@AutoGenerated
	private Label lblWorking;
	@AutoGenerated
	private VerticalLayout workingLayout;
	@AutoGenerated
	private HorizontalLayout drivingContainerLayout;
	@AutoGenerated
	private Label lblDriving;
	@AutoGenerated
	private HorizontalLayout drivingLayout;
	@AutoGenerated
	private HorizontalLayout availableContainerLayout;
	@AutoGenerated
	private Label lblAvailable;
	@AutoGenerated
	private VerticalLayout availableLayout;
	@AutoGenerated
	private VerticalLayout chartLayout;
	@AutoGenerated
	private HorizontalLayout hlAxisHour;
	@AutoGenerated
	private Label lblH24;
	@AutoGenerated
	private Label lblH23;
	@AutoGenerated
	private Label lblH22;
	@AutoGenerated
	private Label lblH21;
	@AutoGenerated
	private Label lblH20;
	@AutoGenerated
	private Label lblH19;
	@AutoGenerated
	private Label lblH18;
	@AutoGenerated
	private Label lblH17;
	@AutoGenerated
	private Label lblH16;
	@AutoGenerated
	private Label lblH15;
	@AutoGenerated
	private Label lblH14;
	@AutoGenerated
	private Label lblH13;
	@AutoGenerated
	private Label lblH12;
	@AutoGenerated
	private Label lblH11;
	@AutoGenerated
	private Label lblH10;
	@AutoGenerated
	private Label lblH09;
	@AutoGenerated
	private Label lblH08;
	@AutoGenerated
	private Label lblH07;
	@AutoGenerated
	private Label lblH06;
	@AutoGenerated
	private Label lblH05;
	@AutoGenerated
	private Label lblH04;
	@AutoGenerated
	private Label lblH03;
	@AutoGenerated
	private Label lblH02;
	@AutoGenerated
	private Label lblH01;
	@AutoGenerated
	private Label lblH00;
	private CssLayout layout =null;

	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public DriverActivityDailyChart() {
		buildMainLayout();
		setCompositionRoot(mainLayout);

		// TODO add user code here
		layout = new CssLayout() {
			 private static final long serialVersionUID = 321137800557050145L;

			 @Override
			 protected String getCss(Component c) {
				 if (c instanceof Label) {
					 TYPE type = (TYPE) ((Label) c).getData();
					 
					 String style=null;
					 if (type.equals(CardActivityDailyChange.TYPE.UNKNOWN))
						 style = "background: #B266FF"; // PURPLE;
		             else if (type.equals(CardActivityDailyChange.TYPE.BREAK_REST))
		            	 style = "background: #FF0000"; // RED		            	 
		             else if (type.equals(CardActivityDailyChange.TYPE.SHORT_BREAK))
		            	 style = "background: #FF9933"; // ORANGE
		             else if (type.equals(CardActivityDailyChange.TYPE.AVAILABLE))
		            	 style = "background: #000000"; // BLACK
		             else if (type.equals(CardActivityDailyChange.TYPE.WORKING))
		            	 style = "background: #FFFF00"; // YELLOW					 
		             else if (type.equals(CardActivityDailyChange.TYPE.DRIVING))
		            	 style = "background: #00CC00"; // GREEN					 
					 
					 // Color the boxes with random colors					 
					 return style;
				 }
				 
				 return null;
			 }
	      };
	      	      
	      layout.setSizeFull();
	      chartLayout.addComponent(layout);
	      chartLayout.setExpandRatio(layout, 1.0f);
	      
	}

	public void clearGraph() {
		layout.removeAllComponents();
	}
	public void paintGraph(List<CardActivityDailyChange> cardActivityDailyChanges) {		
		Calendar calendar = Calendar.getInstance();

		Date startCardActivityDailyChangeTime = null;
		Date endCardActivityDailyChangeTime = null;
		
		String startHour = "0";
		String startMinute = "0";
		String endHour = "0";
		String endMinute = "0";
		
		for (int i = 0; i < cardActivityDailyChanges.size(); i++) {
			Label box = new Label();

			// get the start and end time node
			if (i < cardActivityDailyChanges.size()-1) {								
				startCardActivityDailyChangeTime = cardActivityDailyChanges.get(i).getRecordDate();
				endCardActivityDailyChangeTime = cardActivityDailyChanges.get(i+1).getRecordDate();			
			}
			else {
				startCardActivityDailyChangeTime = cardActivityDailyChanges.get(i).getRecordDate();
				calendar.setTime(startCardActivityDailyChangeTime);
				calendar.set(Calendar.HOUR_OF_DAY, 24);
				calendar.set(Calendar.MINUTE, 0);
				calendar.set(Calendar.SECOND, 0);
				calendar.set(Calendar.MILLISECOND, 0);
				
				endCardActivityDailyChangeTime = calendar.getTime();
			}
			
        	// calculate hours gap
        	long diff = endCardActivityDailyChangeTime.getTime() - startCardActivityDailyChangeTime.getTime();
        	long diffHours = diff / (60 * 60 * 1000) % 24;
        	long diffMinutes = diff / (60 * 1000) % 60;
        	
        	float width = Float.valueOf(diffHours) + Float.valueOf(diffMinutes)/60;
        	
        	// transform hour in % to paint
            float widthPercent = (width / 24) * 100;
            box.setWidth(widthPercent, Unit.PERCENTAGE);
                        
            // set label entity data
            box.setData(cardActivityDailyChanges.get(i).getType());
            
            // set label description
			calendar.setTime(startCardActivityDailyChangeTime);
			startHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			startMinute = String.valueOf(calendar.get(Calendar.MINUTE));
			
			calendar.setTime(endCardActivityDailyChangeTime);
			if (i < cardActivityDailyChanges.size()-1)
				endHour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
			else
				endHour = "24";			
			endMinute = String.valueOf(calendar.get(Calendar.MINUTE));
			box.setDescription(startHour + ":" + startMinute + " - " + endHour + ":" + endMinute);
			
            if (cardActivityDailyChanges.get(i).getType().equals(CardActivityDailyChange.TYPE.UNKNOWN))
            	box.setHeight(10, Unit.PIXELS);
            else if (cardActivityDailyChanges.get(i).getType().equals(CardActivityDailyChange.TYPE.BREAK_REST))
            	box.setHeight(20, Unit.PIXELS);
            else if (cardActivityDailyChanges.get(i).getType().equals(CardActivityDailyChange.TYPE.SHORT_BREAK))
            	box.setHeight(30, Unit.PIXELS);
            else if (cardActivityDailyChanges.get(i).getType().equals(CardActivityDailyChange.TYPE.AVAILABLE))
            	box.setHeight(40, Unit.PIXELS);         
            else if (cardActivityDailyChanges.get(i).getType().equals(CardActivityDailyChange.TYPE.WORKING))
            	box.setHeight(50, Unit.PIXELS);
            else if (cardActivityDailyChanges.get(i).getType().equals(CardActivityDailyChange.TYPE.DRIVING))
            	box.setHeight(60, Unit.PIXELS);               

            layout.addComponent(box);
		}			 	
	}
	
	@AutoGenerated
	private HorizontalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new HorizontalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("100%");
		mainLayout.setHeight("480px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("100.0%");
		setHeight("480px");
		
		// chartLayout
		chartLayout = buildChartLayout();
		mainLayout.addComponent(chartLayout);
		mainLayout.setExpandRatio(chartLayout, 1.0f);
		
		// legendlLayout
		legendlLayout = buildLegendlLayout();
		mainLayout.addComponent(legendlLayout);
		
		return mainLayout;
	}

	@AutoGenerated
	private VerticalLayout buildChartLayout() {
		// common part: create layout
		chartLayout = new VerticalLayout();
		chartLayout.setImmediate(false);
		chartLayout.setWidth("100.0%");
		chartLayout.setHeight("100.0%");
		chartLayout.setMargin(false);
		
		// hlAxisHour
		hlAxisHour = buildHlAxisHour();
		chartLayout.addComponent(hlAxisHour);
		
		return chartLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildHlAxisHour() {
		// common part: create layout
		hlAxisHour = new HorizontalLayout();
		hlAxisHour.setImmediate(false);
		hlAxisHour.setWidth("100.0%");
		hlAxisHour.setHeight("-1px");
		hlAxisHour.setMargin(false);
		
		// lblH00
		lblH00 = new Label();
		lblH00.setImmediate(false);
		lblH00.setWidth("-1px");
		lblH00.setHeight("-1px");
		lblH00.setValue("0");
		hlAxisHour.addComponent(lblH00);
		
		// lblH01
		lblH01 = new Label();
		lblH01.setImmediate(false);
		lblH01.setWidth("-1px");
		lblH01.setHeight("-1px");
		lblH01.setValue("1");
		hlAxisHour.addComponent(lblH01);
		
		// lblH02
		lblH02 = new Label();
		lblH02.setImmediate(false);
		lblH02.setWidth("-1px");
		lblH02.setHeight("-1px");
		lblH02.setValue("2");
		hlAxisHour.addComponent(lblH02);
		
		// lblH03
		lblH03 = new Label();
		lblH03.setImmediate(false);
		lblH03.setWidth("-1px");
		lblH03.setHeight("-1px");
		lblH03.setValue("3");
		hlAxisHour.addComponent(lblH03);
		
		// lblH04
		lblH04 = new Label();
		lblH04.setImmediate(false);
		lblH04.setWidth("-1px");
		lblH04.setHeight("-1px");
		lblH04.setValue("4");
		hlAxisHour.addComponent(lblH04);
		
		// lblH05
		lblH05 = new Label();
		lblH05.setImmediate(false);
		lblH05.setWidth("-1px");
		lblH05.setHeight("-1px");
		lblH05.setValue("5");
		hlAxisHour.addComponent(lblH05);
		
		// lblH06
		lblH06 = new Label();
		lblH06.setImmediate(false);
		lblH06.setWidth("-1px");
		lblH06.setHeight("-1px");
		lblH06.setValue("6");
		hlAxisHour.addComponent(lblH06);
		
		// lblH07
		lblH07 = new Label();
		lblH07.setImmediate(false);
		lblH07.setWidth("-1px");
		lblH07.setHeight("-1px");
		lblH07.setValue("7");
		hlAxisHour.addComponent(lblH07);
		
		// lblH08
		lblH08 = new Label();
		lblH08.setImmediate(false);
		lblH08.setWidth("-1px");
		lblH08.setHeight("-1px");
		lblH08.setValue("8");
		hlAxisHour.addComponent(lblH08);
		
		// lblH09
		lblH09 = new Label();
		lblH09.setImmediate(false);
		lblH09.setWidth("-1px");
		lblH09.setHeight("-1px");
		lblH09.setValue("9");
		hlAxisHour.addComponent(lblH09);
		
		// lblH10
		lblH10 = new Label();
		lblH10.setImmediate(false);
		lblH10.setWidth("-1px");
		lblH10.setHeight("-1px");
		lblH10.setValue("10");
		hlAxisHour.addComponent(lblH10);
		
		// lblH11
		lblH11 = new Label();
		lblH11.setImmediate(false);
		lblH11.setWidth("-1px");
		lblH11.setHeight("-1px");
		lblH11.setValue("11");
		hlAxisHour.addComponent(lblH11);
		
		// lblH12
		lblH12 = new Label();
		lblH12.setImmediate(false);
		lblH12.setWidth("-1px");
		lblH12.setHeight("-1px");
		lblH12.setValue("12");
		hlAxisHour.addComponent(lblH12);
		
		// lblH13
		lblH13 = new Label();
		lblH13.setImmediate(false);
		lblH13.setWidth("-1px");
		lblH13.setHeight("-1px");
		lblH13.setValue("13");
		hlAxisHour.addComponent(lblH13);
		
		// lblH14
		lblH14 = new Label();
		lblH14.setImmediate(false);
		lblH14.setWidth("-1px");
		lblH14.setHeight("-1px");
		lblH14.setValue("14");
		hlAxisHour.addComponent(lblH14);
		
		// lblH15
		lblH15 = new Label();
		lblH15.setImmediate(false);
		lblH15.setWidth("-1px");
		lblH15.setHeight("-1px");
		lblH15.setValue("15");
		hlAxisHour.addComponent(lblH15);
		
		// lblH16
		lblH16 = new Label();
		lblH16.setImmediate(false);
		lblH16.setWidth("-1px");
		lblH16.setHeight("-1px");
		lblH16.setValue("16");
		hlAxisHour.addComponent(lblH16);
		
		// lblH17
		lblH17 = new Label();
		lblH17.setImmediate(false);
		lblH17.setWidth("-1px");
		lblH17.setHeight("-1px");
		lblH17.setValue("17");
		hlAxisHour.addComponent(lblH17);
		
		// lblH18
		lblH18 = new Label();
		lblH18.setImmediate(false);
		lblH18.setWidth("-1px");
		lblH18.setHeight("-1px");
		lblH18.setValue("18");
		hlAxisHour.addComponent(lblH18);
		
		// lblH19
		lblH19 = new Label();
		lblH19.setImmediate(false);
		lblH19.setWidth("-1px");
		lblH19.setHeight("-1px");
		lblH19.setValue("19");
		hlAxisHour.addComponent(lblH19);
		
		// lblH20
		lblH20 = new Label();
		lblH20.setImmediate(false);
		lblH20.setWidth("-1px");
		lblH20.setHeight("-1px");
		lblH20.setValue("20");
		hlAxisHour.addComponent(lblH20);
		
		// lblH21
		lblH21 = new Label();
		lblH21.setImmediate(false);
		lblH21.setWidth("-1px");
		lblH21.setHeight("-1px");
		lblH21.setValue("21");
		hlAxisHour.addComponent(lblH21);
		
		// lblH22
		lblH22 = new Label();
		lblH22.setImmediate(false);
		lblH22.setWidth("-1px");
		lblH22.setHeight("-1px");
		lblH22.setValue("22");
		hlAxisHour.addComponent(lblH22);
		
		// lblH23
		lblH23 = new Label();
		lblH23.setImmediate(false);
		lblH23.setWidth("-1px");
		lblH23.setHeight("-1px");
		lblH23.setValue("23");
		hlAxisHour.addComponent(lblH23);
		
		// lblH24
		lblH24 = new Label();
		lblH24.setImmediate(false);
		lblH24.setWidth("-1px");
		lblH24.setHeight("-1px");
		lblH24.setValue("24");
		hlAxisHour.addComponent(lblH24);
		
		return hlAxisHour;
	}

	@AutoGenerated
	private VerticalLayout buildLegendlLayout() {
		// common part: create layout
		legendlLayout = new VerticalLayout();
		legendlLayout.setImmediate(false);
		legendlLayout.setWidth("200px");
		legendlLayout.setHeight("100.0%");
		legendlLayout.setMargin(false);
		
		// availableContainerLayout
		availableContainerLayout = buildAvailableContainerLayout();
		legendlLayout.addComponent(availableContainerLayout);
		
		// drivingContainerLayout
		drivingContainerLayout = buildDrivingContainerLayout();
		legendlLayout.addComponent(drivingContainerLayout);
		
		// workingContainerLayout
		workingContainerLayout = buildWorkingContainerLayout();
		legendlLayout.addComponent(workingContainerLayout);
		
		// shortBreackContainerLayout
		shortBreackContainerLayout = buildShortBreackContainerLayout();
		legendlLayout.addComponent(shortBreackContainerLayout);
		
		// restContainerLayout
		restContainerLayout = buildRestContainerLayout();
		legendlLayout.addComponent(restContainerLayout);
		
		// unknownContainrLayout
		unknownContainrLayout = buildUnknownContainrLayout();
		legendlLayout.addComponent(unknownContainrLayout);
		
		// fillLayout
		fillLayout = new HorizontalLayout();
		fillLayout.setImmediate(false);
		fillLayout.setWidth("100.0%");
		fillLayout.setHeight("100.0%");
		fillLayout.setMargin(false);
		legendlLayout.addComponent(fillLayout);
		legendlLayout.setExpandRatio(fillLayout, 1.0f);
		
		return legendlLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildAvailableContainerLayout() {
		// common part: create layout
		availableContainerLayout = new HorizontalLayout();
		availableContainerLayout.setImmediate(false);
		availableContainerLayout.setWidth("100.0%");
		availableContainerLayout.setHeight("-1px");
		availableContainerLayout.setMargin(false);
		availableContainerLayout.setSpacing(true);
		
		// availableLayout
		availableLayout = new VerticalLayout();
		availableLayout.setStyleName("activity_available");
		availableLayout.setImmediate(false);
		availableLayout.setWidth("40px");
		availableLayout.setHeight("-1px");
		availableLayout.setMargin(false);
		availableContainerLayout.addComponent(availableLayout);
		
		// lblAvailable
		lblAvailable = new Label();
		lblAvailable.setImmediate(false);
		lblAvailable.setWidth("-1px");
		lblAvailable.setHeight("-1px");
		lblAvailable.setValue("available");
		availableContainerLayout.addComponent(lblAvailable);
		availableContainerLayout.setExpandRatio(lblAvailable, 1.0f);
		availableContainerLayout.setComponentAlignment(lblAvailable,
				new Alignment(33));
		
		return availableContainerLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildDrivingContainerLayout() {
		// common part: create layout
		drivingContainerLayout = new HorizontalLayout();
		drivingContainerLayout.setImmediate(false);
		drivingContainerLayout.setWidth("100.0%");
		drivingContainerLayout.setHeight("-1px");
		drivingContainerLayout.setMargin(false);
		drivingContainerLayout.setSpacing(true);
		
		// drivingLayout
		drivingLayout = new HorizontalLayout();
		drivingLayout.setStyleName("activity_driving");
		drivingLayout.setImmediate(false);
		drivingLayout.setWidth("40px");
		drivingLayout.setHeight("-1px");
		drivingLayout.setMargin(false);
		drivingContainerLayout.addComponent(drivingLayout);
		
		// lblDriving
		lblDriving = new Label();
		lblDriving.setImmediate(false);
		lblDriving.setWidth("-1px");
		lblDriving.setHeight("-1px");
		lblDriving.setValue("driving");
		drivingContainerLayout.addComponent(lblDriving);
		drivingContainerLayout.setExpandRatio(lblDriving, 1.0f);
		drivingContainerLayout.setComponentAlignment(lblDriving, new Alignment(
				33));
		
		return drivingContainerLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildWorkingContainerLayout() {
		// common part: create layout
		workingContainerLayout = new HorizontalLayout();
		workingContainerLayout.setImmediate(false);
		workingContainerLayout.setWidth("100.0%");
		workingContainerLayout.setHeight("-1px");
		workingContainerLayout.setMargin(false);
		workingContainerLayout.setSpacing(true);
		
		// workingLayout
		workingLayout = new VerticalLayout();
		workingLayout.setStyleName("activity_working");
		workingLayout.setImmediate(false);
		workingLayout.setWidth("40px");
		workingLayout.setHeight("-1px");
		workingLayout.setMargin(false);
		workingContainerLayout.addComponent(workingLayout);
		
		// lblWorking
		lblWorking = new Label();
		lblWorking.setImmediate(false);
		lblWorking.setWidth("-1px");
		lblWorking.setHeight("-1px");
		lblWorking.setValue("working");
		workingContainerLayout.addComponent(lblWorking);
		workingContainerLayout.setExpandRatio(lblWorking, 1.0f);
		workingContainerLayout.setComponentAlignment(lblWorking, new Alignment(
				33));
		
		return workingContainerLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildShortBreackContainerLayout() {
		// common part: create layout
		shortBreackContainerLayout = new HorizontalLayout();
		shortBreackContainerLayout.setImmediate(false);
		shortBreackContainerLayout.setWidth("100.0%");
		shortBreackContainerLayout.setHeight("-1px");
		shortBreackContainerLayout.setMargin(false);
		shortBreackContainerLayout.setSpacing(true);
		
		// shortBreackLayout
		shortBreackLayout = new VerticalLayout();
		shortBreackLayout.setStyleName("activity_shortbreack");
		shortBreackLayout.setImmediate(false);
		shortBreackLayout.setWidth("40px");
		shortBreackLayout.setHeight("-1px");
		shortBreackLayout.setMargin(false);
		shortBreackContainerLayout.addComponent(shortBreackLayout);
		
		// lblShortBrack
		lblShortBrack = new Label();
		lblShortBrack.setImmediate(false);
		lblShortBrack.setWidth("-1px");
		lblShortBrack.setHeight("-1px");
		lblShortBrack.setValue("short breack");
		shortBreackContainerLayout.addComponent(lblShortBrack);
		shortBreackContainerLayout.setExpandRatio(lblShortBrack, 1.0f);
		shortBreackContainerLayout.setComponentAlignment(lblShortBrack,
				new Alignment(33));
		
		return shortBreackContainerLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildRestContainerLayout() {
		// common part: create layout
		restContainerLayout = new HorizontalLayout();
		restContainerLayout.setImmediate(false);
		restContainerLayout.setWidth("100.0%");
		restContainerLayout.setHeight("-1px");
		restContainerLayout.setMargin(false);
		restContainerLayout.setSpacing(true);
		
		// restLayout
		restLayout = new VerticalLayout();
		restLayout.setStyleName("activity_rest");
		restLayout.setImmediate(false);
		restLayout.setWidth("40px");
		restLayout.setHeight("-1px");
		restLayout.setMargin(false);
		restContainerLayout.addComponent(restLayout);
		
		// lblRest
		lblRest = new Label();
		lblRest.setImmediate(false);
		lblRest.setWidth("-1px");
		lblRest.setHeight("-1px");
		lblRest.setValue("breack/rest");
		restContainerLayout.addComponent(lblRest);
		restContainerLayout.setExpandRatio(lblRest, 1.0f);
		restContainerLayout.setComponentAlignment(lblRest, new Alignment(33));
		
		return restContainerLayout;
	}

	@AutoGenerated
	private HorizontalLayout buildUnknownContainrLayout() {
		// common part: create layout
		unknownContainrLayout = new HorizontalLayout();
		unknownContainrLayout.setImmediate(false);
		unknownContainrLayout.setWidth("100.0%");
		unknownContainrLayout.setHeight("-1px");
		unknownContainrLayout.setMargin(false);
		unknownContainrLayout.setSpacing(true);
		
		// unknownLayout
		unknownLayout = new VerticalLayout();
		unknownLayout.setStyleName("activity_unknown");
		unknownLayout.setImmediate(false);
		unknownLayout.setWidth("40px");
		unknownLayout.setHeight("-1px");
		unknownLayout.setMargin(false);
		unknownContainrLayout.addComponent(unknownLayout);
		
		// lblUnkown
		lblUnkown = new Label();
		lblUnkown.setImmediate(false);
		lblUnkown.setWidth("-1px");
		lblUnkown.setHeight("-1px");
		lblUnkown.setValue("unkown");
		unknownContainrLayout.addComponent(lblUnkown);
		unknownContainrLayout.setExpandRatio(lblUnkown, 1.0f);
		unknownContainrLayout.setComponentAlignment(lblUnkown,
				new Alignment(33));
		
		return unknownContainrLayout;
	}

}
