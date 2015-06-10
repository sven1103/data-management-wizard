package com.userSlides;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Sizeable;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;

import java.util.HashMap;

/**
 * Created by heumos on 6/5/15.
 */
public class StorageBackupSlide extends AUserSlide {

    private Label headerText;
    private Label subHeader;
    private Label peptidesSpace;
    private TextField storageLocation;
    private ComboBox backupSolution;
    private ComboBox archieveSolution;
    private Panel info;

    // TODO add getter and setter
    private HashMap<String, Integer> expMap;

    public StorageBackupSlide(String header) {
        super(header);
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        VerticalLayout main = new VerticalLayout();

        HorizontalLayout content = new HorizontalLayout();

        VerticalLayout roleType = new VerticalLayout();
        roleType.addComponents(this.backupSolution);
        roleType.addComponent(peptidesSpace);
        HorizontalLayout typeSelection = new HorizontalLayout();
        typeSelection.addComponents(roleType, this.archieveSolution);
        typeSelection.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        typeSelection.setSpacing(true);

        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(typeSelection);
        layout.setSpacing(true);

        content.addComponent(layout);
        content.addComponent(info);
        content.setSpacing(true);
        content.setMargin(true);
        content.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        content.setComponentAlignment(info, Alignment.TOP_CENTER);
        main.addComponents(this.headerText, this.subHeader, this.storageLocation, content);
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

        this.subHeader = new Label("This section covers the topic of data storage/backup and archieve.");
        this.subHeader.addStyleName("colored");
        this.subHeader.addStyleName("small");

        this.storageLocation = new TextField("Storage Location");
        this.storageLocation.setValue("Enter location...");

        // Creates a new combobox using an existing container
        backupSolution = new ComboBox("Select your backup solution.");
        backupSolution.setInputPrompt("No backup solution.");
        backupSolution.setInvalidAllowed(false);
        backupSolution.setNullSelectionAllowed(false);
        backupSolution.addItem("RAID 1");
        backupSolution.addItem("RAID 5");
        backupSolution.addItem("RAID 6");
        backupSolution.addItem("RAID 10");
        // Sets the icon to use with the items
        // backupSolution.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
        // Set full width
        backupSolution.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        backupSolution.setFilteringMode(FilteringMode.CONTAINS);
        backupSolution.setImmediate(true);

        // Disallow null selections
        backupSolution.setNullSelectionAllowed(false);

        archieveSolution = new ComboBox("Select your archieve solution.");
        archieveSolution.setInputPrompt("No backup solution.");
        archieveSolution.setInvalidAllowed(false);
        archieveSolution.setNullSelectionAllowed(false);
        archieveSolution.addItem("TAPE");
        archieveSolution.addItem("RAID 1");
        // Sets the icon to use with the items
        // backupSolution.setItemIconPropertyId(ExampleUtil.iso3166_PROPERTY_FLAG);
        // Set full width
        archieveSolution.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        archieveSolution.setFilteringMode(FilteringMode.CONTAINS);
        archieveSolution.setImmediate(true);

        // Disallow null selections
        archieveSolution.setNullSelectionAllowed(false);


        info = new Panel("About Storage and Backup");
        info.setIcon(FontAwesome.INFO_CIRCLE);
        info.addStyleName("well");
        info.setWidth(300.0f, Sizeable.Unit.PIXELS);
        info.setContent(infoContent());

        peptidesSpace = new Label("PEPTIDES Space");
        peptidesSpace.setValue("Approximate space needed for one PEPTIDES experiment: 20GB.");
    }

    Component infoContent(){
        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        Label content = new Label(
                "The determination of rules for storage & backup" +
                        " contributes to a complete data management plan."
        );
        layout.addComponent(content);
        return layout;
    }

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

    public ComboBox getBackupSolution() {
        return backupSolution;
    }

    public void setBackupSolution(ComboBox backupSolution) {
        this.backupSolution = backupSolution;
    }

    public ComboBox getArchieveSolution() {
        return archieveSolution;
    }

    public void setArchieveSolution(ComboBox archieveSolution) {
        this.archieveSolution = archieveSolution;
    }

    public Panel getInfo() {
        return info;
    }

    public void setInfo(Panel info) {
        this.info = info;
    }

    @Override
    public String getTsvUpload(){
        return "";
    }

    @Override
    public void refreshComponents(){};
}
