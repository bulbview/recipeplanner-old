package test.com.bulbview.recipeplanner.presenter

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture
import test.com.bulbview.recipeplanner.dao.TestUtilities

import com.bulbview.recipeplanner.datamodel.Ingredient
import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao
import com.bulbview.recipeplanner.ui.RecipeEditor
import com.bulbview.recipeplanner.ui.manager.MainWindowView
import com.bulbview.recipeplanner.ui.manager.RecipeEditorIngredientList
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.vaadin.ui.Button
import com.vaadin.ui.TextField

@DirtiesContext
class RecipePreseterFixture extends SpringContextTestFixture {
    @Autowired
    def RecipePresenter presenter
    def MainWindowView mockMainWindowUiHelper
    def RecipeMasterList mockRecipeMasterList
    def TextField mockTextField
    def RecipeEditor mockRecipeEditor
    def RecipeEditorIngredientList mockRecipeEditorIngredientList
    
    def TestUtilities recipeUtils
    def TestUtilities itemUtils
    
    @Autowired
    def EntityDao<Recipe> recipeDao
    @Autowired
    def ItemObjectifyDao itemDao
    
    
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper
    
    def setup() {
        localServiceTestHelper.setUp();
        recipeUtils = TestUtilities.create(recipeDao, Recipe)
        itemUtils = TestUtilities.create(itemDao, Item)
        createMocks()
        initialisePresenter()
        mockRecipeEditor.getRecipeNameField() >> mockTextField
    }
    
    private initialisePresenter() {
        presenter.setMainWindow(mockMainWindowUiHelper)
        presenter.setRecipeMasterList(mockRecipeMasterList)
        presenter.setRecipeEditor(mockRecipeEditor)
        presenter.setRecipeEditorIngredientList(mockRecipeEditorIngredientList)
    }
    
    private createMocks() {
        mockMainWindowUiHelper = Mock()
        mockRecipeMasterList = Mock()
        mockRecipeEditor = Mock()
        mockRecipeEditor.getSaveButton() >> Mock(Button)
        mockTextField = Mock()
        mockRecipeEditorIngredientList =  Mock()
    }
}

class RecipePresenterTest extends RecipePreseterFixture {
    
    def recipeWithName() {
        def Recipe recipe = new Recipe()
        recipe.setName("testRecipe1")
        return recipe
    }
    
    def "should close recipe editor on save" (){
        given:
        presenter.createNewRecipe()
        
        when: "the presenter saves a recipe"
        presenter.save()
        
        then:"the recipe editor is closed"
        1 * mockMainWindowUiHelper.closeRecipeEditor()
    }
    
    
    def "should have no recipes initially" () {
        when:"all saved recipes are retrieved"
        
        def savedRecipes = retrieveAllRecipes()
        
        then:"no recipes are found"
        savedRecipes != null
        savedRecipes.size() == 0
    }
    
    def "should persist a new recipe on save" () {
        given:"a recipe with a name"
        presenter.createNewRecipe()
        
        and: "there are no existing recipes"
        recipeDao.getAll().size() == 0
        
        when: "the recipe is saved"
        presenter.save()
        def savedRecipes = recipeDao.getAll()
        
        then:"the recipe should be persisted"
        savedRecipes != null
        savedRecipes.size() == 1
    }
    
    def "should persist a recipe's ingredients on save" () {
        given:"a recipe with a name"
        def pork = createIngredient("Pork")
        def kale =createIngredient("Kale")
        presenter.createNewRecipe()
        
        and: "there are no existing recipes"
        recipeDao.getAll().size() == 0
        
        when: "the recipe is saved"
        presenter.save()
        def savedRecipes = recipeDao.getAll()
        
        then:"the recipe should be persisted"
        1 * mockRecipeEditorIngredientList.getIngredientsField() >> [pork, kale]
        savedRecipes.size() == 1
        savedRecipes[0].getIngredients().size() == 2
    }
    
    def "should persist a recipe's ingredients attributes on save" () {
        given:"a recipe with an ingredient"
        def ingredientName = "Pork"
        def pork = createIngredient(ingredientName)
        def porkItemKey = pork.getItemKey()
        presenter.createNewRecipe()
        
        when: "the recipe is saved"
        presenter.save()
        def Recipe savedRecipe = recipeDao.getAll()[0]
        
        then:"the recipe should be persisted"
        1 * mockRecipeEditorIngredientList.getIngredientsField() >> [pork]
        def Ingredient savedIngredient = savedRecipe.getIngredients().toArray()[0]
        savedIngredient.getName() == ingredientName
        savedIngredient.getItemKey().getId() == porkItemKey.getId()
    }
    
    private Ingredient createIngredient(name) {
        def pork = new Ingredient()
        pork.setItem(itemUtils.createAndSaveEntityWithName(name))
        return pork
    }
    
    def retrieveAllRecipes() {
        recipeDao.getAll()
    }
    
    def "should add the new recipe to the master list on recipe save" () {
        given:"a recipe with a name"
        presenter.createNewRecipe()
        
        when:"the recipe is saved"
        presenter.save();
        
        then:"the recipe master list is cleared"
        1 * mockRecipeMasterList.addRecipe(_)
    }
    
    def"should add ingredient to the recipe editor when an item is dragged and dropped into ingredient panel"() {
        given:
        def item = itemUtils.createAndSaveEntityWithName("Cheese")
        presenter.init()
        
        when:
        presenter.dragAndDrop(item)
        then:"ingredient is added to the ingredient list"
        1 * mockRecipeEditorIngredientList.addIngredient(_)
    }
    
    private Item createItem(String name) {
        def Item item = new Item()
        item.setName(name)
        return item
    }
    
    
    
    def "should not save a new recipe if name is not unique" (){
    }
}


class RecipePresenterSaveTest extends RecipePreseterFixture {
    
    def "should not save a recipe if no name is defined"() {
    }
    
    def "should save the recipe name when user saves recipe"() {
        given:
        presenter.createNewRecipe()
        when:
        presenter.save()
        then:
        1 * mockTextField.getValue() >> "Brocolli Soup"
        recipeDao.getByName("Brocolli Soup") != null
        recipeDao.getByName("Brocolli Soup").getName().equals("Brocolli Soup")
    }
    
    private initialisePresenter() {
        presenter.setMainWindow(mockMainWindowUiHelper)
        presenter.setRecipeMasterList(mockRecipeMasterList)
        presenter.setRecipeEditor(mockRecipeEditor)
    }
    
    private createMocks() {
        mockMainWindowUiHelper = Mock(MainWindowView)
        mockRecipeMasterList = Mock(RecipeMasterList)
    }
}

