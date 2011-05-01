package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.RecipeJdoDao;
import com.bulbview.recipeplanner.ui.helper.MainWindowUiHelper;
import com.bulbview.recipeplanner.ui.helper.RecipeEditorUiHelper;
import com.bulbview.recipeplanner.ui.helper.RecipeMasterListUiHelper;

@Component
public class RecipePlannerPresenter {

    private final Logger             logger;
    private MainWindowUiHelper       mainWindowUiHelper;
    @Autowired
    private RecipeJdoDao             recipeDao;
    private RecipeEditorUiHelper     recipeFormUiHelper;
    private RecipeMasterListUiHelper recipeMasterListUiHelper;

    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void createNewRecipe() {
        mainWindowUiHelper.showRecipeWindow();
        recipeFormUiHelper.set(new Recipe());
    }

    public void save(final Recipe recipe) {
        recipeDao.saveRecipe(recipe);
        refreshRecipeMasterList();
        logger.debug("saved recipe: {}", recipe);
        mainWindowUiHelper.closeRecipeEditor();
    }

    public void setMainWindow(final MainWindowUiHelper mainWindowUiHelper) {
        this.mainWindowUiHelper = mainWindowUiHelper;
        mainWindowUiHelper.setPresenter(this);
    }

    public void setRecipeEditor(final RecipeEditorUiHelper recipeFormUiHelper) {
        this.recipeFormUiHelper = recipeFormUiHelper;
        recipeFormUiHelper.setPresenter(this);
    }

    public void setRecipeMasterList(final RecipeMasterListUiHelper recipeMasterListUiHelper) {
        this.recipeMasterListUiHelper = recipeMasterListUiHelper;
    }

    private void refreshRecipeMasterList() {
        recipeMasterListUiHelper.clearRecipes();
        recipeMasterListUiHelper.setRecipes(recipeDao.getAll());
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
