package com;

import java.io.File;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
/**
 * Implements the progress bar for DMPcreator.
 * @author Sven Fillinger
 */
public class ProgressBarSven {

    //TODO can be removed, only for testing
    private FileResource barImage = new FileResource(new File(
            VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +
                    "/WEB-INF/images/bar.png"));

     /**
     * The layout for the progress bar
     */
    private Layout progressBarLayout;

    /**
     * Constructor for a progress bar object. Initiates the build
     * of a layout containing the progress labels and bar.
     * @param highLightLabel the label to highlight
     */
    public ProgressBarSven(String highLightLabel){
        init(highLightLabel);
    }

    /**
     * Builds the progress bar from the user slide elements in the
     * LinkedList and returns it as a finished layout that can then be
     * implemented in the parent layout container.
     */
    public void init(String highLightLabel){
        /*
        The vertical layout will contain two horizontal layouts,
        one containing the bar labels, one the progress bar
        */
        VerticalLayout barComplete = new VerticalLayout();
        HorizontalLayout barLabels = new HorizontalLayout();
        HorizontalLayout barLine = new HorizontalLayout();
        // set spacing
        barComplete.setSpacing(true);
        // the width for the progress bar
        float barDisplayRatio = 100.0f/
                (UserSlideList.userSlides.size());
        // the offset for stopping in the middle of an progress label
        float offset = barDisplayRatio/2.0f;

        // Build the labels, which are on top, read out from the userSlides list
        Label tempLabel;
        for(String elem : UserSlideList.userSlides) {
            tempLabel = new Label(elem);
            tempLabel.addStyleName("v-align-center");
            tempLabel.addStyleName("small");
            if (highLightLabel.equals(elem)){
                tempLabel.addStyleName("colored");
                tempLabel.addStyleName("bold");
            }
            barLabels.addComponent(tempLabel);
            barLabels.setComponentAlignment(tempLabel, Alignment.TOP_CENTER);
        }
        barLabels.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        barLabels.setHeightUndefined();
        // add the bar labels to the complete progress bar layout
        barComplete.addComponent(barLabels);
        // define and add the progress bar as image to the layout
        Image image = new Image(null, barImage);
        image.setWidth(barDisplayRatio*(UserSlideList.userSlides.indexOf(highLightLabel)+1)
                - offset, Sizeable.Unit.PERCENTAGE);
        image.setHeight(5.0f, Sizeable.Unit.PIXELS);

        barLine.addComponent(image);
        barLine.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        barComplete.addComponent(barLine);
        // set the member variable with the progress bar layout
        this.setProgressBarLayout(barComplete);
    }

     /**
     * Returns the current progress bar layout
     * @return (Layout) progressBarLayout
     */
    public Layout getProgressBarLayout() {
        return this.progressBarLayout;
    }

    /**
     * Sets the progress bar layout
     * @param progressBarLayout a progress bar layout
     */
    public void setProgressBarLayout(Layout progressBarLayout) {
        this.progressBarLayout = progressBarLayout;
    }
}
