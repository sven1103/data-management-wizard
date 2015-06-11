package com;

import IO.Communicator;
import com.userSlides.AUserSlide;
import com.userSlides.FirstStepsSlide;
import com.userSlides.StorageBackupSlide;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

import java.io.IOException;


@Theme("valo")
@StyleSheet({"http://fonts.googleapis.com/css?family=Roboto:300"})
/**
 * Created by sven on 5/17/15.
 */
public class MainView extends UI {

    Navigator navigator;



    private static String progressStatus;
    private static Communicator parsedTSV = null;
    private AUserSlide currentSlide = new FirstStepsSlide("gogog");

    @DesignRoot
    public class SlideContainerView extends VerticalLayout implements View {

        VerticalLayout progressBars = new VerticalLayout();
        Panel slide = new Panel();
        HorizontalLayout nav = new HorizontalLayout();


        /**
         * The constructor for whole slide view with progress bar, slide and navigation.
         * @param _progressStatus the current progress element (slide)
         */
        public SlideContainerView(String _progressStatus) {
            progressStatus = _progressStatus;
            configureLayout();

            addComponent(progressBars);
            addComponent(slide);
            addComponent(nav);
            setComponentAlignment(nav, Alignment.MIDDLE_RIGHT);

        }

        private void configureLayout(){
            setWidth(100.0f, Unit.PERCENTAGE);
            progressBars.setHeightUndefined();
            progressBars.setWidth(100.0f, Unit.PERCENTAGE);
            slide.setSizeFull();
            slide.setCaption("Settings for Data Management Plan");
        }

        /**
         * A button listener, that will set the current slide element
         * the user wants to go to.
         */
        class ButtonListener implements Button.ClickListener {
            String progressElement;
            public ButtonListener(String Elem) {
                this.progressElement = Elem;
            }

            /**
             * When one of the navigation button is clicked,
             * move the view to the respective element. Can be forward or reverse.
             * @param event the button click
             */
            @Override
            public void buttonClick(Button.ClickEvent event) {
                navigator.navigateTo("/" + this.progressElement);
                // update the progressStatus to the new one
                progressStatus = this.progressElement;
            }
        }

        /**
         * Class for displaying the user slides
         */
        class SlideViewer extends HorizontalLayout {
            /**
             * The constructor for a SlideViewer object. Checks the slide it shall present
             * and then adds it to the slide component.
             * @param slideType
             */
            public SlideViewer(int slideType) {

                currentSlide = UserSlideList.slideContainer.get(slideType);
                slide.setContent(currentSlide.getContent());
                /*
                switch(slideType){

                    case 0:
                        currentSlide = new FirstStepsSlide("test");
                        slide.setContent(currentSlide.getContent());
                        break;
                    case 1:
                        currentSlide = new SecondStepsSlide("hiii", parsedTSV);
                        slide.setContent(currentSlide.getContent());
                        break;
                }*/
            }
        }

        /**
         * Definition of the navigation bar
         */
        @DesignRoot
        class NavViewer extends HorizontalLayout {
            /**
             * Constructor for the navigation view. Builds to navigation buttons, with which you can
             * go through the slides.
             */
            public NavViewer() {
                String nextElem = UserSlideList.getNextElement(progressStatus);
                String prevElem = UserSlideList.getPrevElement(progressStatus);
                Button prev = new Button(prevElem, new ButtonListener(prevElem));
                Button next = new Button(nextElem, new ButtonListener(nextElem));
                prev.setWidth(200.0f, Unit.PIXELS);
                next.setWidth(200.0f, Unit.PIXELS);
                // check, if we are on the first or last slide and deactivate the respective button
                if(prevElem.equals(progressStatus)){
                    prev.setEnabled(false);
                } else if(nextElem.equals(progressStatus)){
                    next.setEnabled(false);
                }
                nav.addComponent(prev);
                nav.addComponent(next);
                nav.setSpacing(true);
            }

        }

        /**
         * The view change listener registers, when the navigateTo() function is called.
         * We recognize a change in the progress bar (user continues in slides) and have to
         * update the buttons and the progress bar.
         * We also have to handle the case, when the view is called the first time.
         * @param event navigator view change event
         */
        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {
            /*
            When the MainView is first generated, no event will be triggered
             */
            if(event.getParameters() == null || event.getParameters().isEmpty()){
                // Catch reload case, so no old nav and progressbar elements will be there
                nav.removeAllComponents();
                progressBars.removeAllComponents();
                //slide.removeAllComponents();
                // build components
                new SlideViewer(UserSlideList.userSlides.indexOf(progressStatus));
                new NavViewer();
                progressBars.addComponent(new ProgressBarSven(UserSlideList.userSlides.getFirst()).getProgressBarLayout());
            } else{
                /*
                Handle the navigation event, when moving the location with navigateTo()
                 */
                // remove progress bar and the nav components
                if(parsedTSV == null && currentSlide.getTsvUpload() != ""){
                    try {
                        parsedTSV = new Communicator(currentSlide.getTsvUpload());
                        // refresh slides
                        for(AUserSlide slide : UserSlideList.slideContainer){
                            slide.refreshComponents();
                        }
                        System.out.println("Also gloaden hoats");
                        System.out.println("Number I:" + parsedTSV.getNumberOfIndividuals());
                        System.out.println(Integer.toString(MainView.parsedTSV.getNcbiOrganismID()));
                        // function that adds necessery information for storage & backup slide
                        updateStorageBackupSlide(parsedTSV);
                    } catch (IOException ex) {
                        System.out.println("Hmmmm, error");
                        parsedTSV = null;
                    }
                } else{
                    System.out.println("what what in the butt?");
                }
                progressBars.removeAllComponents();
                nav.removeAllComponents();
                //slide.removeAllComponents();
                // add new ones with changed values
                progressBars.addComponent(new ProgressBarSven(event.getParameters()).getProgressBarLayout());
                progressStatus = event.getParameters();
                new SlideViewer(UserSlideList.userSlides.indexOf(progressStatus));
                new NavViewer();
            }

        }
    }

    /**
     * Initiate the view and the user slide list.
     * @param request standard vaadin request
     */
    @Override
    public void init(VaadinRequest request) {
        getPage().setTitle("Data Management Plan Creator");

        // Generate the UserSlideList
        UserSlideList.init(parsedTSV);
        // Create new Navigator object
        navigator = new Navigator(this, this);
        // add SlideContainerView, start with initial slide
        navigator.addView("", new SlideContainerView(UserSlideList.userSlides.getFirst()));
    }

    private void updateStorageBackupSlide(Communicator parsedTSV) {
        // TODO implement this out
        StorageBackupSlide storageBackupSlide = (StorageBackupSlide) UserSlideList.getUserSlide("Storage and Backup");
        storageBackupSlide.setStartValues(parsedTSV.getQsampleType());
    }

    public static Communicator getParsedTSV() {
        return parsedTSV;
    }



}
