package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.RecipeDao
import com.google.appengine.tools.development.testing.LocalServiceTestHelper


@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class DaoTest extends Specification {

    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    @Autowired
    def RecipeDao recipeDao

    def setup() {
        localServiceTestHelper.setUp()
    }

    def "should retrieve all persisted recipes" () {
        given:"recipes are saved in persistence"
        def recipe1 = recipeWithName("recipe1")
        def recipe2 = recipeWithName("recipe2")
        recipeDao.saveRecipe(recipe1)
        recipeDao.saveRecipe(recipe2)

        when:"all the recipes are retrieved from persistence"
        def savedRecipes = recipeDao.getAll()

        then:"all persisted recipes are retrieved"
        savedRecipes.size() == 2
        savedRecipes.contains(recipe1)
        savedRecipes.contains(recipe2)
    }

    def recipeWithName(String name) {
        def Recipe recipe = new Recipe()
        recipe.setName(name)
        return recipe
    }
}
