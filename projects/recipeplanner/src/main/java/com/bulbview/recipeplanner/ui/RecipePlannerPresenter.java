package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.vaadin.mvp.presenter.BasePresenter;

import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.ui.eventbus.RecipePlannerEventBus;

@Component
public class RecipePlannerPresenter extends BasePresenter<WindowView, RecipePlannerEventBus> {

    private final Logger         logger;
    private MainWindowUiHelper   mainWindowUiHelper;
    private RecipeEditorUiHelper recipeFormUiHelper;

    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void createNewRecipe() {
        mainWindowUiHelper.showRecipeWindow();
        recipeFormUiHelper.set(new Recipe());
    }

    public void save() {
        mainWindowUiHelper.closeRecipeEditor();
    }

    public void setMainWindow(final MainWindowUiHelper mainWindowUiHelper) {
        this.mainWindowUiHelper = mainWindowUiHelper;
        mainWindowUiHelper.setPresenter(this);
    }

    public void setRecipeEditorUiHelper(final RecipeEditorUiHelper recipeFormUiHelper) {
        this.recipeFormUiHelper = recipeFormUiHelper;
        recipeFormUiHelper.setPresenter(this);
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
