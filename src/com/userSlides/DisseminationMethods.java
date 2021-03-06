package com.userSlides;

import com.TsvUpload;
import com.vaadin.data.Property;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.server.StreamResource;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import IO.PDFGenerator;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    private Button popupButton;
    private Button downloadReportButton;

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

        VerticalLayout dissMethod = new VerticalLayout();
        dissMethod.addComponents(this.dataTypes, this.popupButton);
        HorizontalLayout typeSelection = new HorizontalLayout();
        typeSelection.addComponents(dissMethod, this.dataTypeDescription);
        typeSelection.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        typeSelection.setSpacing(true);

        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(typeSelection);
        layout.addComponent(popupButton);
        layout.addComponent(this.addDataType);
        layout.addComponent(this.selection);
        layout.addComponent(this.removeDataType);
        layout.setSpacing(true);

        rightSideContainer = new VerticalLayout();
        rightSideContainer.addComponents(info, generateReportButton, downloadReportButton);
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

        this.subHeader = new Label("Provide some information " +
                "for your data management plan concerning the sharing and access rules of your data.");
        this.subHeader.addStyleName("colored");
        this.subHeader.addStyleName("small");


        selection = new Table("Already chosen methods.");
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
//        selection.addItem(new Object[]{"Fappening", "Perverse Stuff...."}, 2);
//        selection.addItem(new Object[]{"Sepp", "Platter"}, 3);

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
                    dataTypeDescription.setValue("");
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
        // Sets the icon to use with the items
        // dataTypes.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
        // Set full width
        dataTypes.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        dataTypes.setFilteringMode(FilteringMode.CONTAINS);
        dataTypes.setImmediate(true);

        // Disallow null selections
        dataTypes.setNullSelectionAllowed(false);

        popupButton = new Button("Add unfamiliar method.");
        popupButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                DisseminationMethodsSub sub = new DisseminationMethodsSub(dataTypes);

                // Add it to the root component
                UI.getCurrent().addWindow(sub);
            }
        });

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
                downloadReportButton.setEnabled(true);
            }
        });

        downloadReportButton = new Button("Download Report");
        downloadReportButton.setEnabled(false);

        StreamResource myResource = null;
        try {
            myResource = createResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader fileDownloader = new FileDownloader(myResource);
        fileDownloader.extend(downloadReportButton);
    }

    Component infoContent(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        Label content = new Label(
                "Determining the data dissemination clearyfies for every scientits when, where and how " +
                        "scientific data can be shared."
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

    private StreamResource createResource() throws IOException {
        return new StreamResource(new StreamResource.StreamSource() {
            @Override
            public InputStream getStream() {
                String pathToFile = "/opt/wildfly/bin/FirstPdfTest.pdf";
                byte[] pdf = null;
                try {
                    pdf = Files.readAllBytes(Paths.get(pathToFile));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    //  write here to stream
                    bos.write(pdf);
                    return new ByteArrayInputStream(bos.toByteArray());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }

            }
        }, "DataManagementPlan.pdf");
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