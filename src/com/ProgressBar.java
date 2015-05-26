package com;

import java.util.LinkedList;
import com.vaadin.ui.*;
/**
 * Implements the progress bar for DMPcreator.
 * @author Sven Fillinger
 */
public class ProgressBar {
    /**
     * A linked list with containing all user slides
     */
    private LinkedList<AUserSlide> userSlideList;
    /**
     * The layout for the progress bar
     */
    private Layout progressBarLayout;

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
        VerticalLayout layout = new VerticalLayout();

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
