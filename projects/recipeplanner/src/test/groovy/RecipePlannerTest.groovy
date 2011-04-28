import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification
import spock.lang.Stepwise

import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.RecipeDao
import com.bulbview.recipeplanner.ui.MainWindowUiHelper
import com.bulbview.recipeplanner.ui.RecipePlannerPresenter

@ContextConfiguration(locations="classpath:applicationContext.xml") @Stepwise
class RecipePresenterTest extends Specification {

    @Autowired
    def RecipePlannerPresenter presenter
    def MainWindowUiHelper mockMainWindowUiHelper

    @Autowired
    def RecipeDao recipeDao

    def setup() {
        mockMainWindowUiHelper = Mock(MainWindowUiHelper)
        presenter.setMainWindow(mockMainWindowUiHelper)
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


    def "should not save a new recipe if name is not unique" (){
    }

    def "should not save a recipe if no name is defined"() {
    }
}

