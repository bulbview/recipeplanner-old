package test.com.bulbview.recipeplanner.dao

import org.springframework.beans.factory.annotation.Autowired


import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.EntityDao


class RecipeDaoTest extends DaoTestFixture {


    class recipeDaoTest extends DaoTestFixture {
        @Autowired
        private EntityDao<Recipe> recipeDao

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