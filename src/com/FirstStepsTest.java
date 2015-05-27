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

    @Override
    public void init(VaadinRequest request) {


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

        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(species);
        layout.addComponent(projectName);
        layout.setExpandRatio(species, 1);
        setContent(layout);

    }

}
