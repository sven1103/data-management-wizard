package com;

import com.vaadin.ui.*;

/**
 * <h1>Abstract User Slide</h1>
 * This class works as an abstract interface for
 * every user slide object.
 * Created by heumos on 5/26/15.
 */
public abstract class AUserSlide {

    /**
     * The header or name of the user slide. Must be unique!
     * Every user slide must have a unique header or name.
     */
    private String header;

    /**
     * The progressBars of the user slide.
     */
    private Layout content;

    /**
     * Build an abstract user slide object.
     * @param header Unique name or header of the user slide.
     */
    public AUserSlide(String header) {
        this.header = header;
        this.content = buildContent();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public Layout getContent() {
        return content;
    }

    public void setContent(Layout content) {
        this.content = content;
    }

    /**
     * Abstract method. Must be implemented in the class extending this one.
     * @return Layout This method returns the built progressBars as a Layout object.
     */
    protected abstract Layout buildContent();

}
