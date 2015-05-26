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
public class DataManagementWizard extends UI implements Button.ClickListener {

    private boolean flag = true;

    protected Button button;
    protected Label header2;

    @Override
    public void init(VaadinRequest request) {

        VerticalLayout content = new VerticalLayout();
        setContent(content);
        //content.setSizeFull();

        Label header = new Label("Data Management Tool");
        header.addStyleName("h1");
        content.addComponent(header);

        header2 = new Label("Click the button");
        header2.addStyleName("h2");
        content.addComponent(header2);

        HorizontalLayout menuview = new HorizontalLayout();
        menuview.addStyleName("wrapping");
        //menuview.setSizeFull();

        CssLayout group = new CssLayout();
        group.addStyleName("v-component-group");
        menuview.addComponent(group);

        TextField textfield = new TextField();
        textfield.setValue("Write something nice in here");
        textfield.setWidth(100.0f, Unit.PERCENTAGE);
        group.addComponent(textfield);
        button = new Button("Go");
        button.addClickListener(this);

        group.addComponent(button);
        menuview.setSpacing(false);
        content.addComponent(menuview);
        content.setExpandRatio(menuview, 1);

    }
    public void buttonClick(Button.ClickEvent event){
        if(flag){
            header2.setValue("Hey, u just did it!");
            button.setCaption("Click me harder!");
            flag = false;
        } else{
            flag = true;
            header2.setValue("Click the button!");
        }
    }

}
