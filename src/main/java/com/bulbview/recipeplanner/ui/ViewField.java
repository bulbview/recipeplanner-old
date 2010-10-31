package com.bulbview.recipeplanner.ui;


public interface ViewField {

    public void addItem(Object ingredient);

    public Object getValue();

    public void setEnabled(boolean b);

    public void setValue(Object value);

}
