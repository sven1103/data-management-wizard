package com.userSlides;

import com.vaadin.event.FieldEvents;
import com.vaadin.ui.*;

/**
 * Created by heumos on 5/27/15.
 */
public class SecondStepsSlide extends AUserSlide {

    private Button button;
    private TextField samplePlace;

    public SecondStepsSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(this.button);
        layout.addComponent(this.samplePlace);
        layout.setExpandRatio(this.button, 1);
        setContent(layout);
        return layout;
    }

    @Override
    protected void configureComponents() {
        // sample preparation place text field
        this.samplePlace = new TextField("Sample Place");
        this.samplePlace.setValue("Enter sample place here.");
        this.samplePlace.setMaxLength(40);
        this.samplePlace.setColumns(25);
        this.samplePlace.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent textChangeEvent) {
                // TODO implement this!
            }
        });

        // button
        this.button = new Button("Nothing to fappen here......");

    }
}
