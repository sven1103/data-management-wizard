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
public class DocContManagementSlide extends AUserSlide {

    private Label headerText;
    private Label subHeader;
    private Button addDataType;
    private Button removeDataType;
    private ComboBox dataTypes;
    private TextArea dataTypeDescription;
    private Button popupButton;

    private Table selection;
    private Panel info;

    public DocContManagementSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout

        VerticalLayout main = new VerticalLayout();

        HorizontalLayout content = new HorizontalLayout();

        HorizontalLayout typeSelection = new HorizontalLayout();
        typeSelection.addComponents(this.dataTypes, this.dataTypeDescription);
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

        this.subHeader = new Label("Please specify here which data types including content will " +
                                "be occurring during the project.");
        this.subHeader.addStyleName("colored");
        this.subHeader.addStyleName("small");

        selection = new Table("Already chosen contents.");
        // Define two columns for the built-in container
        selection.addContainerProperty("Datatype", String.class, null);
        selection.addContainerProperty("Description",  String.class, null);
        // Allow selecting items from the table.
        selection.setSelectable(true);
        // Send changes in selection immediately to server.
        selection.setImmediate(true);
        // Shows feedback from selection.
        final Label current = new Label("Selected: -");
        selection.setMultiSelect(true);
        selection.setPageLength(selection.size());
        selection.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        // Handle selection change.
        selection.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                current.setValue("Selected: " + selection.getValue());
            }
        });
//        selection.addItem(new Object[]{"Fappening", "Perverse Stuff...."}, 2);
//        selection.addItem(new Object[]{"Sepp",        "Platter"}, 3);

        addDataType = new Button("Add Content");
        addDataType.addStyleName("friendly");
        addDataType.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                if (!dataTypes.isEmpty()) {
                    Notification.show(dataTypes.getValue().toString(),
                            Notification.Type.TRAY_NOTIFICATION);
                    String datatype = dataTypes.getValue().toString();
                    String descriptio = dataTypeDescription.getValue().toString();
                    StringBuilder sB = new StringBuilder();
                    sB.append(datatype);
                    sB.append(descriptio);
                    selection.addItem(new Object[]{datatype, descriptio}, sB.toString().hashCode());
                }
            }
        });

        removeDataType = new Button("Remove Content");
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
        dataTypes = new ComboBox("Select your data type.");
        dataTypes.setInputPrompt("No data type selected.");
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
        dataTypes.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        dataTypes.setFilteringMode(FilteringMode.CONTAINS);
        dataTypes.setImmediate(true);

        // Disallow null selections
        dataTypes.setNullSelectionAllowed(false);

        popupButton = new Button("Add unfamiliar data type.");
        popupButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                DocContManagementSub sub = new DocContManagementSub(dataTypes);

                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });

        dataTypeDescription = new TextArea("Description");
        dataTypeDescription.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        info = new Panel("About Content Management");
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
                "Having a clear overview over current work progresses and processes is the role of " +
                                        "content management.");
        layout.addComponent(content);
        return layout;
    }

    @Override
    public String getTsvUpload(){
        return "";
    }

    @Override
    public void refreshComponents(){}

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

    public Button getAddDataType() {
        return addDataType;
    }

    public void setAddDataType(Button addDataType) {
        this.addDataType = addDataType;
    }

    public Button getRemoveDataType() {
        return removeDataType;
    }

    public void setRemoveDataType(Button removeDataType) {
        this.removeDataType = removeDataType;
    }

    public ComboBox getDataTypes() {
        return dataTypes;
    }

    public void setDataTypes(ComboBox dataTypes) {
        this.dataTypes = dataTypes;
    }

    public TextArea getDataTypeDescription() {
        return dataTypeDescription;
    }

    public void setDataTypeDescription(TextArea dataTypeDescription) {
        this.dataTypeDescription = dataTypeDescription;
    }

    public Button getPopupButton() {
        return popupButton;
    }

    public void setPopupButton(Button popupButton) {
        this.popupButton = popupButton;
    }

    public Table getSelection() {
        return selection;
    }

    public void setSelection(Table selection) {
        this.selection = selection;
    }

    public Panel getInfo() {
        return info;
    }

    public void setInfo(Panel info) {
        this.info = info;
    }
}