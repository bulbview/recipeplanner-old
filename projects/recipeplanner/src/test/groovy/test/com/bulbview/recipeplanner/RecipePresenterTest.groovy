package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.annotation.DirtiesContext

import test.com.bulbview.recipeplanner.dao.SpringContextTestFixture
import test.com.bulbview.recipeplanner.dao.TestUtilities

import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.ui.RecipeEditor
import com.bulbview.recipeplanner.ui.manager.MainWindowView
import com.bulbview.recipeplanner.ui.manager.RecipeMasterList
import com.bulbview.recipeplanner.ui.presenter.RecipePresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.vaadin.ui.TextField

@DirtiesContext
class RecipePreseterFixture extends SpringContextTestFixture {
    @Autowired
    def RecipePresenter presenter
    def MainWindowView mockMainWindowUiHelper
    def RecipeMasterList mockRecipeMasterList
    def TextField mockTextField
    def RecipeEditor mockRecipeEditor
    
    def TestUtilities recipeUtils
    
    @Autowired
    def EntityDao<Recipe> recipeDao
    
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper
    
    def setup() {
        localServiceTestHelper.setUp();
        recipeUtils = TestUtilities.create(recipeDao, Recipe)
        createMocks()
        initialisePresenter()
        mockRecipeEditor.getRecipeNameField() >> mockTextField
    }
    
    private initialisePresenter() {
        presenter.setMainWindow(mockMainWindowUiHelper)
        presenter.setRecipeMasterList(mockRecipeMasterList)
        presenter.setRecipeEditor(mockRecipeEditor)
    }
    
    private createMocks() {
        mockMainWindowUiHelper = Mock()
        mockRecipeMasterList = Mock()
        mockRecipeEditor = Mock()
        mockTextField = Mock()
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

