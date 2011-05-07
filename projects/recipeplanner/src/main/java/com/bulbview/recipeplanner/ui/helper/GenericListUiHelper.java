package com.bulbview.recipeplanner.ui.helper;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

public abstract class GenericListUiHelper extends UiManager {

    protected Panel panel;

    public void setRecipePanel(final Panel recipePanel) {
        this.panel = recipePanel;
        recipePanel.setCaption("Recipes");
        recipePanel.setStyleName("bubble");
    }

    protected void addListItem(final String listItemName) {
        panel.addComponent(new Label(listItemName));
    }

}