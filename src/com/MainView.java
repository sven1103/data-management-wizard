package com;

import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;


@Theme("valo")
@StyleSheet({"http://fonts.googleapis.com/css?family=Roboto:300"})
/**
 * Created by sven on 5/17/15.
 */
public class MainView extends UI {

    Navigator navigator;
    public static String progressStatus;

    @DesignRoot
    public class SlideContainerView extends VerticalLayout implements View {

        VerticalLayout progressBars = new VerticalLayout();
        VerticalLayout slide = new VerticalLayout();
        VerticalLayout nav = new VerticalLayout();

        public SlideContainerView(String _progressStatus) {
            progressStatus = _progressStatus;
            setSizeFull();
            /*Layout progressbar = new ProgressBar(progressStatus).getProgressBarLayout();
            progressBars.addComponent(progressbar);*/
            progressBars.setWidth(100.0f, Unit.PERCENTAGE);
            addComponent(progressBars);
            addComponent(nav);

        }

        class ButtonListener implements Button.ClickListener {
            String nextTopic;

            public ButtonListener(String nextTopic) {
                this.nextTopic = nextTopic;
            }

            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo("/" + nextTopic);
            }
        }

        @DesignRoot
        class NavViewer extends HorizontalLayout {
            Button next = new Button("Allgemeines", new ButtonListener("Allgemeines"));
            private String status;

            public NavViewer() {
                this.status = progressStatus;
                nav.addComponent(next);
            }

        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            if(event.getParameters() == null || event.getParameters().isEmpty()){
                nav.removeAllComponents();
                progressBars.removeAllComponents();
                nav.addComponent(new NavViewer());
                progressBars.addComponent(new ProgressBar(progressStatus).getProgressBarLayout());
            } else{
                progressBars.removeAllComponents();
                nav.removeAllComponents();
                progressBars.addComponent(new ProgressBar(event.getParameters()).getProgressBarLayout());
                nav.addComponent(new NavViewer());
            }

        }
    }

    @Override
    public void init(VaadinRequest request) {
        getPage().setTitle("Data Management Plan Creator");
        // test of the progress bar
        navigator = new Navigator(this, this);

        navigator.addView("", new SlideContainerView("Gehirntod"));
    }
}
