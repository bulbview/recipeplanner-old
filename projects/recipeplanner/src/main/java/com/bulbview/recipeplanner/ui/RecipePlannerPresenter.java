package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.ObjectifyDao;
import com.bulbview.recipeplanner.ui.manager.MainWindowUiManager;
import com.bulbview.recipeplanner.ui.manager.RecipeEditor;
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList;
import com.bulbview.recipeplanner.ui.presenter.Presenter;

@Component
public class RecipePlannerPresenter extends Presenter {

    private final Logger        logger;
    private MainWindowUiManager mainWindow;
    @Autowired
    private ObjectifyDao<Recipe>      recipeDao;
    private RecipeEditor        recipeEditor;
    private RecipeMasterList    recipeMasterList;

    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void createNewRecipe() {
        mainWindow.showRecipeWindow();
        recipeEditor.set(new Recipe());
    }

    public void init() {
        mainWindow.init();
        recipeMasterList.init();
        recipeEditor.init();
    }

    public void save(final Recipe recipe) {
        final Recipe savedRecipe = recipeDao.save(recipe);
        recipeMasterList.addRecipe(savedRecipe);
        mainWindow.closeRecipeEditor();
    }

    public void setMainWindow(final MainWindowUiManager mainWindow) {
        this.mainWindow = mainWindow;
        setUiManager(mainWindow);
    }

    public void setRecipeEditor(final RecipeEditor recipeEditor) {
        this.recipeEditor = recipeEditor;
        setUiManager(recipeEditor);
    }

    public void setRecipeMasterList(final RecipeMasterList recipeMasterList) {
        this.recipeMasterList = recipeMasterList;
        setUiManager(recipeMasterList);
    }

    // private void setMasterRecipeListAsDropSource(final MasterRecipeListView
    // masterRecipeListView,
    // final DailyRecipeListsContainerView dailyRecipeListView) {
    // final Collection<DailyRecipeList> dailyLists =
    // dailyRecipeListView.getDailyLists();
    // for ( final DailyRecipeList dailyRecipeList : dailyLists ) {
    // dailyRecipeList.setDropSource(masterRecipeListView);
    // }
    // }
}
