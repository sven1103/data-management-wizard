package com;

import java.util.LinkedList;
import com.vaadin.ui.*;
/**
 * Created by fillinger on 5/26/15.
 */
public class ProgressBar {
    private LinkedList<AUserSlide> userSlideList;
    private Layout progressBarLayout;

    /**
     * Constructor for a progress bar element.
     * @param _userSlideList: expects an LinkedList with user slides
     */
    public ProgressBar(LinkedList<AUserSlide> _userSlideList){
        this.userSlideList = _userSlideList;
        this.progressBarLayout = init();
    }

    /**
     * Builds the progress bar from the user slide elements in the
     * LinkedList and returns it as a finished layout that can then be
     * implemented in the parent layout container.
     * @return: (Layout) finished progress bar layout
     */
    public Layout init(){
         return progressBarLayout;
    }

    /**
     * Gets the number of user slides in the linked list
     * @return: (int) number of user slides
     */
    private int getNumberSlides(){
        return this.userSlideList.size();
    }

    public LinkedList<AUserSlide> getUserSlideList() {
        return userSlideList;
    }

    public void setUserSlideList(LinkedList<AUserSlide> userSlideList) {
        this.userSlideList = userSlideList;
    }

    public Layout getProgressBarLayout() {
        return progressBarLayout;
    }

    public void setProgressBarLayout(Layout progressBarLayout) {
        this.progressBarLayout = progressBarLayout;
    }
}
