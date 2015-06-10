package com.userSlides;

import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.Set;

/**
 * Created by heumos on 6/5/15.
 */
public class RolesResponsibilitiesSlide extends AUserSlide {

    private Label headerText;
    private Label subHeader;
    private Button addDataType;
    private Button removeDataType;
    private ComboBox dataTypes;
    private TextArea dataTypeDescription;
    private Button popupButton;

    private Table selection;
    private Panel info;

    public RolesResponsibilitiesSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        VerticalLayout main = new VerticalLayout();

        HorizontalLayout content = new HorizontalLayout();

        VerticalLayout roleType = new VerticalLayout();
        roleType.addComponents(this.dataTypes, this.popupButton);
        HorizontalLayout typeSelection = new HorizontalLayout();
        typeSelection.addComponents(roleType, this.dataTypeDescription);
        typeSelection.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        typeSelection.setSpacing(true);

        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(typeSelection);
        layout.addComponent(popupButton);
        layout.addComponent(this.addDataType);
        layout.addComponent(this.selection);
        layout.addComponent(this.removeDataType);
        layout.setSpacing(true);

        content.addComponent(layout);
        content.addComponent(info);
        content.setSpacing(true);
        content.setMargin(true);
        content.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        content.setComponentAlignment(info, Alignment.TOP_CENTER);
        main.addComponents(this.headerText, this.subHeader, content);
        main.setSpacing(true);
        main.setMargin(true);
        setContent(main);
        return main;
    }

    @Override
    protected void configureComponents() {

        // configure Components

        this.headerText = new Label(this.header);
        this.headerText.addStyleName("h2");

        this.subHeader = new Label("Provide some general information for your data management plan");
        this.subHeader.addStyleName("colored");
        this.subHeader.addStyleName("small");


        selection = new Table("Already chosen responsibilities.");
        // Define two columns for the built-in container
        selection.addContainerProperty("Role_Type", String.class, null);
        selection.addContainerProperty("Person_In_Charge", String.class, null);
        // Allow selecting items from the table.
        selection.setSelectable(true);
        // Send changes in selection immediately to server.
        selection.setImmediate(true);
        selection.setMultiSelect(true);
        selection.setPageLength(selection.size());
        selection.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        // Handle selection change.
        selection.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                Notification.show("Selected: " + selection.getValue(),
                        Notification.Type.TRAY_NOTIFICATION);
            }
        });
//        selection.addItem(new Object[]{"Fappening", "Perverse Stuff...."}, 2);
//        selection.addItem(new Object[]{"Sepp", "Platter"}, 3);

        addDataType = new Button("Add Responsibility");
        addDataType.addStyleName("friendly");
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

        removeDataType = new Button("Delete Responsibility");
        removeDataType.addStyleName("danger");
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

        popupButton = new Button("Add unfamiliar role.");
        popupButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                RolesResponsibilitiesSub sub = new RolesResponsibilitiesSub(dataTypes);

                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });

        dataTypeDescription = new TextArea("Person In Charge.");
        dataTypeDescription.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);


        info = new Panel("About Roles and Responsibilities");
        info.setIcon(FontAwesome.INFO_CIRCLE);
        info.addStyleName("well");
        info.setWidth(300.0f, Sizeable.Unit.PIXELS);
        info.setContent(infoContent());
    }

    Component infoContent(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        Label content = new Label(
          "Scientists being aware of their roles & responsibilities" +
                  " maintain an efficient and productive working environment for everyone."
        );
        layout.addComponent(content);
        return layout;
    }

    @Override
    public String getTsvUpload(){
        return "";
    }

    @Override
    public void refreshComponents(){};
}
