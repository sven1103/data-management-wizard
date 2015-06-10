package com.userSlides;

import com.TsvUpload;
import com.vaadin.data.Property;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import IO.PDFGenerator;

import java.util.Set;

/**
 * Created by heumos on 6/5/15.
 */
public class DisseminationMethods extends AUserSlide {

    private Label headerText;
    private Label subHeader;
    private Button addDataType;
    private Button removeDataType;
    private ComboBox dataTypes;
    private TextArea dataTypeDescription;

    private Table selection;
    private Panel info;
    private Button generateReportButton;
    private VerticalLayout rightSideContainer;

    private static PDFGenerator pdfGenerator;

    public DisseminationMethods(String header) {
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
        layout.addComponent(this.addDataType);
        layout.addComponent(this.selection);
        layout.addComponent(this.removeDataType);
        layout.setSpacing(true);

        rightSideContainer = new VerticalLayout();
        rightSideContainer.addComponents(info, generateReportButton);
        content.addComponent(layout);
        content.addComponent(rightSideContainer);
        content.setSpacing(true);
        content.setMargin(true);
        content.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        rightSideContainer.setComponentAlignment(info, Alignment.TOP_CENTER);
        rightSideContainer.setComponentAlignment(generateReportButton, Alignment.BOTTOM_CENTER);
        rightSideContainer.setSpacing(true);
        rightSideContainer.setMargin(true);

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


        selection = new Table("Already Selected.");
        // Define two columns for the built-in container
        selection.addContainerProperty("Method", String.class, null);
        selection.addContainerProperty("Description", String.class, null);
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
        selection.addItem(new Object[]{"Fappening", "Perverse Stuff...."}, 2);
        selection.addItem(new Object[]{"Sepp", "Platter"}, 3);

        addDataType = new Button("Add Method");
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

        removeDataType = new Button("Delete Method(s)");
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
        dataTypes = new ComboBox("Select your dissemination method.");
        dataTypes.setInputPrompt("No method selected.");
        dataTypes.setInvalidAllowed(false);
        dataTypes.setNullSelectionAllowed(false);
        dataTypes.addItem("Public Access");
        dataTypes.addItem("Journal");
        dataTypes.addItem("Web");
        dataTypes.addItem("Media");
        dataTypes.addItem("Others...");
        // Sets the icon to use with the items
        // dataTypes.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
        // Set full width
        dataTypes.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        dataTypes.setFilteringMode(FilteringMode.CONTAINS);
        dataTypes.setImmediate(true);

        // Disallow null selections
        dataTypes.setNullSelectionAllowed(false);

        dataTypeDescription = new TextArea("Description");
        dataTypeDescription.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);


        info = new Panel("About Dissemination");
        info.setIcon(FontAwesome.INFO_CIRCLE);
        info.addStyleName("well");
        info.setWidth(300.0f, Sizeable.Unit.PIXELS);
        info.setContent(infoContent());

        generateReportButton = generateReport();
        pdfGenerator = new PDFGenerator();
        generateReportButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent clickEvent) {
                pdfGenerator.writePDF();
            }
        });
    }

    Component infoContent(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        Label content = new Label(
                "Spiel nicht an deinem Pillermann sonst geht er an die Eier ran."
        );
        layout.addComponent(content);
        return layout;
    }

    Button generateReport(){
        generateReportButton = new Button("Generate Report");
        generateReportButton.addStyleName("huge");
        generateReportButton.addStyleName("primary");
        generateReportButton.setIcon(FontAwesome.ARCHIVE);
        return generateReportButton;
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

    public Button getGenerateReportButton() {
        return generateReportButton;
    }

    public void setGenerateReportButton(Button generateReportButton) {
        this.generateReportButton = generateReportButton;
    }

    public VerticalLayout getRightSideContainer() {
        return rightSideContainer;
    }

    public void setRightSideContainer(VerticalLayout rightSideContainer) {
        this.rightSideContainer = rightSideContainer;
    }

    public static PDFGenerator getPdfGenerator() {
        return pdfGenerator;
    }

    public static void setPdfGenerator(PDFGenerator pdfGenerator) {
        DisseminationMethods.pdfGenerator = pdfGenerator;
    }
}