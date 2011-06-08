package test.com.bulbview.recipeplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.ContextConfiguration

import spock.lang.Specification

import com.bulbview.recipeplanner.datamodel.Item
import com.bulbview.recipeplanner.datamodel.ItemCategory
import com.bulbview.recipeplanner.persistence.ItemObjectifyDao
import com.bulbview.recipeplanner.persistence.ObjectifyDao
import com.bulbview.recipeplanner.ui.manager.CategorisedItemList
import com.bulbview.recipeplanner.ui.presenter.CategoryListPresenter
import com.google.appengine.tools.development.testing.LocalServiceTestHelper

@ContextConfiguration(locations=[
    "classpath:applicationContext.xml", "classpath:itest-persistenceContext.xml"
])
class CategoryListPresenterTest extends Specification {


    @Autowired
    def ItemObjectifyDao itemDao
    @Autowired
    def ObjectifyDao<ItemCategory> categoryDao
    @Autowired
    def CategoryListPresenter categoryListPresenter
    def CategorisedItemList mockCategorisedItemList
    @Autowired
    def  LocalServiceTestHelper localServiceTestHelper



    def "should display all items for a category on application startup" () {
        given:"there are a number of persisted items"
        saveItem("milk")
        saveItem("bread")
        saveItem("chocolate")
        when:"the presenter is initialised"
        categoryListPresenter.init()

        then:"all items for the given category are displayed"
        3 * mockCategorisedItemList.addListItem(_)
    }

    private saveItem(name) {
        def item = new Item()
        item.setName(name)
        itemDao.save(item)
    }

    def "should save item under associated category" () {
        when:""

        then:""
    }

    def "adding a new item should save and add the item to the view" () {
        given:
        def category = new ItemCategory()
        category.setName "Dairy"
        categoryDao.save(category)

        when:"an item is saved to an existing category"
        categoryListPresenter.setCategory("Dairy")
        categoryListPresenter.addItem("cheese")

        then:"a new item is saved"
        def all = itemDao.getAll()
        all.size() == 1
        def savedItem = itemDao.getByName("cheese")
        savedItem.getName().equals("cheese")

        and:"the item is added to the list"
        1 * mockCategorisedItemList.addListItem(_);
    }

    def setup() {
        localServiceTestHelper.setUp()
        mockCategorisedItemList =  Mock()
        categoryListPresenter.setView(mockCategorisedItemList)
    }
}
