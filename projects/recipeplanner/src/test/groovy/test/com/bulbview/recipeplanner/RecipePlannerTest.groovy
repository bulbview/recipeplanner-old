package test.com.bulbview.recipeplanner
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification
import spock.lang.Stepwise

import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.JdoDao
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter
import com.bulbview.recipeplanner.ui.helper.CategoryTabs
import com.bulbview.recipeplanner.ui.helper.CategoryUiManager
import com.bulbview.recipeplanner.ui.helper.GenericListUiHelper
import com.bulbview.recipeplanner.ui.helper.MainWindowUiManager
import com.bulbview.recipeplanner.ui.helper.RecipeMasterList
import com.bulbview.recipeplanner.ui.helper.UiManager
import com.google.appengine.tools.development.testing.LocalServiceTestHelper
import com.googlecode.objectify.ObjectifyFactory

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
]) @Stepwise
class RecipePresenterTest extends Specification {

    @Autowired
    def RecipePlannerPresenter presenter
    def UiManager mockMainWindowUiHelper
    def GenericListUiHelper mockRecipeMasterList
    def CategoryTabs mockCategoriesList
    def CategoryUiManager mockCategoryWindow

    @Autowired
    def ObjectifyFactory objectifyFactory

    @Autowired
    def JdoDao<Recipe> recipeDao
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    def setup() {
        localServiceTestHelper.setUp();
        createMocks()
        initialisePresenter()
    }

    private initialisePresenter() {
        presenter.setMainWindow(mockMainWindowUiHelper)
        presenter.setRecipeMasterList(mockRecipeMasterList)
        presenter.setCategoryWindow(mockCategoryWindow)
    }

    private createMocks() {
        mockMainWindowUiHelper = Mock(MainWindowUiManager)
        mockRecipeMasterList = Mock(RecipeMasterList)
        mockCategoriesList = Mock(CategoryTabs)
        mockCategoryWindow = Mock(CategoryUiManager)
    }

    def recipeWithName() {
        def Recipe recipe = new Recipe()
        recipe.setName("testRecipe1")
        return recipe
    }

    def "should close recipe editor on save" (){

        when: "the presenter saves a recipe"
        presenter.save(recipeWithName())

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
        Recipe recipe = recipeWithName()

        and: "there are no existing recipes"
        def savedRecipes = recipeDao.getAll()
        savedRecipes != null
        savedRecipes.size() == 0

        when: "the recipe is saved"
        presenter.save(recipe)
        savedRecipes = recipeDao.getAll()

        then:"the recipe should be persisted"
        savedRecipes != null
        savedRecipes.size() == 1
        savedRecipes[0].equals(recipe)
    }

    def retrieveAllRecipes() {
        recipeDao.getAll()
    }

    def "should refresh recipe master list on recipe save" () {
        given:"a recipe with a name"
        Recipe recipe = recipeWithName()

        when:"the recipe is saved"
        presenter.save(recipe);

        then:"the recipe master list is cleared"
        1 * mockRecipeMasterList.clearPanel()

        and:"the recipe master list is refreshed"
        1 * mockRecipeMasterList.setRecipes(_)
    }

    def "should display all categories in categories list" () {
        when:"the application initiates"
        presenter.setCategoryTabs(mockCategoriesList)

        then:"the categories list is populated"
        1 * mockCategoriesList.setCategories(_)
    }


    def "should not save a new recipe if name is not unique" (){
    }

    def "should not save a recipe if no name is defined"() {
    }

    def "should open category editor for a new category" () {
        when:""
        presenter.addCategoryMenuSelected()
        then:""
        1 * mockCategoryWindow.setItemCategory(_)
        1 * mockMainWindowUiHelper.showCategoryWindow()
    }

    def "should save a new category" () {
        when:""

        then:""
    }
}

