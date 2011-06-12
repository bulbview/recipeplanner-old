package com.bulbview.recipeplanner.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.MainWindowUiManager;
import com.bulbview.recipeplanner.ui.manager.RecipeEditor;
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList;
import com.bulbview.recipeplanner.ui.presenter.Presenter;

@Component
public class RecipePresenter extends Presenter {

    private MainWindowUiManager mainWindow;
    @Autowired
    private EntityDao<Recipe>   recipeDao;
    private RecipeEditor        recipeEditor;
    private RecipeMasterList    recipeMasterList;

    public void createNewRecipe() {
        mainWindow.showRecipeWindow();
        recipeEditor.set(new Recipe());
    }

    @Override
    public void init() {
        mainWindow.init();
        recipeMasterList.init();
        recipeEditor.init();
    }

    public void save(final Recipe recipe) {
        Recipe savedRecipe;
        try {
            savedRecipe = recipeDao.save(recipe);
            recipeMasterList.addRecipe(savedRecipe);
            mainWindow.closeRecipeEditor();
        } catch (final DaoException e) {
            recipeEditor.showErrorMessage(e.getMessage());
        }
    }

    @Autowired
    public void setMainWindow(final MainWindowUiManager mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void setRecipeEditor(final RecipeEditor recipeEditor) {
        this.recipeEditor = recipeEditor;
        setUiManager(recipeEditor);
    }

    public void setRecipeMasterList(final RecipeMasterList recipeMasterList) {
        this.recipeMasterList = recipeMasterList;
        setUiManager(recipeMasterList);
    }
}
