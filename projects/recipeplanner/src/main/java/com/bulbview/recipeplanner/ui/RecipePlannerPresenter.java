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
import com.bulbview.recipeplanner.ui.helper.RecipeEditor;
import com.bulbview.recipeplanner.ui.helper.RecipeMasterList;
import com.bulbview.recipeplanner.ui.helper.UiManager;

@Component
public class RecipePlannerPresenter {

    @Autowired
    private JdoDao<ItemCategory> categoryDao;
    private CategoryEditor       categoryEditor;
    private CategoryTabs         categoryTabs;
    private final Logger         logger;
    private MainWindowUiManager  mainWindow;
    @Autowired
    private JdoDao<Recipe>       recipeDao;
    private RecipeEditor recipeFormUiHelper;
    private RecipeMasterList     recipeMasterList;

    public RecipePlannerPresenter() {
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public void addCategoryMenuSelected() {
        categoryEditor.setItemCategory(createItemCategory());
        mainWindow.showCategoryWindow();
    }

    public void createNewRecipe() {
        mainWindow.showRecipeWindow();
        recipeFormUiHelper.set(new Recipe());
    }

    public void save(final Recipe recipe) {
        final Recipe savedRecipe = recipeDao.save(recipe);
        recipeMasterList.addRecipe(savedRecipe);
        mainWindow.closeRecipeEditor();
    }

    public void saveCategory(final ItemCategory itemCategory) {
        logger.debug("saving category: {}...", itemCategory);
        categoryTabs.addCategory(categoryDao.save(itemCategory));
        mainWindow.closeCategoryWindow();
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
        this.mainWindow = mainWindowUiHelper;
        initialiseUiManager(mainWindowUiHelper);
    }

    public void setRecipeEditor(final RecipeEditor recipeEditor) {
        this.recipeFormUiHelper = recipeEditor;
        initialiseUiManager(recipeEditor);
    }

    public void setRecipeMasterList(final RecipeMasterList recipeMasterList) {
        this.recipeMasterList = recipeMasterList;
        recipeMasterList.setRecipePanel(mainWindow.getRecipePanel());
        initialiseUiManager(recipeMasterList);
    }

    private ItemCategory createItemCategory() {
        return new ItemCategory();
    }

    private void initialiseUiManager(final UiManager mainWindowUiHelper) {
        mainWindowUiHelper.setPresenter(this);
        mainWindowUiHelper.init();
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
