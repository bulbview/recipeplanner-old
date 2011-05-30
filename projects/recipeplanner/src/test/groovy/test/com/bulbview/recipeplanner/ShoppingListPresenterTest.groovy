package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.ObjectifyDao
import com.bulbview.recipeplanner.ui.manager.ShoppingList
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

    @Autowired
    def ObjectifyDao<ItemCategory> categoryDao

    def setup() {
        localServiceTestHelper.setUp()
        mockShoppingList = Mock(ShoppingList)
        presenter.setShoppingList(mockShoppingList)
    }

    def "should create shopping list sections for all persisted categories on application startup" () {
        given:"item persisted categories with a name"
        def itemCategory0 = saveItemCategoryWithName("cat0")
        def itemCategory1 = saveItemCategoryWithName("cat1")
        def itemCategory2 = saveItemCategoryWithName("cat2")

        when:"the presenter is intialised"
        presenter.init()

        then:"a new category tab is created with the same name"
        3 * mockShoppingList.addCategory(_)
    }

    def ItemCategory saveItemCategoryWithName(String name) {
        def itemCategory = new ItemCategory()
        itemCategory.setName(name)
        def saved = categoryDao.save(itemCategory)
        return saved
    }





}
