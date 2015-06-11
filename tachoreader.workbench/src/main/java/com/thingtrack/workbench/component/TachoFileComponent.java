package com.thingtrack.workbench.component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.server.FileResource;
import com.vaadin.server.Page;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class TachoFileComponent extends AbstractI18NCustomComponent
	implements Receiver, ProgressListener, FailedListener, SucceededListener {

	/*- VaadinEditorProperties={"grid":"RegularGrid,20","showGrid":true,"snapToGrid":true,"snapToObject":true,"movingGuides":false,"snappingDistance":10} */

	@AutoGenerated
	private VerticalLayout mainLayout;
	@AutoGenerated
	private Panel panel;
	@AutoGenerated
	private VerticalLayout panelContent;
	@AutoGenerated
	private HorizontalLayout uploadLayout;
	@AutoGenerated
	private Upload upload;
	
	// Show uploaded file in this placeholder
    private Image image = new Image("Uploaded Image");
    
    // Put upload in this memory buffer that grows automatically
    private FileOutputStream fos = null;
    
    // Name of the uploaded file
    private File file;
    private String filename;
    
    // Prevent too big downloads
    final long UPLOAD_LIMIT = 1000000l; // max image bytes lenght
    
    private SimpleDateFormat sdfFile = new SimpleDateFormat("yyyyMMddHHmmss");

    private String imagePath;
    
	/**
	 * The constructor should first build the main layout, set the
	 * composition root and then do any custom initialization.
	 *
	 * The constructor will not be automatically regenerated by the
	 * visual editor.
	 */
	public TachoFileComponent() {
		buildMainLayout();
		
		// get Services
		getServices();
		
		// TODO add user code here
		upload.setReceiver(this);
        upload.addProgressListener(this);
        upload.addFailedListener(this);
        upload.addSucceededListener(this);
         
        upload.addStartedListener(new StartedListener() {
            @Override
            public void uploadStarted(StartedEvent event) {
            	if (event.getContentLength() == -1)
            		return;
            	
                if (event.getContentLength() > UPLOAD_LIMIT) {
                    Notification.show("Too big file",
                        Notification.Type.ERROR_MESSAGE);
                    upload.interruptUpload();
                }
            }
        });
        
        // Check the size also during progress 
        upload.addProgressListener(new ProgressListener() {
            @Override
            public void updateProgress(long readBytes, long contentLength) {
                if (readBytes > UPLOAD_LIMIT) {
                    Notification.show("Too big file", Notification.Type.ERROR_MESSAGE);
                    
                    upload.interruptUpload();
                }
            } 
        });     
        
        // initialize image components
        image.setVisible(false);
        image.setHeight("140px");
        image.setWidth("180px");
        
        panelContent.addComponent(image);
        
        // get Image Path Repository from Spring 
		//this.imagePath = appConfig.getAppliedPropertySources().get("localProperties").getProperty("image.directoty").toString();
	}

	private void getServices() {
		//this.appConfig = (PropertySourcesPlaceholderConfigurer) WorkbenchUI.getCurrent().getApplicationContext().getBean("appConfig");	
	}
	
	@Override
	public void uploadSucceeded(SucceededEvent event) {
		image.setVisible(true);
		image.setCaption(getI18N().getMessage("com.thingtrack.workbench.view.field.ImageField.message.uploadSucceeded", filename, file.length()));
        image.setSource(new FileResource(file));
        image.markAsDirty();
		
	}

	@Override
	public void uploadFailed(FailedEvent event) {		
		Notification.show(getI18N().getMessage("com.thingtrack.workbench.view.field.ImageField.message.uploadFailed"), Notification.Type.ERROR_MESSAGE);
		
	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
	       /*progress.setVisible(true);
	       
	        if (contentLength == -1)
	            progress.setIndeterminate(true);
	        else {
	            progress.setIndeterminate(false);
	            progress.setValue(((float)readBytes) / ((float)contentLength));
	        }*/
		
	}

	@Override
	public OutputStream receiveUpload(String filename, String mimeType) {
		String imageFile;
		String[] tokens = filename.split("\\.");
		
		// set numeric file name (need if reload or change the file)
		if (tokens.length == 2)
			imageFile = sdfFile.format(new Date()) + "." + tokens[1];
		else
			imageFile = sdfFile.format(new Date());
					
		// check if exist the image Repository
		File imageRepository = new File(imagePath);
		if (!imageRepository.exists())
			imageRepository.mkdir();	
		
        try {
        	// delete old image file if exist
        	if (this.filename != null) {	
        		Path pathImage = FileSystems.getDefault().getPath(imagePath, this.filename);
				Files.deleteIfExists(pathImage);
        	}
        	
            // save the new image file and set
            file = new File(imageRepository, imageFile);
        	fos = new FileOutputStream(file);
        	
        	this.filename = imageFile;
     
        	
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Could not open file<br/>", e.getMessage(),
                             Notification.Type.ERROR_MESSAGE).show(Page.getCurrent());
            return null;
        } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	    return fos;
	}
	
	@Override
	protected void updateLabels() {
		// TODO Auto-generated method stub
		upload.setButtonCaption(getI18N().getMessage("com.thingtrack.workbench.view.field.ImageField.upload.caption"));
	}

	@AutoGenerated
	private VerticalLayout buildMainLayout() {
		// common part: create layout
		mainLayout = new VerticalLayout();
		mainLayout.setImmediate(false);
		mainLayout.setWidth("-1px");
		mainLayout.setHeight("-1px");
		mainLayout.setMargin(false);
		mainLayout.setSpacing(true);
		
		// top-level component properties
		setWidth("-1px");
		setHeight("-1px");
		
		// panel
		panel = buildPanel();
		mainLayout.addComponent(panel);
		
		return mainLayout;
	}

	@AutoGenerated
	private Panel buildPanel() {
		// common part: create layout
		panel = new Panel();
		panel.setImmediate(false);
		panel.setWidth("-1px");
		panel.setHeight("-1px");
		
		// panelContent
		panelContent = buildPanelContent();
		panel.setContent(panelContent);
		
		return panel;
	}

	@AutoGenerated
	private VerticalLayout buildPanelContent() {
		// common part: create layout
		panelContent = new VerticalLayout();
		panelContent.setImmediate(false);
		panelContent.setWidth("-1px");
		panelContent.setHeight("-1px");
		panelContent.setMargin(false);
		
		// uploadLayout
		uploadLayout = buildUploadLayout();
		panelContent.addComponent(uploadLayout);
		
		return panelContent;
	}

	@AutoGenerated
	private HorizontalLayout buildUploadLayout() {
		// common part: create layout
		uploadLayout = new HorizontalLayout();
		uploadLayout.setImmediate(false);
		uploadLayout.setWidth("-1px");
		uploadLayout.setHeight("-1px");
		uploadLayout.setMargin(false);
		
		// upload
		upload = new Upload();
		upload.setImmediate(false);
		upload.setWidth("-1px");
		upload.setHeight("-1px");
		uploadLayout.addComponent(upload);
		
		return uploadLayout;
	}

}
