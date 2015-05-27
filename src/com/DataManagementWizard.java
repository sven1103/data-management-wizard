package com;

import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


@Theme("valo")
@StyleSheet({"http://fonts.googleapis.com/css?family=Roboto:300"})
/**
 * Created by sven on 5/17/15.
 */
public class DataManagementWizard extends UI {

    private boolean flag = true;

    protected Button button;
    protected Label header2;
    protected Layout progressBar;

    @Override
    public void init(VaadinRequest request) {

        // test of the progress bar
        ProgressBar progressBar = new ProgressBar("Allgemeines");
        HorizontalLayout content = new HorizontalLayout();
        setContent(content);
        // add sample progress bar
        content.addComponent(progressBar.getProgressBarLayout());

        content.setSizeFull();
    }


}
