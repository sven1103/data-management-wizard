package com;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Item;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

@Title("FirstStepsTest")
@Theme("valo")

/**
 * Created by heumos on 5/27/15.
 */
public class FirstStepsTest extends UI {

    protected Button addDocType;
    protected ComboBox dataTypes;
    protected TextArea description;

    protected Table selection;

    @Override
    public void init(VaadinRequest request) {


        // configure Components
        selection = new Table("Already Selected.");

        addDocType = new Button("+");
        addDocType.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                Notification.show(dataTypes.getValue().toString(),
                        Notification.Type.TRAY_NOTIFICATION);
                description.setValue(dataTypes.getValue().toString());
            }
        });

        // Creates a new combobox using an existing container
        dataTypes = new ComboBox("Select your datatype.");
        dataTypes.setInputPrompt("No datatype selected.");
        dataTypes.setInvalidAllowed(false);
        dataTypes.setNullSelectionAllowed(false);
        dataTypes.addItem("DOCX");
        dataTypes.addItem("XLS");
        dataTypes.addItem("PDF");
        dataTypes.addItem("TEX");
        dataTypes.addItem("PNG");
        // Sets the icon to use with the items
        // dataTypes.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
        // Set full width
        dataTypes.setWidth(100.0f, Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        dataTypes.setFilteringMode(FilteringMode.CONTAINS);
        dataTypes.setImmediate(true);

        // Disallow null selections
        dataTypes.setNullSelectionAllowed(false);

        description = new TextArea("Description");
        description.setValue("Enter description here.");

        // buildLayout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(this.addDocType, this.dataTypes, this.description);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(horizontalLayout);
        layout.addComponent(this.selection);
        setContent(layout);

    }

}
