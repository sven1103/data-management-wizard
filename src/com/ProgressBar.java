package com;

import java.io.File;
import java.util.LinkedList;

import com.sun.xml.internal.bind.v2.TODO;
import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.*;
/**
 * Implements the progress bar for DMPcreator.
 * @author Sven Fillinger
 */
public class ProgressBar {

    //TODO can be removed, only for testing
    private int numberSlides = 7;
    private LinkedList<String> list = new LinkedList<String>();
    private FileResource barImage = new FileResource(new File(
            VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() +
                    "/WEB-INF/images/bar.png"));

    /**
     * A linked list with containing all user slides
     */
    private LinkedList<AUserSlide> userSlideList;
    /**
     * The layout for the progress bar
     */
    private Layout progressBarLayout;

    /**
     * Default constructor, sets no user slide list and should
     * only be used for testing.
     */
    public ProgressBar(){
        init();
    }
    /**
     * Constructor for a progress bar element.
     * @param _userSlideList: expects an LinkedList with user slides
     */
    public ProgressBar(LinkedList<AUserSlide> _userSlideList){
        this.userSlideList = _userSlideList;
        init();
    }

    /**
     * Builds the progress bar from the user slide elements in the
     * LinkedList and returns it as a finished layout that can then be
     * implemented in the parent layout container.
     */
    public void init(){
        list.add("Einleitung");
        list.add("Allgemeines");
        list.add("Jipeee");
        list.add("Gehirntod");
        list.add("Superman");
        list.add("Gogogogo");
        /*
        The vertical layout will contain two horizontal layouts,
        one containing the bar labels, one the progress bar
        */
        VerticalLayout barComplete = new VerticalLayout();
        barComplete.setSpacing(true);
        // start with the labels, which are on top
        HorizontalLayout barLabels = new HorizontalLayout();
        HorizontalLayout barLine = new HorizontalLayout();
        Label tempLabel;
        for(String elem : list) {
            tempLabel = new Label(elem);
            tempLabel.addStyleName("v-align-center");
            tempLabel.addStyleName("small");
            barLabels.addComponent(tempLabel);
            barLabels.setComponentAlignment(tempLabel, Alignment.TOP_CENTER);
        }
        barLabels.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        barLabels.setHeightUndefined();
        // add the bar labels to the complete progress bar layout
        barComplete.addComponent(barLabels);

        Image image = new Image(null, barImage);
        image.setWidth((33.3f - 8.0f), Sizeable.Unit.PERCENTAGE);
        image.setHeight(5.0f, Sizeable.Unit.PIXELS);

        barLine.addComponent(image);
        barLine.setWidth(100.0f, Sizeable.Unit.PERCENTAGE);
        barComplete.addComponent(barLine);


        barComplete.addStyleName("");
        barComplete.addComponent(new Label(String.valueOf(barComplete.getComponentCount())));
        this.setProgressBarLayout(barComplete);
    }

    /**
     * Gets the number of user slides in the linked list
     * @return (int) number of user slides
     */
    private int getNumberSlides(){
        return this.userSlideList.size();
    }

    /**
     * Returns the UserSlideList
     * @return (LinkedList) userSlideList
     */
    public LinkedList<AUserSlide> getUserSlideList() {
        return this.userSlideList;
    }

    /**
     * Set the UserSlideList
     * @param userSlideList the list with the user slides
     */
    public void setUserSlideList(LinkedList<AUserSlide> userSlideList) {
        this.userSlideList = userSlideList;
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
