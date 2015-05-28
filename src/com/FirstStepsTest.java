package com;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;


@Title("FirstStepsTest")
@Theme("valo")

/**
 * Created by heumos on 5/27/15.
 */
public class FirstStepsTest extends UI {

    protected TextField species;
    protected TextField projectName;
    protected TextField personInCharge;
    protected HorizontalLayout contact;

    @Override
    public void init(VaadinRequest request) {


        // configure Components
        species = new TextField("Species");
        species.setValue("Enter Species Name here.");
        species.setMaxLength(40);
        species.setColumns(25);

        projectName = new TextField("Project Name");
        projectName.setValue("Dumm Dumm Dumm");

        this.personInCharge = new TextField("Person in Charge");
        this.personInCharge.setMaxLength(40);

        species.addTextChangeListener(new TextChangeListener() {
            @Override
            public void textChange(TextChangeEvent textChangeEvent) {
                System.out.println(textChangeEvent.getText());
            }
        });

        // Upload
        TsvUpload tsvUpload = new TsvUpload();
        tsvUpload.setSlow(true);

        Upload uploader = new Upload(null, tsvUpload);
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

        // contact layout
        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        TextField phone = new TextField("Phone");
        TextField email = new TextField("Email");
        DateField birthDate = new DateField("Birth date");
        contact = new HorizontalLayout();
        contact.setCaption("Contact Formular");
        contact.setMargin(true);
        contact.setSpacing(true);
        contact.addComponents(firstName, lastName, phone, email, birthDate);

        // buildLayout
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(species);
        layout.addComponent(projectName);
        layout.addComponent(contact);
        layout.setExpandRatio(species, 1);
        layout.addComponent(uploader);
        setContent(layout);

    }

}
