package com.bulbview.recipeplanner.ui.helper;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;

@Component
public class RecipeMasterListUiHelper extends UiHelper {

    private Panel recipePanel;

    public void clearRecipes() {
        recipePanel.removeAllComponents();
    }

    public void setRecipePanel(final Panel recipePanel) {
        this.recipePanel = recipePanel;
        recipePanel.setCaption("Recipes");
        recipePanel.setStyleName("bubble");
    }

    public void setRecipes(final Collection<Recipe> recipes) {
        for ( final Recipe recipe : recipes ) {// TODO replace with label
                                               // factory bean
            recipePanel.addComponent(new Label(recipe.getName()));
        }
    }
}
