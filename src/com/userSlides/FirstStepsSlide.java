package com.userSlides;

import IO.Communicator;
import com.TsvUpload;
import com.UploadInfoWindow;
import com.vaadin.event.FieldEvents;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import javax.validation.constraints.Null;

/**
 * Created by heumos on 5/27/15.
 */
public class FirstStepsSlide extends AUserSlide {

    private Label headerText;
    private Label subHeader;
    private TextField projectName;
    private TextField personInCharge;
    private VerticalLayout contact;
    private Upload uploader;
    private TsvUpload content = null;
    private String uploadedStuff = "";
    private Label spacer;
    private Label uploadInfo;

    public FirstStepsSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        VerticalLayout main = new VerticalLayout();
        HorizontalLayout content = new HorizontalLayout();
        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(this.projectName);
        layout.addComponent(this.personInCharge);
        layout.addComponent(this.uploader);
        layout.addComponent(this.uploadInfo);
        layout.setMargin(true);
        layout.setSpacing(true);

        content.addComponents(layout, this.contact);
        content.setComponentAlignment(layout, Alignment.TOP_LEFT);

        main.addComponents(this.headerText, this.subHeader, content);
        main.setSpacing(true);
        main.setMargin(true);

        setContent(main);
        return main;
    }

    @Override
    protected void configureComponents() {
        // species text field
        this.headerText = new Label(this.header);
        this.headerText.addStyleName("h2");

        this.subHeader = new Label("Provide some general information for your data management plan");
        this.subHeader.addStyleName("colored");
        this.subHeader.addStyleName("small");

        this.spacer = new Label("&nbsp;", ContentMode.HTML);
        this.spacer.setHeight("1em");

        // project name text field
        this.projectName = new TextField("Project Name");
        this.projectName.setWidth(350.0f, Sizeable.Unit.PIXELS);

        // person in charge text field
        this.personInCharge = new TextField("Person in Charge");
        this.personInCharge.setMaxLength(50);
        this.personInCharge.setWidth(350.0f, Sizeable.Unit.PIXELS);

        // Upload
        this.content = new TsvUpload();
        this.content.setSlow(true);

        this.uploader = new Upload(null, content);
        final UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploader, content);
        uploader.setImmediate(false);
        uploader.setButtonCaption("Upload File");
        uploader.setCaption("Experiment Design Upload from QWizard.");
        uploader.addStartedListener(new Upload.StartedListener() {
            @Override
            public void uploadStarted(final Upload.StartedEvent event) {
                if (uploadInfoWindow.getParent() == null) {
                    UI.getCurrent().addWindow(uploadInfoWindow);
                }
                uploadInfoWindow.setClosable(false);
            }
        });
        uploader.addFinishedListener(new Upload.FinishedListener() {
            @Override
            public void uploadFinished(final Upload.FinishedEvent event) {
                uploadInfoWindow.setClosable(true);
                uploadedStuff = uploadInfoWindow.getUpload();
                uploadInfo.setValue(uploadInfoWindow.getFileName());

            }
        });

        // will be updated on upload
        this.uploadInfo = new Label("");
        this.uploadInfo.setCaption("Uploaded File:");
        this.uploadInfo.addStyleName("small");
        this.uploadInfo.addStyleName("colored");


        // contact data layout
        TextField institute = new TextField("Institute / Organization");
        TextField street = new TextField("Street");
        TextField zip = new TextField("ZIP-code");
        zip.setWidth(100.0f, Sizeable.Unit.PIXELS);

        TextField city = new TextField("City");
        TextField country = new TextField("Country");
        HorizontalLayout cityCode = new HorizontalLayout();
        cityCode.addComponents(zip, city);
        cityCode.setSpacing(true);
        this.contact = new VerticalLayout();
        this.contact.setMargin(true);
        this.contact.setSpacing(true);
        this.contact.addComponents(institute, street, cityCode, country);
        this.contact.addStyleName("wrapping");


    }

    @Override
    public String getTsvUpload(){
        return this.uploadedStuff;
    }

    @Override
    public void refreshComponents() {}
}
