package com.userSlides;

import IO.Communicator;
import com.TsvUpload;
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
    protected String header;

    /**
     * The progressBars of the user slide.
     */
    protected Layout content;

    /**
     * The uploaded content
     */
    protected Communicator tsvUpload;

    /**
     * Build an abstract user slide object.
     * @param header Unique name or header of the user slide.
     */
    public AUserSlide(String header) {
        this.header = header;
        this.content = buildContent();
        this.tsvUpload = null;
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
     * @return Layout. This method returns the built components as a Layout object.
     */
    private Layout buildContent() {

        // configure Components
        configureComponents();

        // buildLayout
        Layout mainLayout = buildLayout();

        return mainLayout;
    }


    /**
     * Abstract method. This method initializes and configures
     * the components of the user slide.
     */
    protected abstract void configureComponents();

    /**
     * Abstract method. This method arranges the components in one layout
     * that is returned.
     * @return Layout.
     */
    protected abstract Layout buildLayout();

    public abstract String getTsvUpload();

    /**
     * refreshes the layout, shall be called when the TSV is loaded
     */
    public abstract void refreshComponents();

}
