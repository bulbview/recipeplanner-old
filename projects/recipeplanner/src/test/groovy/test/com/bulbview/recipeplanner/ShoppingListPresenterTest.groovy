package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification
import test.com.bulbview.recipeplanner.dao.TestUtilities

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao
import com.bulbview.recipeplanner.persistence.ObjectifyDao
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
    def Item item
    def ItemCategory category

    @Autowired
    def ObjectifyDao<ItemCategory> categoryDao
    @Autowired
    private ItemObjectifyDao itemDao

    def TestUtilities<Item> itemUtil
    def TestUtilities<ItemCategory> categoryUtil

    def setup() {
        localServiceTestHelper.setUp()
        mockShoppingList = Mock(ShoppingList)
        mockShoppingListCategoryFactory = Mock()
        mockShoppingListCategory = Mock()
        presenter.setShoppingList(mockShoppingList)
        presenter.setShoppingListCategoryFactory(mockShoppingListCategoryFactory)
        itemUtil = TestUtilities.create(itemDao, Item)
        categoryUtil = TestUtilities.create(categoryDao,ItemCategory)

        item = itemUtil.createAndSaveEntityWithName("oranges")
        category = categoryUtil.createAndSaveEntityWithName("Fruit")
    }



    def "new items added to the schedule should be added to a new category" () {
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






}
