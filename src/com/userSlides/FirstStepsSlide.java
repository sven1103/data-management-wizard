package com.userSlides;

import com.TsvUpload;
import com.UploadInfoWindow;
import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

/**
 * Created by heumos on 5/27/15.
 */
public class FirstStepsSlide extends AUserSlide {

    private TextField species;
    private TextField projectName;
    private TextField personInCharge;
    private HorizontalLayout contact;
    private Upload uploader;

    public FirstStepsSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(this.species);
        layout.addComponent(this.projectName);
        layout.addComponent(this.contact);
        layout.addComponent(this.personInCharge);
        layout.addComponent(this.uploader);
        layout.setExpandRatio(this.species, 1);
        setContent(layout);
        return layout;
    }

    @Override
    protected void configureComponents() {
        // species text field
        this.species = new TextField("Species");
        this.species.setValue("Enter Species Name here.");
        this.species.setMaxLength(40);
        this.species.setColumns(25);
        this.species.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                // TODO implement this!
            }
        });

        // project name text field
        this.projectName = new TextField("Project Name");
        this.projectName.setValue("Dumm Dumm Dumm");

        // person in charge text field
        this.personInCharge = new TextField("Person in Charge");
        this.personInCharge.setMaxLength(40);

        // Upload
        TsvUpload tsvUpload = new TsvUpload();
        tsvUpload.setSlow(true);

        this.uploader = new Upload(null, tsvUpload);
        final UploadInfoWindow uploadInfoWindow = new UploadInfoWindow(uploader, tsvUpload);
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
            }
        });

        // contact data layout
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField phone = new TextField("Phone");
        TextField email = new TextField("Email");
        DateField birthDate = new DateField("Birth date");
        this.contact = new HorizontalLayout();
        this.contact.setCaption("Contact Formular");
        this.contact.setMargin(true);
        this.contact.setSpacing(true);
        this.contact.addComponents(firstName, lastName, phone, email, birthDate);
    }
}
