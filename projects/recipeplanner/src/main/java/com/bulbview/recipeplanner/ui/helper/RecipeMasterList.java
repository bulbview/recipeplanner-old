package com.bulbview.recipeplanner.ui.helper;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.vaadin.ui.Panel;

@Component
public class RecipeMasterList extends GenericListUiHelper {

    private Panel panel;

    public void clearPanel() {
        panel.removeAllComponents();
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    public void setRecipes(final Collection<Recipe> recipes) {
        for ( final Recipe recipe : recipes ) {
            addListItem(recipe.getName());
        }
    }

}
