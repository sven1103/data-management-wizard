package com.userSlides;

import com.vaadin.ui.*;

/**
 * Created by heumos on 6/10/15.
 */
// Define a sub-window by inheritance
class DisseminationMethodsSub extends Window {
    private ComboBox comboBox;
    public DisseminationMethodsSub(final ComboBox comboBox) {
        super("Add unfamiliar dissemination method."); // Set window caption
        center();

        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        final TextField roleText = new TextField("Enter dissemination method below.");
        content.addComponent(roleText);
        content.setMargin(true);
        setContent(content);

        // Trivial logic for closing the sub-window
        Button ok = new Button("Add dissemination method.");
        ok.addClickListener(new Button.ClickListener() {
            public void buttonClick(Button.ClickEvent event) {
                String val = roleText.getValue().trim();
                if (!val.equals("")) {
                    comboBox.addItem(roleText.getValue());
                }
                close(); // Close the sub-window
            }
        });
        content.addComponent(ok);
    }
}
