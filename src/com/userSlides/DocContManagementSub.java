package com.userSlides;

import com.vaadin.ui.*;

/**
 * Created by heumos on 6/10/15.
 */
// Define a sub-window by inheritance
class DocContManagementSub extends Window {
    private ComboBox comboBox;
    public DocContManagementSub(final ComboBox comboBox) {
        super("Add unfamiliar data type."); // Set window caption
        center();

        // Some basic content for the window
        VerticalLayout content = new VerticalLayout();
        final TextField roleText = new TextField("Enter data type below.");
        content.addComponent(roleText);
        content.setMargin(true);
        setContent(content);

        // Trivial logic for closing the sub-window
        Button ok = new Button("Add data type.");
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
