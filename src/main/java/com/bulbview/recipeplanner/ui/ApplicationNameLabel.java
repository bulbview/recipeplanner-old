package com.bulbview.recipeplanner.ui;

import com.google.inject.Inject;
import com.vaadin.ui.Label;

public class ApplicationNameLabel extends Label {

    @Inject public ApplicationNameLabel(final String applicationName) {
        setValue(String.format("<h1>%s</h1>", applicationName));
        setContentMode(Label.CONTENT_XHTML);
    }

}
