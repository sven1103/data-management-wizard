package com.userSlides;

import com.vaadin.data.Property;
import com.vaadin.event.FieldEvents;
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

    private Label peptidesSpaceLabel;
    private Label dnaSpaceLabel;

    private TextField peptidesSpaceField;
    private TextField dnaSpaceField;
    private Label totalSpace;
    private Label requiredSpace;


    private TextField storageLocation;
    private ComboBox backupSolution;
    private ComboBox archieveSolution;
    private Panel info;

    private double totSpace;
    private double reqSpace;
    private int totExpDna;
    private int totExpPeptides;
    private HashMap<String, Double> raidMap;

    // TODO add getter and setter
    private HashMap<String, Integer> expMap;
    private String reference;

    public StorageBackupSlide(String header) {
        super(header);
        this.totExpDna = 0;
        this.totExpPeptides = 0;
        this.totSpace = 0;
        this.reqSpace = 0;
        initHashMapRef();
    }

    @Override
    protected Layout buildLayout() {
        // buildLayout
        VerticalLayout main = new VerticalLayout();

        HorizontalLayout content = new HorizontalLayout();

        VerticalLayout roleType = new VerticalLayout();
        roleType.addComponents(this.backupSolution);
        roleType.addComponent(peptidesSpaceLabel);
        roleType.addComponent(dnaSpaceLabel);
        VerticalLayout space = new VerticalLayout();
        space.addComponents(this.archieveSolution, this.peptidesSpaceField, this.dnaSpaceField);
        HorizontalLayout typeSelection = new HorizontalLayout();
        typeSelection.addComponents(roleType, space);
        typeSelection.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        typeSelection.setSpacing(true);
        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(typeSelection);
        layout.setSpacing(true);
        layout.addComponent(totalSpace);
        layout.addComponent(requiredSpace);

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
        backupSolution.addValueChangeListener(new Property.ValueChangeListener() {
            @Override
            public void valueChange(Property.ValueChangeEvent event) {
                // Assuming that the value type is a String
                String value = (String) event.getProperty().getValue();

                // Do something with the value
                Integer dna = Integer.parseInt(dnaSpaceField.getValue());
                Integer peptides = Integer.parseInt(peptidesSpaceField.getValue());
                totSpace = totExpDna*dna + totExpPeptides*peptides;
                totalSpace.setValue("Total space in GB needed: " + totSpace);
                reqSpace = calcReqSpace();
                requiredSpace.setValue("Required space for storage/backup chosen RAID solution in GB: " + reqSpace);
            }
        });

        archieveSolution = new ComboBox("Select your archive solution.");
        archieveSolution.setInputPrompt("No archive solution.");
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

        peptidesSpaceLabel = new Label("PEPTIDES Space");
        peptidesSpaceLabel.setValue("Approximate disk space in GB needed for one PEPTIDES experiment:");
        dnaSpaceLabel = new Label("DNA Space");
        dnaSpaceLabel.setValue("Approximate disk space in GB needed for one DNA experiment:");

        peptidesSpaceField = new TextField("PEPTIDES Space");
        peptidesSpaceField.setImmediate(true);
        peptidesSpaceField.setValue("20");
        peptidesSpaceField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                // Assuming that the value type is a String
                String value = (String) event.getText();

                // Do something with the value
                Integer dna = Integer.parseInt(dnaSpaceField.getValue());
                Integer peptides = Integer.parseInt(value);
                totSpace = totExpDna * dna + totExpPeptides * peptides;
                totalSpace.setValue("Total space in GB needed: " + totSpace);
                reqSpace = calcReqSpace();
                requiredSpace.setValue("Required space for storage/backup chosen RAID solution in GB: " + reqSpace);
            }
        });
        dnaSpaceField = new TextField("DNA Space");
        dnaSpaceField.setImmediate(true);
        dnaSpaceField.setValue("500");
        dnaSpaceField.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                // Assuming that the value type is a String
                String value = (String) event.getText();

                if (!value.isEmpty()) {
                    // Do something with the value
                    Integer peptides = Integer.parseInt(peptidesSpaceField.getValue());
                    Integer dna = Integer.parseInt(value);
                    totSpace = totExpDna*dna + totExpPeptides*peptides;
                    totalSpace.setValue("Total space in GB needed: " + totSpace);
                    reqSpace = calcReqSpace();
                    requiredSpace.setValue("Required space for storage/backup chosen RAID solution in GB: " + reqSpace);
                }
            }
        });

        totalSpace = new Label("Total Space");
        totalSpace.setValue("Total space in GB needed: " + this.totSpace);
        requiredSpace = new Label("Required Space");
        requiredSpace.setValue("Required space in GB: " + this.reqSpace);
    }

    private double calcReqSpace() {
        String raid = "";
        if (!this.backupSolution.isEmpty()) {
            raid = (String) this.backupSolution.getValue();
        }
        double factor = 0.0;
        if (this.raidMap.containsKey(raid)) {
            factor = this.raidMap.get(raid);
        }
        return totSpace + totSpace*factor;
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

    private void initHashMapRef() {
        HashMap<String, Integer> hM = new HashMap<String, Integer>();
        hM.put("DNA", 10);
        hM.put("PEPTIDES", 10);
        this.expMap = hM;
        this.reference = "Homo sapiens";
        HashMap<String, Double> hMM = new HashMap<String, Double>();
        hMM.put("RAID 1", 1.0);
        hMM.put("RAID 5", 0.125);
        hMM.put("RAID 6", 0.33);
        hMM.put("RAID 10", 1.0);
        this.raidMap = hMM;
    }

    public void setStartValues(HashMap<String, Integer> expMap) {
        this.expMap = expMap;
        this.totExpDna = expMap.get("DNA");
        this.totExpPeptides = expMap.get("PEPTIDES");
        this.totSpace = this.totExpDna*500 + this.totExpPeptides*20;
        this.reqSpace = calcReqSpace();
        totalSpace.setValue("Total space in GB needed: " + totSpace);
        requiredSpace.setValue("Required space for storage/backup chosen RAID solution in GB: " + reqSpace);
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

    public Label getPeptidesSpaceLabel() {
        return peptidesSpaceLabel;
    }

    public void setPeptidesSpaceLabel(Label peptidesSpaceLabel) {
        this.peptidesSpaceLabel = peptidesSpaceLabel;
    }

    public Label getDnaSpaceLabel() {
        return dnaSpaceLabel;
    }

    public void setDnaSpaceLabel(Label dnaSpaceLabel) {
        this.dnaSpaceLabel = dnaSpaceLabel;
    }

    public TextField getPeptidesSpaceField() {
        return peptidesSpaceField;
    }

    public void setPeptidesSpaceField(TextField peptidesSpaceField) {
        this.peptidesSpaceField = peptidesSpaceField;
    }

    public TextField getDnaSpaceField() {
        return dnaSpaceField;
    }

    public void setDnaSpaceField(TextField dnaSpaceField) {
        this.dnaSpaceField = dnaSpaceField;
    }

    public Label getTotalSpace() {
        return totalSpace;
    }

    public void setTotalSpace(Label totalSpace) {
        this.totalSpace = totalSpace;
    }

    public Label getRequiredSpace() {
        return requiredSpace;
    }

    public void setRequiredSpace(Label requiredSpace) {
        this.requiredSpace = requiredSpace;
    }

    public TextField getStorageLocation() {
        return storageLocation;
    }

    public void setStorageLocation(TextField storageLocation) {
        this.storageLocation = storageLocation;
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

    public double getTotSpace() {
        return totSpace;
    }

    public void setTotSpace(double totSpace) {
        this.totSpace = totSpace;
    }

    public double getReqSpace() {
        return reqSpace;
    }

    public void setReqSpace(double reqSpace) {
        this.reqSpace = reqSpace;
    }

    public int getTotExpDna() {
        return totExpDna;
    }

    public void setTotExpDna(int totExpDna) {
        this.totExpDna = totExpDna;
    }

    public int getTotExpPeptides() {
        return totExpPeptides;
    }

    public void setTotExpPeptides(int totExpPeptides) {
        this.totExpPeptides = totExpPeptides;
    }

    public HashMap<String, Double> getRaidMap() {
        return raidMap;
    }

    public void setRaidMap(HashMap<String, Double> raidMap) {
        this.raidMap = raidMap;
    }

    public HashMap<String, Integer> getExpMap() {
        return expMap;
    }

    public void setExpMap(HashMap<String, Integer> expMap) {
        this.expMap = expMap;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String getTsvUpload(){
        return "";
    }

    @Override
    public void refreshComponents(){};
}
