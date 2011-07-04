package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.RecipeEditor;
import com.bulbview.recipeplanner.ui.manager.MainWindowView;
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList;

@Component
public class RecipePresenter extends Presenter implements SessionPresenter {
    
    private MainWindowView        mainWindow;
    @Autowired
    private EntityDao<Recipe>     recipeDao;
    @Autowired
    private RecipeEditor          recipeEditor;
    private RecipeMasterList      recipeMasterList;
    private Recipe                recipe;
    @Autowired
    private ObjectFactory<Recipe> recipeFactory;
    
    public void createNewRecipe() {
        mainWindow.showRecipeWindow();
        recipe = recipeFactory.getObject();
    }
    
    @Override
    public void init() {
        mainWindow.init();
        recipeMasterList.init();
        recipeEditor.init();
    }
    
    public void save() {
        try {
            recipe.setName((String) recipeEditor.getRecipeNameField().getValue());
            Recipe savedRecipe = recipeDao.save(recipe);
            recipeMasterList.addRecipe(savedRecipe);
            mainWindow.closeRecipeEditor();
        }
        catch (final DaoException e) {
            recipeEditor.showErrorMessage(e.getMessage());
        }
    }
    
    @Autowired
    public void setMainWindow(final MainWindowView mainWindow) {
        this.mainWindow = mainWindow;
    }
    
    public void setRecipeEditor(final RecipeEditor recipeEditor) {
        this.recipeEditor = recipeEditor;
        // setView(recipeEditor);
    }
    
    @Autowired
    public void setRecipeMasterList(final RecipeMasterList recipeMasterList) {
        this.recipeMasterList = recipeMasterList;
        setView(recipeMasterList);
    }
}
