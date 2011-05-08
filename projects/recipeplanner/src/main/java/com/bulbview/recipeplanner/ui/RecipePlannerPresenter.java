package com.bulbview.recipeplanner.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.ItemCategory;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.JdoDao;
import com.bulbview.recipeplanner.ui.helper.CategoryEditor;
import com.bulbview.recipeplanner.ui.helper.CategoryTabs;
import com.bulbview.recipeplanner.ui.helper.MainWindowUiManager;
import com.bulbview.recipeplanner.ui.helper.RecipeEditorUiHelper;
import com.bulbview.recipeplanner.ui.helper.RecipeMasterList;
import com.bulbview.recipeplanner.ui.helper.UiManager;

@Component
public class RecipePlannerPresenter {

    @Autowired
    private JdoDao<ItemCategory> categoryDao;
    private CategoryEditor       categoryEditor;
    private CategoryTabs         categoryTabs;
    private final Logger         logger;
    private MainWindowUiManager  mainWindowUiHelper;
    @Autowired
    private JdoDao<Recipe>       recipeDao;
    private RecipeEditorUiHelper recipeFormUiHelper;
    private RecipeMasterList     recipeMasterListUiHelper;

    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addCategoryMenuSelected() {
        categoryEditor.setItemCategory(createItemCategory());
        mainWindowUiHelper.showCategoryWindow();
    }

    public void createNewRecipe() {
        mainWindowUiHelper.showRecipeWindow();
        recipeFormUiHelper.set(new Recipe());
    }

    public void save(final Recipe recipe) {
        recipeDao.save(recipe);
        refreshRecipeMasterList();
        logger.debug("saved recipe: {}", recipe);
        mainWindowUiHelper.closeRecipeEditor();
    }

    public void saveCategory(final ItemCategory itemCategory) {
        logger.debug("saving category: {}...", itemCategory);
        categoryDao.save(itemCategory);
        categoryTabs.setCategories(categoryDao.getAll());
        mainWindowUiHelper.closeCategoryWindow();
    }

    public void setCategoryDao(final JdoDao<ItemCategory> categoryDao) {
        this.categoryDao = categoryDao;
    }

    public void setCategoryEditorWindow(final CategoryEditor categoryUiManager) {
        this.categoryEditor = categoryUiManager;
        initialiseUiManager(categoryUiManager);
    }

    public void setCategoryTabs(final CategoryTabs categoryTabs) {
        this.categoryTabs = categoryTabs;
        initialiseUiManager(categoryTabs);
        this.categoryTabs.setCategories(categoryDao.getAll());
    }

    public void setMainWindow(final MainWindowUiManager mainWindowUiHelper) {
        this.mainWindowUiHelper = mainWindowUiHelper;
        initialiseUiManager(mainWindowUiHelper);
    }

    public void setRecipeEditor(final RecipeEditorUiHelper recipeEditor) {
        this.recipeFormUiHelper = recipeEditor;
        initialiseUiManager(recipeEditor);
    }

    public void setRecipeMasterList(final RecipeMasterList recipeMasterListUiHelper) {
        this.recipeMasterListUiHelper = recipeMasterListUiHelper;
        initialiseUiManager(recipeMasterListUiHelper);
    }

    private ItemCategory createItemCategory() {
        return new ItemCategory();
    }

    private void initialiseUiManager(final UiManager mainWindowUiHelper) {
        mainWindowUiHelper.setPresenter(this);
        mainWindowUiHelper.init();
    }

    private void refreshRecipeMasterList() {
        recipeMasterListUiHelper.clearPanel();
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
