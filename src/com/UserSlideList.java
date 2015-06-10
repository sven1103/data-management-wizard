package com;

import java.util.LinkedList;

import IO.Communicator;
import com.userSlides.*;

/**
 * <h1>User Slides</h1>
 * Created by heumos on 5/26/15.
 */
public class UserSlideList {

    /**
     * The actual user slide list.
     */
    public static LinkedList<String> userSlides = new LinkedList<String>();

    /**
     *  The slide container with slide objects.
     *
     */
    public static LinkedList<AUserSlide> slideContainer = new LinkedList<AUserSlide>();

    /**
     * This method initiates all relevant user slides adding them
     * to a linked list. The field userSlides is then set as the linked
     * list.
     */
    public static void init(Communicator parsedTSV) {
        /*
        We have to check, if the user pressed reload. If yes, the list was already created
        and does not have to be filled again. Otherwise we would have duplicates.
         */
        if(userSlides.isEmpty()){
            userSlides.add("General");
            userSlides.add("Roles & Responsibilities");
            userSlides.add("Content Management");
            userSlides.add("Storage/Backup");
            userSlides.add("Dissemination");
        }
        if(slideContainer.isEmpty()){
            slideContainer.add(new FirstStepsSlide("General Information"));
            slideContainer.add(new RolesResponsibilitiesSlide("Roles & Responsibilities"));
            slideContainer.add(new DocContManagementSlide("Content Management"));
            slideContainer.add(new DocContManagementSlide("Storage and Backup"));
            slideContainer.add(new DisseminationMethods("Dissemination Methods"));
        }
    }

    public static LinkedList<String> getUserSlides() {
        return userSlides;
    }

    public static String getPrevElement(String element){
        int currElementIndex = userSlides.indexOf(element);
        return currElementIndex > 0 ? userSlides.get(currElementIndex-1): userSlides.get(currElementIndex);
    }

    public static String getNextElement(String element){
        int currElementIndex = userSlides.indexOf(element);
        return currElementIndex < userSlides.size()-1 ? userSlides.get(currElementIndex+1): userSlides.get(currElementIndex);
    }


}
