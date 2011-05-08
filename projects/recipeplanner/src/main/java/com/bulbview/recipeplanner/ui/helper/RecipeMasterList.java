package com.bulbview.recipeplanner.ui.helper;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.vaadin.ui.Table;

@Component
public class RecipeMasterList extends GenericListUiManager<Recipe> {

    public RecipeMasterList() {
        super(Recipe.class);
    }

    public void addRecipe(final Recipe recipe) {
        addListItem(recipe);
    }

    public void setRecipes(final Collection<Recipe> recipes) {
        for ( final Recipe recipe : recipes ) {
            addRecipe(recipe);
        }
    }

    @Override
    @Autowired
    public void setTable(final Table recipeTable) {
        super.setTable(recipeTable);
    }

}
