package com;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.data.Property;
import com.vaadin.server.FileDownloader;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.combobox.FilteringMode;
import com.vaadin.ui.*;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Set;

@Title("FirstStepsTest")
@Theme("valo")

/**
 * Created by heumos on 5/27/15.
 */
public class FirstStepsTest extends UI {

    protected Button saveBtn;
    protected ComboBox dataTypes;
    protected TextArea dataTypeDescription;

    @Override
    public void init(VaadinRequest request) {
        Button downloadButton = new Button("Download image");

        StreamResource myResource = null;
        try {
            myResource = createResource();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileDownloader fileDownloader = new FileDownloader(myResource);
        fileDownloader.extend(downloadButton);


        // configure Components

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
        dataTypes.setWidth(100.0f, Unit.PERCENTAGE);

        // Set the appropriate filtering mode for this example
        dataTypes.setFilteringMode(FilteringMode.CONTAINS);
        dataTypes.setImmediate(true);

        // Disallow null selections
        dataTypes.setNullSelectionAllowed(false);

        dataTypeDescription = new TextArea("Person In Charge.");


        // buildLayout
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.addComponents(this.dataTypes, this.dataTypeDescription);
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(horizontalLayout);
        layout.addComponent(downloadButton);
        setContent(layout);


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

}
