package com.bulbview.recipeplanner.ui;

import com.vaadin.ui.AbstractSelect;

public class VaadinPropertyAdapter implements ViewField {

    private final AbstractSelect comboBox;

    public VaadinPropertyAdapter(final AbstractSelect categoryComboBox) {
        this.comboBox = categoryComboBox;
    }

    @Override
    public void setValue(final Object value) {
        comboBox.setValue(value);
    }

}
