package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.JdoDao
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class DaoTestFixture extends Specification {

    def savedEntities

    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper

    def setup() {
        localServiceTestHelper.setUp()
    }



    def entitesArePersisted(List expectedEntities) {
        assert savedEntities.size() == expectedEntities.size()
        for(entity in expectedEntities) {
            assert savedEntities.contains(entity)
        }
        return true
    }
}


class DaoTest extends DaoTestFixture {


    class recipeDaoTest extends DaoTestFixture {
        @Autowired
        private JdoDao<Recipe> recipeDao

        def recipeWithName(String name) {
            def Recipe recipe = new Recipe()
            recipe.setName(name)
            return recipe
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
    }
}