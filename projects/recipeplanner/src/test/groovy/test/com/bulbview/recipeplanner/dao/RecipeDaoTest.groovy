package test.com.bulbview.recipeplanner.dao
import org.springframework.beans.factory.annotation.Autowired

import com.bulbview.recipeplanner.datamodel.Ingredient
import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao



class RecipeDaoTest extends SpringContextTestFixture {
    
    
    @Autowired
    private EntityDao<Recipe> recipeDao
    @Autowired
    def ItemObjectifyDao itemDao
    def TestUtilities itemUtils
    
    def recipeWithName(String name) {
        def Recipe recipe = new Recipe()
        recipe.setName(name)
        return recipe
    }
    
    def setup() {
        itemUtils = TestUtilities.create(itemDao, Item)
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
    
    def"should retrieve all persisted recipe's ingredients"() {
        given:
        def Recipe recipe = recipeWithName("Smoked Salmon")
        Ingredient salmon = createIngredient("salmon")
        Ingredient butter = createIngredient("butter")
        Ingredient parsley = createIngredient("parlsey")
        recipe.setIngredients([salmon, butter, parsley])
        when:
        recipeDao.save(recipe)
        then:
        def savedRecipe =recipeDao.getByName("Smoked Salmon")
        savedRecipe.getIngredients().size() == 3
        //        savedRecipe.getIngredients().contains(salmon)
    }
    
    private Ingredient createIngredient(String name) {
        Ingredient ing = new Ingredient()
        ing.setItem(itemUtils.createAndSaveEntityWithName(name))
        return ing
    }
}