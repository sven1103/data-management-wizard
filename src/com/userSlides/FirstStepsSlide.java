package com.userSlides;

import com.TsvUpload;
import com.UploadInfoWindow;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

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
    private TsvUpload tsvContent = null;
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
        this.tsvContent = new TsvUpload();
        this.tsvContent.setSlow(true);

        this.uploader = new Upload(null, tsvContent);
        final UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploader, tsvContent);
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

    public Label getHeaderText() {
        return headerText;
    }

    public void setHeaderText(Label headerText) {
        this.headerText = headerText;
    }

    public Label getSubHeader() {
        return subHeader;
    }

    public void setSubHeader(Label subHeader) {
        this.subHeader = subHeader;
    }

    public TextField getProjectName() {
        return projectName;
    }

    public void setProjectName(TextField projectName) {
        this.projectName = projectName;
    }

    public TextField getPersonInCharge() {
        return personInCharge;
    }

    public void setPersonInCharge(TextField personInCharge) {
        this.personInCharge = personInCharge;
    }

    public VerticalLayout getContact() {
        return contact;
    }

    public void setContact(VerticalLayout contact) {
        this.contact = contact;
    }

    public Upload getUploader() {
        return uploader;
    }

    public void setUploader(Upload uploader) {
        this.uploader = uploader;
    }

    public TsvUpload getTsvContent() {
        return tsvContent;
    }

    public void setTsvContent(TsvUpload tsvContent) {
        this.tsvContent = tsvContent;
    }

    public String getUploadedStuff() {
        return uploadedStuff;
    }

    public void setUploadedStuff(String uploadedStuff) {
        this.uploadedStuff = uploadedStuff;
    }

    public Label getSpacer() {
        return spacer;
    }

    public void setSpacer(Label spacer) {
        this.spacer = spacer;
    }

    public Label getUploadInfo() {
        return uploadInfo;
    }

    public void setUploadInfo(Label uploadInfo) {
        this.uploadInfo = uploadInfo;
    }
}
