package com.bulbview.ui.handlers;

import com.vaadin.event.Action;

public class ActionFactory {

    public Action create(final String actionLabel) {
        return new Action(actionLabel);
    }

}
