package com.bulbview.recipeplanner.ui.manager;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.MainWindow;
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter;
import com.vaadin.ui.Table;

@Component
public class RecipeMasterList extends GenericListView<Recipe, RecipePresenter> {
    
    public RecipeMasterList() {
        super(Recipe.class);
    }
    
    public void addRecipe(final Recipe recipe) {
        addListItem(recipe);
    }
    
    @Override
    public void init() {
        super.init();
        topLevelPanel.setCaption("Recipes");
    }
    
    @Autowired
    public void initialise(final MainWindow generatedComponent) {
        setTopLevelPanel(generatedComponent.getRecipePanel());
    }
    
    @Override
    @Autowired
    public void setGenericListTable(final Table genericListTable) {
        super.setGenericListTable(genericListTable);
    }
    
    public void setRecipes(final Collection<Recipe> recipes) {
        for (final Recipe recipe : recipes) {
            addRecipe(recipe);
        }
    }
    
}
