package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.JdoDao
import com.google.appengine.tools.development.testing.LocalServiceTestHelper


@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class DaoTest extends Specification {

    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    @Autowired
    def JdoDao<Recipe> recipeDao
    @Autowired
    def JdoDao<ItemCategory> categoryDao
    def savedEntities

    def setup() {
        localServiceTestHelper.setUp()
    }

    def "should retrieve all persisted recipes" () {
        given:"recipes are saved in persistence"
        def expectedRecipes = [
            recipeWithName("recipe1"),
            recipeWithName("recipe2")
        ]
        for(recipe in expectedRecipes) {
            recipeDao.save(recipe)
        }

        when:"all the recipes are retrieved from persistence"
        savedEntities = recipeDao.getAll()

        then:"all persisted recipes are retrieved"
        entitesArePersisted(expectedRecipes)
    }

    def "should retrieve all persisted categories" () {
        given:"categories are persisted"
        def expectedCategories = [
            category("category1"),
            category("category2")
        ]
        for(category in expectedCategories) {
            categoryDao.save(category)
        }

        when:"all the recipes are retrieved from persistence"
        savedEntities = categoryDao.getAll()

        then:"all categories are retrieved"
        entitesArePersisted(expectedCategories)
    }

    def category(name) {
        def ItemCategory category = new ItemCategory()
        category.setName(name)
        return category
    }

    private entitesArePersisted(List expectedEntities) {
        assert savedEntities.size() == expectedEntities.size()
        for(entity in expectedEntities) {
            assert savedEntities.contains(entity)
        }
        return true
    }

    def recipeWithName(String name) {
        def Recipe recipe = new Recipe()
        recipe.setName(name)
        return recipe
    }
}
