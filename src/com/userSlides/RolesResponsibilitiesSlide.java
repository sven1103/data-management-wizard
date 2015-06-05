package com.userSlides;

import com.vaadin.data.Property;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.Set;

/**
 * Created by heumos on 6/5/15.
 */
public class RolesResponsibilitiesSlide extends AUserSlide {

    protected Button addDataType;
    protected Button removeDataType;
    protected ComboBox dataTypes;
    protected TextArea dataTypeDescription;

    protected Table selection;

    public RolesResponsibilitiesSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(this.addDataType, this.dataTypes, this.dataTypeDescription);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(horizontalLayout);
        layout.addComponent(this.selection);
        layout.addComponent(this.removeDataType);
        setContent(layout);
        return layout;
    }

    @Override
    protected void configureComponents() {
        // configure Components
        selection = new Table("Already Selected.");
        // Define two columns for the built-in container
        selection.addContainerProperty("Role_Type", String.class, null);
        selection.addContainerProperty("Person_In_Charge",  String.class, null);
        // Allow selecting items from the table.
        selection.setSelectable(true);
        // Send changes in selection immediately to server.
        selection.setImmediate(true);
        selection.setMultiSelect(true);
        // Handle selection change.
        selection.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Notification.show("Selected: " + selection.getValue(),
                        Notification.Type.TRAY_NOTIFICATION);
            }
        });
        selection.addItem(new Object[]{"Fappening", "Perverse Stuff...."}, 2);
        selection.addItem(new Object[]{"Sepp", "Platter"}, 3);

        addDataType = new Button("+");
        addDataType.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (!dataTypes.isEmpty()) {
                    Notification.show(dataTypes.getValue().toString(),
                            Notification.Type.TRAY_NOTIFICATION);
                    String dataType = dataTypes.getValue().toString();
                    String description = dataTypeDescription.getValue().toString();
                    StringBuilder sB = new StringBuilder();
                    sB.append(dataType);
                    sB.append(description);
                    selection.addItem(new Object[]{dataType, description}, sB.toString().hashCode());
                }
            }
        });

        removeDataType = new Button("-");
        removeDataType.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                // get the current selected rows as a set
                Set<Integer> selectedVals = (Set<Integer>) selection.getValue();
                for (Integer itemId : selectedVals) {
                    selection.removeItem(itemId);
                }
            }
        });

        // Creates a new combobox using an existing container
        dataTypes = new ComboBox("Select your role type.");
        dataTypes.setInputPrompt("No role type selected.");
        dataTypes.setInvalidAllowed(false);
        dataTypes.setNullSelectionAllowed(false);
        dataTypes.addItem("Data Owners");
        dataTypes.addItem("Data Managers");
        dataTypes.addItem("Data Contributors");
        dataTypes.addItem("Data Researchers");
        // Sets the icon to use with the items
        // dataTypes.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
        // Set full width
        dataTypes.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        dataTypes.setFilteringMode(FilteringMode.CONTAINS);
        dataTypes.setImmediate(true);

        // Disallow null selections
        dataTypes.setNullSelectionAllowed(false);

        dataTypeDescription = new TextArea("Person In Charge.");
    }
}
