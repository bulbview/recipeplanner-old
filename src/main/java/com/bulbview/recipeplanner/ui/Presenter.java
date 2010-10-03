package com.bulbview.recipeplanner.ui;

import java.util.Date;

import com.bulbview.recipeplanner.datamodel.Recipe;

public interface Presenter {

    public void closeRecipeEditor();

    public void createNewRecipe();

    public void initialise();

    public void saveRecipe(Recipe recipe);

    public void setStartDate(final Date date);

}
