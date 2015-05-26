package com;

import java.util.LinkedList;
import java.util.List;

/**
 * <h1>User Slides</h1>
 * Created by heumos on 5/26/15.
 */
public class UserSlideList {

    /**
     * The actual user slide list.
     */
    private LinkedList<String> userSlides;

    /**
     *
     */
    public UserSlideList() {
        init();
    }

    /**
     * This method initiates all relevant user slides adding them
     * to a linked list. The field userSlides is then set as the linked
     * list.
     */
    public void init() {
        LinkedList<String> userSlides = new LinkedList<String>();
        // TODO implement this!
        this.userSlides = userSlides;
    }

    public LinkedList<String> getUserSlides() {
        return userSlides;
    }

    public void setUserSlides(LinkedList<String> userSlides) {
        this.userSlides = userSlides;
    }
}
