package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification
import test.com.bulbview.recipeplanner.dao.TestUtilities

import com.bulbview.recipeplanner.datamodel.Ingredient
import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.datamodel.Recipe
import com.bulbview.recipeplanner.persistence.EntityDao
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao
import com.bulbview.recipeplanner.ui.manager.ShoppingList
import com.bulbview.recipeplanner.ui.manager.ShoppingListCategory
import com.bulbview.recipeplanner.ui.manager.ShoppingListCategoryFactory
import com.bulbview.recipeplanner.ui.presenter.ShoppingListPresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class ShoppingListPresenterTest extends Specification {
    //presenter integration test mocking the view only
    //
    @Autowired
    private  LocalServiceTestHelper localServiceTestHelper
    
    @Autowired
    def ShoppingListPresenter presenter
    def ShoppingList mockShoppingList
    def ShoppingListCategory mockShoppingListCategory
    def ShoppingListCategoryFactory mockShoppingListCategoryFactory
    def ItemCategory category
    
    @Autowired
    def EntityDao<ItemCategory> categoryDao
    @Autowired
    private  ItemObjectifyDao itemDao
    
    def TestUtilities<Item> itemUtil
    def TestUtilities<ItemCategory> categoryUtil
    
    def Item item
    
    def setup() {
        localServiceTestHelper.setUp()
        mockShoppingList = Mock(ShoppingList)
        mockShoppingListCategoryFactory = Mock()
        mockShoppingListCategory = Mock()
        presenter.setView(mockShoppingList)
        presenter.setShoppingListCategoryFactory(mockShoppingListCategoryFactory)
        categoryUtil = TestUtilities.create(categoryDao,ItemCategory)
        itemUtil = TestUtilities.create(itemDao, Item)
        mockShoppingListCategoryFactory.create(_) >> mockShoppingListCategory
    }
    
    private createItemAndCategory() {
        category = categoryUtil.createAndSaveEntityWithName("Fruit")
        item = itemUtil.createAndSaveEntityWithName("oranges")
    }
    
    def setupSpec() {
        
    }
    
    
    
    def "new items added to the schedule should be added to a new category" () {
        createItemAndCategory()
        given:"the item has a category"
        item.setCategory(category)
        
        and:"the item's category has not been previously created"
        //do nothing
        
        when:"the new item is added to the shopping list"
        presenter.addItem(item)
        
        then:"an appropriate category is created"
        1 * mockShoppingListCategoryFactory.create("Fruit") >> mockShoppingListCategory
        
        and:"the category is added to the shopping list"
        1 * mockShoppingList.addCategory(mockShoppingListCategory)
        
        and:"the item is added to the category"
        1 * mockShoppingListCategory.addListItem(item)
    }
    
    def "new items added to the schedule should be added to an existing category" () {
        createItemAndCategory()
        given:"the item has a category"
        item.setCategory(category)
        
        and:"the item's category has  been previously created"
        presenter.setShoppingListCategories(["Fruit": mockShoppingListCategory])
        
        
        when:"the new item is added to the shopping list"
        presenter.addItem(item)
        
        then:"the item is added to an existing category"
        1 * mockShoppingListCategory.addListItem(item)
        
        and:"no new category is created"
        0 * mockShoppingListCategoryFactory.create(_)
    }
    
    def"should add only 1 category to shopping list if items from the same category are added"() {
        given:"a category"
        def ItemCategory category = categoryUtil.createAndSaveEntityWithName("salad");
        
        and:"the category has items"
        def createAndSaveToCategory ={ def saveItem = itemUtil.createAndSaveEntityWithName(it); saveItem.setCategory(category); return saveItem}
        def radish = createAndSaveToCategory("radish")
        def celery = createAndSaveToCategory("celery")
        
        when:
        presenter.addItem(radish)
        presenter.addItem(celery)
        then:
        1 *mockShoppingList.addCategory(mockShoppingListCategory)
    }
    
    def"should clear all visible categories and constituent items"() {
        when:
        presenter.clear()
        then:
        1 * mockShoppingList.clear();
        presenter.visibleCategories.size() == 0
    }
    
    
    def"should add a recipe's ingredients to the shopping list"() {
        given:"a number of persisted items"
        def ItemCategory category = categoryUtil.createAndSaveEntityWithName("general category");
        def Item rice = itemUtil.createAndSaveEntityWithName("rice")
        def Item fish = itemUtil.createAndSaveEntityWithName("fish")
        def Item lemon =itemUtil.createAndSaveEntityWithName("lemon")
        
        and:"a number of ingredients referencing those items"
        def createIngredient ={it->
            def ing = new Ingredient();
            ing.setItem(it);
            it.setCategory(category);
            itemDao.save(it);
            return ing};
        
        def Ingredient riceIngredient = createIngredient(rice)
        def Ingredient fishIngredient = createIngredient(fish)
        def Ingredient lemonIngredient = createIngredient(lemon)
        
        and:"a recipe containing the ingredients"
        def Recipe recipe = new Recipe()
        recipe.setIngredients([
            riceIngredient,
            fishIngredient,
            lemonIngredient
        ])
        
        when:"the recipe is added to the shopping list"
        presenter.addItem(recipe)
        
        then:"the individual items are added to the shopping list"
        1 * mockShoppingListCategory.addListItem(rice)
        1 * mockShoppingListCategory.addListItem(fish)
        1 * mockShoppingListCategory.addListItem(lemon)
    }
    
}
