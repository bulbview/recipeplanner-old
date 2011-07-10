package com.bulbview.recipeplanner.ui.presenter;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bulbview.recipeplanner.datamodel.Ingredient;
import com.bulbview.recipeplanner.datamodel.Item;
import com.bulbview.recipeplanner.datamodel.Recipe;
import com.bulbview.recipeplanner.persistence.DaoException;
import com.bulbview.recipeplanner.persistence.EntityDao;
import com.bulbview.recipeplanner.ui.RecipeEditor;
import com.bulbview.recipeplanner.ui.manager.MainWindowView;
import com.bulbview.recipeplanner.ui.manager.RecipeEditorIngredientList;
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

@Component
public class RecipePresenter extends Presenter implements SessionPresenter {
    
    @Autowired
    private ObjectFactory<Ingredient>  ingredientFactory;
    private MainWindowView             mainWindow;
    private Recipe                     recipe;
    @Autowired
    private EntityDao<Recipe>          recipeDao;
    @Autowired
    private RecipeEditor               recipeEditor;
    @Autowired
    private RecipeEditorIngredientList recipeEditorIngredientList;
    @Autowired
    private ObjectFactory<Recipe>      recipeFactory;
    private RecipeMasterList           recipeMasterList;
    
    public void createNewRecipe() {
        mainWindow.showRecipeWindow();
        recipe = recipeFactory.getObject();
    }
    
    public void dragAndDrop(final Item item) {
        final Ingredient ingredient = ingredientFactory.getObject();
        ingredient.setItem(item);
        recipeEditorIngredientList.addIngredient(ingredient);
        
    }
    
    @SuppressWarnings("serial")
    @Override
    public void init() {
        mainWindow.init();
        recipeMasterList.init();
        recipeEditor.init();
        recipeEditorIngredientList.init();
        recipeEditorIngredientList.setPresenter(this);
        recipeEditor.getSaveButton().addListener(new ClickListener() {
            
            @Override
            public void buttonClick(final ClickEvent event) {
                save();
            }
        });
    }
    
    public void save() {
        try {
            recipe.setName((String) recipeEditor.getRecipeNameField().getValue());
            final Recipe savedRecipe = recipeDao.save(recipe);
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
    }
    
    @Autowired
    public void setRecipeEditorIngredientList(final RecipeEditorIngredientList recipeEditorIngredientList) {
        this.recipeEditorIngredientList = recipeEditorIngredientList;
    }
    
    @Autowired
    public void setRecipeMasterList(final RecipeMasterList recipeMasterList) {
        this.recipeMasterList = recipeMasterList;
        setView(recipeMasterList);
    }
}
