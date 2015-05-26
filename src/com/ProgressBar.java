package com;

import java.util.LinkedList;

import com.sun.xml.internal.bind.v2.TODO;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.*;
/**
 * Implements the progress bar for DMPcreator.
 * @author Sven Fillinger
 */
public class ProgressBar {

    //TODO can be removed, only for testing
    private int numberSlides = 7;
    private LinkedList<String> list = new LinkedList<String>();

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
        list.add("keine Ahnung");
        list.add("Ab gehts");
        HorizontalLayout layout = new HorizontalLayout();
        Label tempLabel;
        for(String elem : list) {
            tempLabel = new Label(elem);
            tempLabel.addStyleName("v-align-center");
            layout.addComponent(tempLabel);
            layout.setComponentAlignment(tempLabel, Alignment.TOP_CENTER);
        }

        layout.setSizeFull();
        this.setProgressBarLayout(layout);
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
