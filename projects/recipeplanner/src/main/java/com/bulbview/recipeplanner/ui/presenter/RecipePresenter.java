package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.manager.MainWindowView;
import com.bulbview.recipeplanner.ui.manager.RecipeEditorOld;
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList;

@Component
public class RecipePresenter extends Presenter implements SessionPresenter {

    private MainWindowView mainWindow;
    @Autowired
    private EntityDao<Recipe>   recipeDao;
    private RecipeEditorOld        recipeEditor;
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
    public void setMainWindow(final MainWindowView mainWindow) {
        this.mainWindow = mainWindow;
    }

    @Autowired
    public void setRecipeEditor(final RecipeEditorOld recipeEditor) {
        this.recipeEditor = recipeEditor;
        setView(recipeEditor);
    }

    @Autowired
    public void setRecipeMasterList(final RecipeMasterList recipeMasterList) {
        this.recipeMasterList = recipeMasterList;
        setView(recipeMasterList);
    }
}
