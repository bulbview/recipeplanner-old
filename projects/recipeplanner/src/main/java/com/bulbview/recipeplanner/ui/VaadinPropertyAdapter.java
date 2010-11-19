package com.bulbview.recipeplanner.ui;

import com.vaadin.ui.AbstractSelect;

public class VaadinPropertyAdapter implements ViewField {

    private final AbstractSelect select;

    public VaadinPropertyAdapter(final AbstractSelect categoryComboBox) {
        this.select = categoryComboBox;
    }

    @Override
    public void addItem(final Object value) {
        select.addItem(value);

    }

    @Override
    public void focus() {
        select.focus();

    }

    @Override
    public Object getValue() {
        return select.getValue();

    }

    @Override
    public void setEnabled(final boolean enabled) {
        select.setEnabled(enabled);

    }

    @Override
    public void setValue(final Object value) {
        select.setValue(value);
    }

}
